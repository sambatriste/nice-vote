package nablarch.fw.web;

import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nablarch.core.util.Builder;
import nablarch.core.util.annotation.Published;

/**
 * testing framework用の{@link HttpRequest}実装クラス。
 *
 * @author Hisaaki Shioiri
 */
public class MockHttpRequest extends HttpRequest {

    /** 改行文字 */
    private static final String LS = "\r\n";

    /**
     * デフォルトコンストラクタ。
     * <pre>
     * 下記のHTTPリクエストメッセージと等価な内容のオブジェクトを生成する。:
     *     GET / HTTP/1.1/
     * 基本的に業務アプリケーションがHttpRequestインスタンスを直接生成することはない。
     * このメソッドはFWの内部やテストケースで使用することを想定したものである。
     * </pre>
     */
    @Published(tag = "architect")
    public MockHttpRequest() {
        setRequestUri("/");
    }

    /**
     * 引数で渡されたHTTPリクエストメッセージと等価な内容のオブジェクトを生成する。
     * <pre>
     *   このメソッドはテストケース内で使用することを想定したものである。
     * </pre>
     *
     * @param message HTTPリクエストメッセージ
     */
    @Published(tag = "architect")
    public MockHttpRequest(String message) {
        parse(new StringReader(message));
    }

    /**
     * HTTPリクエストメソッド名を返す。
     *
     * @return リクエストメソッド名
     */
    @Published
    public String getMethod() {
        return this.method;
    }

    /**
     * HTTPリクエストメソッド名を設定する。
     * <pre>
     * 明示的に設定しない場合のデフォルト値は"GET"である。
     * </pre>
     *
     * @param method HTTPメソッド名
     * @return このオブジェクト自体
     */
    public HttpRequest setMethod(String method) {
        this.method = method.trim();
        return this;
    }

    /** HTTPリクエストメソッド名 */
    private String method = "GET";

    /**
     * HTTPバージョン名を返す。
     *
     * @return HTTPバージョン名
     */
    @Published
    public String getHttpVersion() {
        return this.httpVersion;
    }

    /**
     * HTTPバージョン名を指定する。
     * <pre>
     * 明示的に指定しない場合のデフォルト値は"HTTP/1.1"である。
     * </pre>
     *
     * @param httpVersion HTTPバージョン名
     * @return このオブジェクト自体
     */
    public HttpRequest setHttpVersion(String httpVersion) {
        if (!HTTP_VERSION_SYNTAX.matcher(httpVersion)
                .matches()) {
            throw new IllegalArgumentException(
                    "Illegal HTTP version.: " + httpVersion
            );
        }
        this.httpVersion = httpVersion.trim();
        return this;
    }

    /** HTTPバージョン名の書式 */
    private static final Pattern HTTP_VERSION_SYNTAX = Pattern.compile(
            "HTTP/(0\\.9|1\\.0|1\\.1)"
    );

    /** HTTPバージョン名 */
    private String httpVersion = "HTTP/1.1";

    /**
     * リクエストボディの読み出し用I/Oを返す。
     *
     * @return 読み出し用I/O
     */
    public Reader getBodyReader() {
        return this.bodyReader;
    }

    /**
     * リクエストボディの読み出し用I/Oを設定する。
     *
     * @param reader 読み出し用I/O
     * @return このオブジェクト自体
     */
    public HttpRequest setBodyReader(Reader reader) {
        bodyReader = reader;
        return this;
    }

    /** リクエストボディの読み出し用I/O */
    private Reader bodyReader;

    /**
     * リクエストパラメータのMapを返す。
     * <pre>
     * HTTPリクエストメッセージ中の以下のパラメータを格納したMapを返す。
     *   1. リクエストURI中のクエリパラメータ
     *   2. メッセージボディ内のPOSTパラメータ
     * パラメータ名は重複する可能性があるので、値の型はString[]で定義されている。
     * このMapに対する変更は直接反映される。
     * </pre>
     *
     * @return リクエストパラメータのMap
     */
    public Map<String, String[]> getParamMap() {
        return this.params;
    }

    /**
     * リクエストパラメータを取得する。
     * <pre>
     * このメソッドの処理は、以下のソースコードと等価である。
     *     this.params().get(name);
     * </pre>
     *
     * @param name パラメータ名
     * @return パラメータの値
     * @see #getParamMap()
     */
    public String[] getParam(String name) {
        return this.params.get(name);
    }

    /**
     * リクエストパラメータを設定する。
     * <pre>
     * このメソッドの処理は、以下のソースコードと等価である。
     *     this.params().put(name, params);
     * </pre>
     *
     * @param name パラメータ名
     * @param params パラメータの値
     * @return このオブジェクト自体
     */
    @Published
    public HttpRequest setParam(String name, String... params) {
        this.params.put(name, params);
        return this;
    }

    /**
     * リクエストパラメータを設定する。
     * <pre>
     * このメソッドは自動テストからの使用を想定している。
     * </pre>
     *
     * @param params リクエストパラメータのMap
     * @return このオブジェクト自体
     */
    @Published(tag = "architect")
    public HttpRequest setParamMap(Map<String, String[]> params) {
        this.params = params;
        return this;
    }

    /** HTTPリクエストパラメータを格納したMap */
    private Map<String, String[]> params = new HashMap<String, String[]>();

    /**
     * メッセージ中のURLエンコードされたパラメータを読み込む。
     *
     * @param scanner メッセージに対するscanner
     */
    private void parseUrlEncodedParams(Scanner scanner) {
        Scanner body = scanner.useDelimiter("[&;]|$");
        Map<String, List<String>> params = new HashMap<String, List<String>>();

        while (body.hasNext(POST_PARAM_SYNTAX)) {
            body.next(POST_PARAM_SYNTAX);
            MatchResult entry = body.match();
            String key = entry.group(1);
            String val = null;
            try {
                val = URLDecoder.decode(entry.group(2), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // will never happen.
                throw new IllegalStateException("url decoding failed.", e);
            }

            List<String> values = params.get(key);
            if (values == null) {
                values = new ArrayList<String>();
            }
            values.add(val);
            params.put(key, values);
        }

        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            List<String> list = entry.getValue();
            String[] array = list.toArray(new String[list.size()]);
            this.params.put(entry.getKey(), array);
        }
    }

    /** ポストパラメータの書式 */
    private static final Pattern
            POST_PARAM_SYNTAX = Pattern.compile("(.+?)=(.*)");

    /**
     * HTTPリクエストヘッダを格納したMapを取得する。
     * <pre>
     * このMapに対する変更は直接反映される。
     * </pre>
     *
     * @return HTTPリクエストヘッダのMap
     */
    @Published
    public Map<String, String> getHeaderMap() {
        return headers;
    }

    /**
     * HTTPリクエストヘッダを格納したMapを設定する。
     *
     * @param headers HTTPリクエストヘッダを格納したMap
     * @return このオブジェクト自体
     */
    public HttpRequest setHeaderMap(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * HTTPリクエストヘッダの値を返す。
     *
     * @param headerName リクエストヘッダ名
     * @return HTTPリクエストヘッダの値
     */
    @Published
    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    /** HTTPリクエストヘッダのMap */
    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * HTTPリクエストのホストヘッダを取得する。
     * <pre>
     * このメソッドの処理は以下のソースコードと等価である。
     *     getHeaderMap().get("HOST")
     * </pre>
     *
     * @return ホストヘッダ
     */
    @Published
    public String getHost() {
        return this.headers.get("Host");
    }

    /**
     * ホストヘッダの値を設定する。
     *
     * @param host ホストヘッダの値
     * @return このオブジェクト自体
     */
    public HttpRequest setHost(String host) {
        this.headers.put("Host", host);
        return this;
    }

    /**
     * このリクエストで送信されるクッキー情報を取得する。
     *
     * @return クッキー情報オブジェクト
     */
    @Published(tag = "architect")
    public HttpCookie getCookie() {
        return MockHttpCookie.valueOf(headers.get("Cookie"));
    }

    /**
     * このリクエストで送信されるクッキー情報を設定する。
     *
     * @param cookie クッキー情報オブジェクト
     * @return このオブジェクト自体
     */
    @Published(tag = "architect")
    public HttpRequest setCookie(HttpCookie cookie) {
        this.headers.put("Cookie", cookie.toString());
        return this;
    }

    /**
     * {@inheritDoc}
     * このクラスの実装では、オブジェクトの内容と等価なHTTPリクエストメッセージを返す。
     */
    public String toString() {
        String requestLine = String.format(
                "%s %s %s",
                this.getMethod(), this.getRequestUri(), this.getHttpVersion()
        );
        StringBuilder buffer = new StringBuilder(requestLine).append(LS);

        StringBuilder bodyBuffer = new StringBuilder();
        Map<String, String[]> paramMap = getParamMap();
        if (!paramMap.isEmpty()) {
            Iterator<Map.Entry<String, String[]>>
                    params = paramMap.entrySet()
                    .iterator();

            while (params.hasNext()) {
                Map.Entry<String, String[]> param = params.next();
                String name = param.getKey();
                String[] values = param.getValue();
                for (int i = 0; i < values.length; i++) {
                    String value = null;
                    try {
                        value = URLEncoder.encode(values[i], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalStateException("url encoding failed.", e);
                    }
                    bodyBuffer.append(name)
                            .append("=")
                            .append(value);
                    if (i < values.length - 1) {
                        bodyBuffer.append("&");
                    }
                }
                if (params.hasNext()) {
                    bodyBuffer.append("&");
                }
            }
        }
        if (!headers.containsKey("Content-Length")) {
            headers.put("Content-Length", String.valueOf(bodyBuffer.length())); //FIXME
        }

        Map<String, String> headers = this.getHeaderMap();
        Iterator<Map.Entry<String, String>> entries = headers.entrySet()
                .iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String name = entry.getKey();
            String value = entry.getValue();
            buffer.append(name)
                    .append(": ")
                    .append(value);
            buffer.append(LS);
        }
        buffer.append(LS)
                .append(bodyBuffer);

        return buffer.toString();
    }

    /**
     * HTTPリクエストメッセージを読み込んで、このオブジェクトを初期化する。
     *
     * @param source HTTPリクエストメッセージ
     */
    private void parse(Reader source) {
        Scanner requestMessage = new Scanner(source);
        Scanner requestLine = new Scanner(requestMessage.nextLine());
        scanHttpMethod(requestLine);
        scanRequestUri(requestLine);
        scanHttpVersion(requestLine);

        String header = null;
        while (requestMessage.hasNextLine()) {
            String line = requestMessage.nextLine();
            if (line.length() == 0) {
                break; // Blank line. following lines are message body.
            }
            if (header == null) {
                header = line;
                continue;
            }
            if (line.matches("\\s+.*")) {
                header += (" " + line.trim());
                continue;
            }
            scanRequestHeader(header);
            header = line;
        }
        if (header != null) {
            scanRequestHeader(header);
        }
        scanRequestBody(requestMessage);
    }

    /**
     * HTTPリクエストメッセージボディを読み込む。
     *
     * @param message HTTPリクエストメッセージ
     */
    private void scanRequestBody(Scanner message) {
        StringBuilder buff = new StringBuilder();
        while (message.hasNextLine()) {
            String line = message.nextLine();
            buff.append(line)
                    .append(LS);
        }
        setBodyReader(new StringReader(buff.toString()));
        parseUrlEncodedParams(new Scanner(this.getBodyReader()));
    }

    /**
     * HTTPリクエストヘッダーを読み込む。
     *
     * @param header HTTPリクエストヘッダ
     */
    private void scanRequestHeader(String header) {
        Matcher m = HTTP_REQUEST_HEADER_SYNTAX.matcher(header);
        if (!m.matches()) {
            parseError();
        }
        this.headers.put(m.group(1), m.group(2));
    }

    /** HTTPリクエストヘッダーの書式 */
    private static final Pattern HTTP_REQUEST_HEADER_SYNTAX = Pattern.compile(
            "([a-zA-Z0-9\\-]+):\\s(.*)"
    );

    /**
     * HTTPリクエストメソッド名を読み込む。
     *
     * @param scanner HTTPリクエストライン
     */
    private void scanHttpMethod(Scanner scanner) {
        this.method = scanner.next(HTTP_METHODS);
    }

    /** HTTPリクエストメソッド名 */
    private static final Pattern HTTP_METHODS = Pattern.compile(
            "GET|POST|DELETE|PUT|HEAD", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern query_parameter = Pattern.compile("^[?;]");

    /**
     * HTTPリクエストURIを読み込む。
     *
     * @param scanner HTTPリクエストライン
     */
    private void scanRequestUri(Scanner scanner) {
        if (!scanner.hasNext(REQUEST_URI_SYNTAX)) {
            throw new HttpErrorResponse(400);
        }
        setRequestUri(scanner.next(REQUEST_URI_SYNTAX));
        final String query = scanner.match().group(2);
        if (query != null) {
            parseUrlEncodedParams(new Scanner(query.substring(1)));
        }
    }

    /** URI中のトークン中に使用可能な文字 */
    static final String XPALPHA = "(?:[a-zA-Z0-9$\\-_@.&!*\"'(),]"
            + "|"
            + "%[0-9a-fA-F]{2})";

    /** リクエストURIの書式 */
    static final Pattern REQUEST_URI_SYNTAX = Pattern.compile(
            Builder.linesf(
                    "(                " // キャプチャ#1: パス部分
                    , "  (?:/?%s*)      ", XPALPHA
                    , "  (?:/%s*)*/?    ", XPALPHA
                    , "  |              "
                    , "  /              "
                    , ")                "
                    , "(                " // キャプチャ#2: クエリパラメータ
                    , "    [?;]%s+=%s*  ", XPALPHA, XPALPHA
                    , "  (?:            "
                    , "    [&;]%s+=%s*  ", XPALPHA, XPALPHA
                    , "  )*             "
                    , ")?               "
            ), Pattern.COMMENTS);

    /**
     * HTTPバージョン名を読み込む。
     *
     * @param scanner HTTPリクエストライン
     */
    private void scanHttpVersion(Scanner scanner) {
        this.httpVersion = scanner.next(HTTP_VERSION_SYNTAX);
    }

    /** パース処理中のエラーを例外として送出する。 */
    private void parseError() {
        throw new RuntimeException(
                "Invalid http request message.: " + this.toString()
        );
    }
}

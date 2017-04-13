package nablarch.fw.web;

import java.util.Map;
import java.util.regex.Pattern;

import nablarch.core.util.StringUtil;
import nablarch.core.util.annotation.Published;

/**
 * {@link HttpCookie}のテストFW用モック実装クラス。
 *
 * @author Hisaaki Shioiri
 */
@Published(tag = "architect")
public class MockHttpCookie extends HttpCookie {

    /** 区切り文字 */
    private static final Pattern SEPARATOR = Pattern.compile(";[ ]?");

    /**
     * Cookieの値を文字列に変換する。
     * <p/>
     * 変換後の値は、HTTPリクエストのCookieヘッダーに埋め込む形式でフォーマットする。
     *
     * @return 文字列に変換した値
     */
    @Override
    public String toString() {
        final Map<String, String> cookies = getDelegateMap();

        final StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : cookies.entrySet()) {
            if (result.length() != 0) {
                result.append("; ");
            }
            result.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue());
        }
        return result.toString();
    }

    /**
     * 文字列を{@link HttpCookie}に変換する。
     *
     * @param cookieStr HTTPリクエストのCookieヘッダー形式の文字列
     * @return {@link HttpCookie}
     */
    public static HttpCookie valueOf(final String cookieStr) {
        final HttpCookie result = new HttpCookie();
        if (StringUtil.isNullOrEmpty(cookieStr)) {
            return result;
        }

        // キーにも値にも「;」は許容されないので、単純に「;」で分割
        final String[] cookies = SEPARATOR.split(cookieStr);
        for (String cookie : cookies) {
            // Cookieのキー値には、「=」は許容されないので単純に「=」で分割可能
            final int index = cookie.indexOf('=');
            if (index == -1) {
                throw new IllegalArgumentException("invalid format. Cookie String=[" + cookieStr + ']');
            }
            final String key = cookie.substring(0, index);
            final String value = cookie.substring(index + 1);
            result.put(key, value);
        }
        return result;
    }
}

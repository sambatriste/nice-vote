package com.nablarch.example;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.StringUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.BodyConverter;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpErrorResponse;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.HttpResponse.Status;

import java.util.List;

/**
 * {@link HttpResponse}および{@link HttpErrorResponse}を作成するためのビルダークラス。
 *
 * 設定されたHTTPステータスとレスポンスオブジェクトを使用して、
 * {@link HttpResponse}、{@link HttpErrorResponse}を作成する。
 * HTTPステータスが設定されていない場合は、
 * デフォルト値として200 OK (HttpResponse)または400 BAD REQUEST (HttpErrorResponse)が使用される。
 *
 * {@link HttpResponse}を生成する例を以下に示す。
 * <pre>
 *     HttpResponse res = ResponseBuilder.with(executionContext)
 *                                       .build(responseObject);
 * </pre>
 * （ステータスコードは200が使用される）
 *
 * {@link HttpErrorResponse}を生成する例を以下に示す。
 * <pre>
 *     throw ResponseBuilder.with(executionContext)
 *                          .setHttpStatus(Status.NOT_FOUND)
 *                          .buildError(responseObject);
 * </pre>
 * （ステータスコードは、設定した404が使用される）
 * <p>
 * {@link SystemRepository}に{@link ResponseBuilder#BODY_CONVERTERS_KEY}のキーで
 * {@link BodyConverter}実装クラスのリストが設定されている必要がある。
 * 設定されていない場合、変換実行時に例外{@link IllegalStateException}がスローされる。
 *
 * 設定例を以下に示す。
 * <pre>
 *    <list name="bodyConverters">
 *      <component class="nablarch.integration.jaxrs.jackson.Jackson2BodyConverter"/>
 *      <component class="nablarch.fw.jaxrs.JaxbBodyConverter"/>
 *      <component class="nablarch.fw.jaxrs.FormUrlEncodedConverter"/>
 *    </list>
 * </pre>
 *
 * このリストは、{@link nablarch.fw.jaxrs.BodyConvertHandler#setBodyConverters(List)}
 * で設定するものと共有することが望ましい（設定の矛盾や二重メンテナンスを避けるため）。
 *
 * @noinspection unpublishedApi
 */
public class ResponseBuilder {

    /** {@link BodyConverter}を取得するためのキー */
    private static final String BODY_CONVERTERS_KEY = "bodyConverters";

    /**
     * ファクトリメソッド。
     *
     * @param context 現在の{@link ExecutionContext}
     * @return 新しいインスタンス
     */
    public static ResponseBuilder with(ExecutionContext context) {
        return new ResponseBuilder(context);
    }

    /** 実行コンテキスト */
    private final ExecutionContext executionContext;

    /** {@link JaxRsContext} */
    private final JaxRsContext jaxRsContext;

    /** HTTPステータス */
    private Status httpStatus = null;

    /**
     * プライベートコンストラクタ。
     *
     * @param context 実行コンテキスト
     */
    private ResponseBuilder(ExecutionContext context) {
        if (context == null) {
            throw new IllegalArgumentException("ExecutionContext must not be null.");
        }
        this.executionContext = context;
        this.jaxRsContext = JaxRsContext.get(context);
        if (jaxRsContext == null) {
            throw new IllegalStateException("JaxRsContext must be set in ExecutionContext.");
        }
    }

    /**
     * レスポンスに出力するHTTPステータスを設定する。
     *
     * @param httpStatus HTTPステータス
     * @return 本インスタンス
     */
    public ResponseBuilder setHttpStatus(Status httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    /**
     * 指定されたオブジェクトで{@link HttpResponse}を作成する。
     *
     * @param responseObject 出力オブジェクト
     * @return {@link HttpResponse}
     */
    public HttpResponse build(Object responseObject) {
        Status httpStatus = this.httpStatus == null ? Status.OK : this.httpStatus;
        return buildHttpResponse(responseObject, httpStatus);
    }

    /**
     * 指定されたオブジェクトで{@link HttpErrorResponse}を作成する。
     *
     * @param responseObject レスポンスに出力するオブジェクト
     * @return {@link HttpErrorResponse}
     */
    public HttpErrorResponse buildError(Object responseObject) {
        Status httpStatus = this.httpStatus == null ? Status.BAD_REQUEST : this.httpStatus;
        HttpResponse httpResponse = buildHttpResponse(responseObject, httpStatus);
        return new HttpErrorResponse(httpResponse);
    }

    /**
     * {@link HttpResponse}を作成する。
     *
     * @param responseObject レスポンスに出力するオブジェクト
     * @return {@link HttpResponse}
     */
    private HttpResponse buildHttpResponse(Object responseObject, Status httpStatus) {
        if (responseObject == null) {
            throw new IllegalArgumentException("response object must be set.");
        }
        String producesMediaType = getMediaTypeToProduce();
        BodyConverter converterToProduce = findConverter(producesMediaType);
        HttpResponse httpResponse = converterToProduce.write(responseObject, executionContext);
        httpResponse.setStatusCode(httpStatus.getStatusCode());
        return httpResponse;
    }

    /**
     * {@link javax.ws.rs.Produces}からメディアタイプを取得する。
     *
     * @return メディアタイプ
     */
    private String getMediaTypeToProduce() {
        String mediaType = jaxRsContext.getProducesMediaType();
        if (StringUtil.isNullOrEmpty(mediaType)) {
            throw new IllegalStateException("could not find media type to produce. [" + mediaType + "]");
        }
        return mediaType;
    }

    /**
     * メディアタイプを変換するための{@link BodyConverter}を取得する。
     * <p>
     * 変換対象の{@link BodyConverter}が存在しない場合は、
     * {@link Status#UNSUPPORTED_MEDIA_TYPE}を持つ{@link HttpErrorResponse}を送出する。
     *
     * @param mediaType メディアタイプ
     * @return {@link BodyConverter}
     */
    private BodyConverter findConverter(String mediaType) {
        for (BodyConverter converter : getBodyConverters()) {
            if (converter.isConvertible(mediaType)) {
                return converter;
            }
        }
        throw new HttpErrorResponse(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
    }

    /**
     * {@link SystemRepository}から全{@link BodyConverter}を取得する。
     *
     * @return 全{@link BodyConverter}
     */
    private static List<BodyConverter> getBodyConverters() {
        List<BodyConverter> bodyConverters = SystemRepository.get(BODY_CONVERTERS_KEY);
        if (bodyConverters == null) {
            throw new IllegalStateException(
                    "could not find [" + BODY_CONVERTERS_KEY + "] in SystemRepository.");
        }
        return bodyConverters;
    }

}

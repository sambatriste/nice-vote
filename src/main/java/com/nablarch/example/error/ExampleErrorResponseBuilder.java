package com.nablarch.example.error;

import com.nablarch.example.ResponseException;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.core.util.StringUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.BodyConverter;
import nablarch.fw.jaxrs.ErrorResponseBuilder;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpErrorResponse;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.HttpResponse.Status;

import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Example用のエラーレスポンス生成クラス。
 *
 * @author Nabu Rakutaro
 * @noinspection unpublishedApi
 */
public class ExampleErrorResponseBuilder extends ErrorResponseBuilder {

    /** {@link BodyConverter} */
    private List<BodyConverter> bodyConverters = new ArrayList<>();

    /**
     * エラーレスポンスを生成する。
     * <p/>
     * 発生したエラーが{@link NoDataException}の場合は{@code 404}、
     * {@link OptimisticLockException}の場合は{@code 409}を生成する。
     * それ以外のエラーの場合には、{@link ErrorResponseBuilder#build(HttpRequest, ExecutionContext, Throwable)}に処理を委譲する。
     *
     * @param request {@link HttpRequest}
     * @param context {@link ExecutionContext}
     * @param throwable 発生したエラーの情報
     * @return エラーレスポンス
     */
    @Override
    public HttpResponse build(HttpRequest request, ExecutionContext context, Throwable throwable) {
        if (throwable instanceof NoDataException) {
            return new HttpResponse(404);
        } else if (throwable instanceof OptimisticLockException) {
            return new HttpResponse(409);
        } else if (throwable instanceof ApplicationException) {
            return createErrorResponse(context, (ApplicationException) throwable);
        } else if (throwable instanceof ResponseException) {
            return createErrorResponse(context, (ResponseException) throwable);
        } else {
            return super.build(request, context, throwable);
        }
    }

    /**
     * エラー時のレスポンスを生成する。
     * 発生した例外{@link ApplicationException}からメッセージを取得し、
     * {@link HttpResponse}に設定する。ステータスコードは400が設定される。
     *
     * @param context 実行コンテキスト
     * @param e 例外
     * @return レスポンス
     */
    private HttpResponse createErrorResponse(ExecutionContext context, ApplicationException e) {
        BodyConverter converter = findConverterToProduce(context);
        List<String> messages = e.getMessages()
                                .stream()
                                .map(Message::formatMessage)
                                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("messages", messages);
        return converter.write(map, context)
                        .setStatusCode(400);
    }


    /**
     * エラー時のレスポンスを生成する。
     * 発生した例外{@link ResponseException}からステータスコードとレスポンスオブジェクトを取得し、
     * {@link HttpResponse}に設定する。
     *
     * @param context 実行コンテキスト
     * @param e 例外
     * @return レスポンス
     */
    private HttpResponse createErrorResponse(ExecutionContext context, ResponseException e) {
        BodyConverter converter = findConverterToProduce(context);
        Object responseObject = e.getResponseObject();
        return converter.write(responseObject, context)
                        .setStatusCode(e.getStatusCode());
    }

    private BodyConverter findConverterToProduce(ExecutionContext context) {
        String producesMediaType = getMediaTypeToProduce(context);
        return findConverter(producesMediaType);
    }

    private String getMediaTypeToProduce(ExecutionContext context) {
        JaxRsContext jaxRsContext = JaxRsContext.get(context);
        String mediaType = jaxRsContext.getProducesMediaType();
        if (StringUtil.isNullOrEmpty(mediaType)) {
            throw new IllegalStateException("could not find media type to produce. [" + mediaType + "]");
        }
        return mediaType;
    }

    /**
     * メディアタイプを変換するための{@link BodyConverter}を取得する。
     *
     * 変換対象の{@link BodyConverter}が存在しない場合は、{@link Status#UNSUPPORTED_MEDIA_TYPE}を持つ{@link HttpErrorResponse}を送出する。
     *
     * @param mediaType メディアタイプ
     * @return {@link BodyConverter}
     */
    private BodyConverter findConverter(final String mediaType) {
        for (BodyConverter converter : bodyConverters) {
            if (converter.isConvertible(mediaType)) {
                return converter;
            }
        }
        throw new HttpErrorResponse(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
    }

    /**
     * {@link BodyConverter}のリストを設定する。
     *
     * 既に設定されていた{@link BodyConverter}のリストは破棄される。
     *
     * @param bodyConverters {@link BodyConverter}
     */
    public void setBodyConverters(final List<BodyConverter> bodyConverters) {
        this.bodyConverters = Collections.unmodifiableList(bodyConverters);
    }

}

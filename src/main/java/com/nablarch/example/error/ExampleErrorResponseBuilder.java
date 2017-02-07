package com.nablarch.example.error;

import com.nablarch.example.ResponseBuilder;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.ErrorResponseBuilder;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.persistence.OptimisticLockException;
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

        List<String> messages = e.getMessages()
                                 .stream()
                                 .map(Message::formatMessage)
                                 .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("messages", messages);
        return ResponseBuilder.with(context)
                              .setStatusCode(400)
                              .setResponseObject(map)
                              .build();
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
        return ResponseBuilder.with(context)
                              .setStatusCode(e.getStatusCode())
                              .setResponseObject(e.getResponseObject())
                              .build();
    }


}

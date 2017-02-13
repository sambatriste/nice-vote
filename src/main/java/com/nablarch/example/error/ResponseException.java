package com.nablarch.example.error;

import nablarch.fw.web.HttpResponse.Status;

import static nablarch.fw.web.HttpResponse.Status.BAD_REQUEST;

/**
 * エラーレスポンスを返却するための例外クラス。
 */
public class ResponseException extends RuntimeException {

    /** レスポンス出力に使用するオブジェクト */
    private final Object responseObject;

    /** レスポンス出力に使用するHTTPステータス */
    private final Status status;

    /**
     * コンストラクタ。
     * HTTPステータスは400 BAD REQUEST が使用される。
     *
     * @param responseObject レスポンス出力に使用するオブジェクト
     */
    public ResponseException(Object responseObject) {
        this(responseObject, BAD_REQUEST);
    }

    /**
     * コンストラクタ。
     * @param responseObject レスポンス出力に使用するオブジェクト
     * @param status レスポンス出力に使用するHTTPステータス
     */
    public ResponseException(Object responseObject, Status status) {
        this.responseObject = responseObject;
        this.status = status;
    }

    /**
     * レスポンスオブジェクトを取得する。
     *
     * @return レスポンスオブジェクト
     */
    public Object getResponseObject() {
        return responseObject;
    }

    /**
     * HTTPステータスを取得する。
     *
     * @return HTTPステータス
     */
    public Status getStatus() {
        return status;
    }

}

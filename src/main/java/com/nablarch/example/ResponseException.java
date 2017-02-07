package com.nablarch.example;

/**
 * Created by tie301686 on 2017/02/06.
 */
public class ResponseException extends RuntimeException {

    private final Object responseObject;

    private final int statusCode;

    public ResponseException(Object responseObject) {
        this(responseObject, 400);
    }

    public ResponseException(Object responseObject, int statusCode) {
        this.responseObject = responseObject;
        this.statusCode = statusCode;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public int getStatusCode() {
        return statusCode;
    }


}

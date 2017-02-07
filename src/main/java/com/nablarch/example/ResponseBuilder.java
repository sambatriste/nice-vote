package com.nablarch.example;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.StringUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.BodyConverter;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpErrorResponse;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.HttpResponse.Status;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by tie301686 on 2017/02/07.
 * @noinspection unpublishedApi
 */
public class ResponseBuilder {

    public static ResponseBuilder with(ExecutionContext context) {
        return new ResponseBuilder(context);
    }


    private final ExecutionContext executionContext;

    private static List<BodyConverter> getBodyConverters() {
        return SystemRepository.get("bodyConverters");
    }

    private ResponseBuilder(ExecutionContext context) {
        this.executionContext = context;

    }




    private int statusCode = 200;

    public ResponseBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    private Object responseObject = null;
    private String mediaType = MediaType.APPLICATION_JSON;

    public ResponseBuilder setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
        return this;
    }

    public HttpResponse build() {
        BodyConverter converterToProduce = findConverterToProduce();
        HttpResponse httpResponse = converterToProduce.write(responseObject, executionContext);
        httpResponse.setStatusCode(statusCode);
        return httpResponse;
    }
    private BodyConverter findConverterToProduce() {
        String producesMediaType = getMediaTypeToProduce();
        return findConverter(producesMediaType);
    }

    private String getMediaTypeToProduce() {
        JaxRsContext jaxRsContext = JaxRsContext.get(executionContext);
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
    private BodyConverter findConverter(String mediaType) {
        for (BodyConverter converter : getBodyConverters()) {
            if (converter.isConvertible(mediaType)) {
                return converter;
            }
        }
        throw new HttpErrorResponse(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
    }


}

package com.nablarch.example;

import nablarch.core.beans.BeanUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpRequest;

/**
 * Created by kawasaki on 2017/02/12.
 */
public class RequestParamConvertingHandler implements Handler<HttpRequest, Object> {

    @Override
    public Object handle(HttpRequest request, ExecutionContext context) {
        JaxRsContext jaxRsContext = JaxRsContext.get(context);
        RequestParamConverter converter = new RequestParamConverter(request, jaxRsContext);
        converter.convertIfNecessary();
        return context.handleNext(request);
    }

    private static class RequestParamConverter {
        private final HttpRequest httpRequest;
        private final JaxRsContext jaxRsContext;

        RequestParamConverter(HttpRequest httpRequest, JaxRsContext jaxRsContext) {
            this.httpRequest = httpRequest;
            this.jaxRsContext = jaxRsContext;
        }

        boolean shouldConvert() {
            return isGetMethod() &&
                    noContentType() &&
                    noConsumesMediaType() &&
                    hasBeanParameter();
        }

        protected void convertIfNecessary() {
            if (!shouldConvert()) {
                return;
            }

            if (hasAlreadyConvertedRequestObject()) {
                throw new IllegalStateException(
                        "already converted. request object=[" + jaxRsContext.getRequest() + "]");
            }
            Object requestObject = convert();
            jaxRsContext.setRequest(requestObject);
        }

        private Object convert() {
            return BeanUtil.createAndCopy(jaxRsContext.getRequestClass(),
                                          httpRequest.getParamMap());
        }

        private boolean isGetMethod() {
            return httpRequest.getMethod().equalsIgnoreCase("GET");
        }

        private boolean noContentType() {
            return httpRequest.getHeader("Content-Type") == null;
        }

        private boolean noConsumesMediaType() {
            return jaxRsContext.getConsumesMediaType() == null;
        }

        private boolean hasBeanParameter() {
            return jaxRsContext.getRequestClass() != null;
        }
        private boolean hasAlreadyConvertedRequestObject() {
            return jaxRsContext.hasRequest();
        }
    }

}

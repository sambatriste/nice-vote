package com.nablarch.example.validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import nablarch.core.message.Message;
import nablarch.core.validation.ee.ConstraintViolationConverterFactory;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpRequest;

/**
 * Created by tie301686 on 2017/01/16.
 */
public class BeanValidationHandler implements Handler<HttpRequest, Object> {

    private ConstraintViolationConverter<?> converter = new BasicConstraintViolationConverter();

    @Override
    public Object handle(HttpRequest request, ExecutionContext executionContext) {

        JaxRsContext jaxRsContext = JaxRsContext.get(executionContext);

        boolean shouldValidate = jaxRsContext.hasValidAnnotation() && jaxRsContext.hasRequest();
        if (shouldValidate) {
            Set<ConstraintViolation<Object>> constraintViolations = ValidatorUtil.getValidator()
                                                                                 .validate(jaxRsContext.getRequest());

            if (!constraintViolations.isEmpty()) {
                return converter.convert(constraintViolations);
            }
        }
        return executionContext.handleNext(request);
    }


    public interface ConstraintViolationConverter<RET> {
        RET convert(Set<ConstraintViolation<Object>> violations);
    }


    public static class BasicConstraintViolationConverter implements ConstraintViolationConverter<List<String>> {
        @Override
        public List<String> convert(Set<ConstraintViolation<Object>> violations) {
            return toMessages(violations).stream()
                                         .map(Message::formatMessage)
                                         .collect(Collectors.toList());
        }

        private static List<Message> toMessages(Set<ConstraintViolation<Object>> violations) {
            return new ConstraintViolationConverterFactory().create().convert(violations);
        }
    }
}

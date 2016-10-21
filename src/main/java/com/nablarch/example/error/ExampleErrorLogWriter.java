package com.nablarch.example.error;

import nablarch.common.dao.NoDataException;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsErrorLogWriter;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.persistence.OptimisticLockException;

/**
 * Example用のエラーログ出力クラス。
 *
 * @author Nabu Rakutaro
 */
public class ExampleErrorLogWriter extends JaxRsErrorLogWriter {

    @Override
    public void write(HttpRequest request, HttpResponse response, ExecutionContext context, Throwable throwable) {
        if (!(throwable instanceof NoDataException) && !(throwable instanceof OptimisticLockException)) {
            super.write(request, response, context, throwable);
        }

    }
}

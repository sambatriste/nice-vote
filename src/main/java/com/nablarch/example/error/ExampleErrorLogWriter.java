package com.nablarch.example.error;

import com.nablarch.example.ResponseException;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsErrorLogWriter;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.persistence.OptimisticLockException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Example用のエラーログ出力クラス。
 *
 * @author Nabu Rakutaro
 */
public class ExampleErrorLogWriter extends JaxRsErrorLogWriter {

    /** @noinspection blacklistJavaApi*/
    private final Set<Class<? extends Throwable>> excludes
            = new HashSet<>(Arrays.asList(NoDataException.class,
                                          OptimisticLockException.class,
                                          ResponseException.class));

    /** {@inheritDoc} */
    @Override
    public void write(HttpRequest request, HttpResponse response, ExecutionContext context, Throwable throwable) {
        if (isExcluded(throwable)) {
            return;
        }
        super.write(request, response, context, throwable);
    }

    /** {@inheritDoc} */
    @Override
    protected void writeApplicationExceptionLog(HttpRequest request, HttpResponse response, ExecutionContext context, ApplicationException exception) {
        // NOP
    }

    /**
     * ログ出力除外対象の例外であるかどうか判定する。
     *
     * @param throwable 判定対象となる例外
     * @return ログ出力除外対象の例外である場合、真
     */
    protected boolean isExcluded(Throwable throwable) {
        return excludes.contains(throwable.getClass());
    }
}

package com.nablarch.example.error;

import com.nablarch.example.ResponseBuilderTest;
import com.nablarch.example.ResponseBuilderTest.DummyAction;
import com.nablarch.example.SystemRepositoryResource;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.HttpResponse.Status;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * {@link ExampleErrorResponseBuilder}のテストクラス。
 *
 * @noinspection unpublishedApi
 */
public class ExampleErrorResponseBuilderTest {

    @ClassRule
    public static SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource();

    /** テスト対象 */
    ExampleErrorResponseBuilder sut = new ExampleErrorResponseBuilder();

    /**
     * 指定された例外{@link ResponseException}から、{@link HttpResponse}が作成できること。
     */
    @Test
    public void testCreateResponse() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.handleMethod));

        ResponseException exception = new ResponseException(new DummyResponse(), Status.NOT_FOUND);

        HttpResponse httpResponse = sut.createErrorResponse(ctx,
                                                            exception);

        assertThat(httpResponse.getStatusCode(), is(404));
        assertThat(httpResponse.getBodyString(),
                   is("{\"errorCode\":\"E001\",\"message\":\"見つかりませんでした.\"}"));
    }


    public static class DummyAction {

        @Produces(MediaType.APPLICATION_JSON)
        public Object handle() {
            return null;
        }

        static Method handleMethod = get("handle");

        private static Method get(String name) {
            try {
                return ResponseBuilderTest.DummyAction.class.getMethod(name);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static class DummyResponse {
        public String errorCode = "E001";

        public String message = "見つかりませんでした.";
    }

}
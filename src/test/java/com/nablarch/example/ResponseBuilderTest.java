package com.nablarch.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.ClassRule;
import org.junit.Test;

import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsContext;
import nablarch.fw.web.HttpErrorResponse;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.HttpResponse.Status;

/**
 * {@link ResponseBuilder}のテストクラス。
 * @noinspection unpublishedApi
 */
public class ResponseBuilderTest {

    @ClassRule
    public static SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource();

    /**
     * {@link ResponseBuilder#build(Object)}のテスト。
     * 指定したHTTPステータスとレスポンスオブジェクトで、
     * {@link HttpResponse}が作成できること。
     */
    @Test
    public void testBuild() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.handleMethod));

        HttpResponse httpResponse = ResponseBuilder.with(ctx)
                                                   .setHttpStatus(Status.CREATED)
                                                   .build(new DummyResponse());
        assertThat(httpResponse.getStatusCode(), is(Status.CREATED.getStatusCode()));
        assertThat(httpResponse.getBodyString(), is("{\"name\":\"山田\",\"address\":\"大阪\"}"));
    }

    /**
     * {@link ResponseBuilder#buildError(Object)}のテスト。
     */
    @Test
    public void testBuildError() throws NoSuchMethodException {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.handleMethod));

        HttpErrorResponse httpErrorResponse = ResponseBuilder.with(ctx)
                                                             .setHttpStatus(Status.NOT_FOUND)
                                                             .buildError(new DummyResponse());
        HttpResponse httpResponse = httpErrorResponse.getResponse();
        assertThat(httpResponse.getStatusCode(), is(Status.NOT_FOUND.getStatusCode()));
        assertThat(httpResponse.getBodyString(), is("{\"name\":\"山田\",\"address\":\"大阪\"}"));
    }

    /**
     * ExecutionContextがnullの場合、例外が発生すること。
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecutionContextNull() {
        ResponseBuilder.with(null);
    }

    /**
     * {@link ExecutionContext}に{@link JaxRsContext}が設定されていない場合、
     * 例外が発生すること。
     */
    @Test(expected = IllegalStateException.class)
    public void testNoJaxRsContext() {
        ResponseBuilder.with(new ExecutionContext());
    }



    /**
     * レスポンスオブジェクトがnullの時、例外が発生すること。
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildNull() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.handleMethod));
        ResponseBuilder.with(ctx).build(null);
    }

    /**
     * レスポンスオブジェクトがnullの時、例外が発生すること。
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildErrorNull() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.handleMethod));
        ResponseBuilder.with(ctx).buildError(null);
    }

    /**
     * アクションのメソッドにメディアタイプが設定されていない場合、例外が発生すること。
     */
    @Test(expected = IllegalStateException.class)
    public void testNoMediaTypeOnActionMethod() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.noMediaTypeMethod));
        ResponseBuilder.with(ctx).build(new DummyResponse());
    }

    /**
     * メディアタイプに対応する{@link nablarch.fw.jaxrs.BodyConverter}が見つからない場合、
     * 例外が発生すること。
     */
    @Test
    public void testNoSuitableConverter() {
        ExecutionContext ctx = new ExecutionContext();
        JaxRsContext.set(ctx, new JaxRsContext(DummyAction.noSuitableConverter));
        try {
            ResponseBuilder.with(ctx).build(new DummyResponse());
            fail();
        } catch (HttpErrorResponse e) {
            assertThat(e.getResponse().getStatusCode(), is(415));
        }
    }


    public static class DummyAction {

        @Produces(MediaType.APPLICATION_JSON)
        public Object handle() {
            return null;
        }

        @Produces(MediaType.APPLICATION_SVG_XML)
        public Object noSuitableConverter() {
            return null;
        }

        public Object noMediaType() {
            return null;
        }

        static Method handleMethod = get("handle");

        static Method noMediaTypeMethod = get("noMediaType");

        static Method noSuitableConverter = get("noSuitableConverter");

        private static Method get(String name) {
            try {
                return DummyAction.class.getMethod(name);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static class DummyResponse {
        public String name = "山田";

        public String address = "大阪";
    }
}
package com.nablarch.example.action;

import com.nablarch.example.entity.Agreement;
import com.nablarch.example.entity.Opinion;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;
import nablarch.fw.web.HttpResponse;

import java.io.Serializable;

/**
 * Created by kawasaki on 1/16/17.
 */
public class AgreementAction {

    /**
     * 意見({@link Opinion}に対して賛同する。
     *
     * @param form フォーム
     * @return レスポンス
     */

    public HttpResponse agree(AgreementForm form) {
        Agreement agreement = BeanUtil.createAndCopy(Agreement.class, form);
        UniversalDao.insert(agreement);
        return new HttpResponse(201);
    }

    public static class AgreementForm implements Serializable {

        /** ID */
        @Required
        @Domain("id")
        public String opinionId;

        /** ID */
        @Required
        @Domain("id")
        public String agreementId;

    }
}

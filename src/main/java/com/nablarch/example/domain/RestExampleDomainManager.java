package com.nablarch.example.domain;

import nablarch.core.validation.ee.DomainManager;

/**
 * {@link RestExampleDomain}のマネージャクラス。
 *
 * @author Nabu Rakutaro
 */
public class RestExampleDomainManager implements DomainManager<RestExampleDomain> {
    @Override
    public Class<RestExampleDomain> getDomainBean() {
        return RestExampleDomain.class;
    }
}

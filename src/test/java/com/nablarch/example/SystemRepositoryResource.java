package com.nablarch.example;

import nablarch.core.repository.SystemRepository;
import nablarch.core.repository.di.DiContainer;
import nablarch.core.repository.di.config.xml.XmlComponentDefinitionLoader;
import org.junit.rules.ExternalResource;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class SystemRepositoryResource extends ExternalResource {

    private final String path;

    public SystemRepositoryResource() {
        this("rest-boot.xml");
    }

    public SystemRepositoryResource(String path) {
        this.path = path;
    }

    @Override
    protected void before() throws Throwable {
        loadXmlFile(path);
    }

    private void loadXmlFile(String path) {
        XmlComponentDefinitionLoader xmlComponentDefinitionLoader = new XmlComponentDefinitionLoader(path);
        DiContainer diContainer = new DiContainer(xmlComponentDefinitionLoader);

        SystemRepository.clear();
        SystemRepository.load(diContainer);
    }

    @Override
    protected void after() {
        SystemRepository.clear();
    }
}

package com.nablarch.example;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.rules.ExternalResource;

import nablarch.core.repository.ObjectLoader;
import nablarch.core.repository.SystemRepository;
import nablarch.core.repository.di.DiContainer;
import nablarch.core.repository.di.config.xml.XmlComponentDefinitionLoader;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class SystemRepositoryResource extends ExternalResource {

    private static final String DEFAULT_COMPONENT_DEFINITION_FILE = "rest-boot.xml";

    private final String path;

    public SystemRepositoryResource() {
        this(DEFAULT_COMPONENT_DEFINITION_FILE);
    }
    private static final DiContainerCache diContainerCache = new DiContainerCache(8);

    public SystemRepositoryResource(String path) {
        this.path = path;
    }

    @Override
    protected void before() throws Throwable {
        SystemRepository.clear();
        SystemRepository.load(diContainerCache.get(path));
    }

    @Override
    protected void after() {
        SystemRepository.clear();
    }


    private static class DiContainerCache {

        private final LRUMap<String, ObjectLoader> cache;


        DiContainerCache(int maxCapacity) {
            cache = new LRUMap<>(maxCapacity);
        }

        ObjectLoader get(String path) {
            return cache.computeIfAbsent(path, this::create);
        }

        ObjectLoader create(String path) {
            XmlComponentDefinitionLoader xmlComponentDefinitionLoader = new XmlComponentDefinitionLoader(path);
            return new DiContainer(xmlComponentDefinitionLoader);
        }
    }

    private static class LRUMap<K, V> extends LinkedHashMap<K, V> {
        private final int maxCapacity;

        public LRUMap(int maxCapacity) {
            super(maxCapacity + 1, 0.75f, true);
            this.maxCapacity = maxCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Entry<K, V> eldest) {
            return size() > maxCapacity;
        }
    }
}

package org.hub.gateway.config;

import lombok.Getter;

@Getter
public enum CacheType {
    FEED_RECOMMENDS("feed_recommends"),
    FEED_PAGE_NUMBER("feed_page_number"),
    MAX_PAGE_NUMBER("max_page_number"),
    REGION("region");

    private final String name;
    private final Integer expireAfterWrite;
    private final Integer maximumSize;

    CacheType(String name) {
        this.name = name;
        this.expireAfterWrite = ConstConfig.DEFAULT_TTL_SEC;
        this.maximumSize = ConstConfig.DEFAULT_MAX_SIZE;
    }

    static class ConstConfig {
        static final Integer DEFAULT_TTL_SEC = 600;
        static final Integer DEFAULT_MAX_SIZE = 10240;
    }
}

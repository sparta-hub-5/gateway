package org.hub.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.uri")
public class UriProperties {

    /**
     * 외부 데모용 httpbin 기본 URL
     * ex) http://httpbin.org:80
     */
    private String httpbin = "http://httpbin.org:80";

    public String getHttpbin() { return httpbin; }
    public void setHttpbin(String httpbin) { this.httpbin = httpbin; }
}
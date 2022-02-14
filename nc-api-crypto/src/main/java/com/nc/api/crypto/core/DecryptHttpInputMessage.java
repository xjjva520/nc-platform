package com.nc.api.crypto.core;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.core
 */
public class DecryptHttpInputMessage implements HttpInputMessage {
    private final InputStream body;
    private final HttpHeaders headers;

    public InputStream getBody() {
        return this.body;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public DecryptHttpInputMessage(final InputStream body, final HttpHeaders headers) {
        this.body = body;
        this.headers = headers;
    }
}

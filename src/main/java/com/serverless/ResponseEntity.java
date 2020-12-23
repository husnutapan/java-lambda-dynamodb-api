package com.serverless;

import java.util.Collections;
import java.util.Map;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseEntity {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    public ResponseEntity(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final ObjectMapper objectMapper = new ObjectMapper();
        private int statusCode = 200;
        private Map<String, String> headers = Collections.emptyMap();
        private Object objectBody;

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setObjectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }


        public ResponseEntity build() {
            String body = "";
            try {
                body = objectMapper.writeValueAsString(objectBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new ResponseEntity(statusCode, body, headers);
        }
    }
}

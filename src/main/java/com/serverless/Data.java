package com.serverless;

import java.util.Map;

public class Data {
    private final String message;
    private final Map<String,Object> input;

    public Data(String message, Map<String, Object> input) {
        this.message = message;
        this.input = input;
    }
}

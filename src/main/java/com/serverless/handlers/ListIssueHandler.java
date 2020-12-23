package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.Data;
import com.serverless.ResponseEntity;
import com.serverless.models.Issues;
import com.serverless.util.IssueFunctionality;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListIssueHandler implements RequestHandler<Map<String, Object>, ResponseEntity> {
    @Override
    public ResponseEntity handleRequest(Map<String, Object> inputData, Context context) {
        try {
            List<Issues> issues = new IssueFunctionality("issues").list();
            return ResponseEntity.builder().setStatusCode(200).setHeaders(Collections.emptyMap()).setObjectBody(issues).build();
        } catch (Exception e) {
            Data errorResponseData = new Data("Error listing issues", inputData);
            return ResponseEntity.builder().setStatusCode(500).setObjectBody(errorResponseData).build();
        }

    }
}

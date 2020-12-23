package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ResponseEntity;
import com.serverless.Data;
import com.serverless.models.Issues;
import com.serverless.util.IssueFunctionality;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class CreateIsssueHandler implements RequestHandler<Map<String, Object>, ResponseEntity> {

    @Override
    public ResponseEntity handleRequest(Map<String, Object> input, Context context) {
        try {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

            Issues issues = new Issues();
            issues.setTitle(body.get("title").asText());
            issues.setAssignedUser(body.get("assignedUser").asText());
            issues.setExplain(body.get("explain").asText());
            new IssueFunctionality("issues", issues).save();
            return ResponseEntity.builder()
                    .setStatusCode(200)
                    .setObjectBody(issues)
                    .setHeaders(Collections.emptyMap())
                    .build();

        } catch (Exception ex) {
            Data responseErrorBody = new Data("Error in saving product: ", input);
            return ResponseEntity.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseErrorBody)
                    .setHeaders(Collections.emptyMap())
                    .build();
        }

    }
}

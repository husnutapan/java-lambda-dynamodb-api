package com.serverless.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.serverless.models.Issues;

import java.util.List;

public class IssueFunctionality {

    private static Database adapterDb;
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;
    private final Issues issues;
    private final String tableName;

    public IssueFunctionality(String tableName) {
        this(tableName, new Issues());
    }

    public IssueFunctionality(String tableName, Issues issues) {
        this.issues = issues;
        this.tableName = tableName;
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
        this.adapterDb = Database.generateDbInstance();
        this.client = adapterDb.getClient();
        this.mapper = adapterDb.createMapper(mapperConfig);
    }

    boolean ifDynamoDBExist() {
        return this.client.describeTable(tableName).getTable().getTableStatus().equals("ACTIVE");
    }

    public List<Issues> list() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Issues> data = this.mapper.scan(Issues.class, scanExpression);
        return data;
    }

    public void save() {
        this.mapper.save(issues);
    }
}

package com.serverless.util;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class Database {

    private final AmazonDynamoDB client;

    private DynamoDBMapper mapper;

    private static Database database = null;

    public Database() {
        this.client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_EAST_1).build();
    }


    static Database generateDbInstance() {
        synchronized (Database.class) {
            if (database == null) database = new Database();
            return database;
        }
    }

    public AmazonDynamoDB getClient() {
        return client;
    }


    DynamoDBMapper createMapper(DynamoDBMapperConfig mapperConfig) {
        if (client != null)
            mapper = new DynamoDBMapper(client, mapperConfig);
        return mapper;
    }
}

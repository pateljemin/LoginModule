package com.login.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created By : Jemin
 * Date       : 4/6/18
 * Time       : 12:29 AM
 */
@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

    private static final String HOST = "127.0.0.1";
    private static final String DB_NAME = "global";

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(HOST, 27017);
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }
}

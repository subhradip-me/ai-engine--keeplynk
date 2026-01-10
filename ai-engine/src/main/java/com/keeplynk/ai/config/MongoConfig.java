package com.keeplynk.ai.config;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mongodb.ConnectionString;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer(Environment env) {
        String mongoUrl = env.getProperty("MONGO_URL", 
                          env.getProperty("MONGODB_URI", 
                          "mongodb://localhost:27017/keeplynk_ai"));
        
        System.out.println("=== MongoConfig: Using MongoDB URL: " + mongoUrl);
        
        return builder -> builder.applyConnectionString(new ConnectionString(mongoUrl));
    }
}

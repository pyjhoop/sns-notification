package com.study.config;

import com.mongodb.ConnectionString;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Configuration
public class LocalMongoConfig {

    private static final String MONGODB_IMAGE_NAME = "mongo:5.0";
    private static final int MONGODB_INNER_PORT = 27017;
    private static final String DATABASE_NAME = "notification";
    private static final GenericContainer MONGO = createMongoInstance();

    private static GenericContainer createMongoInstance() {
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
                .withExposedPorts(MONGODB_INNER_PORT)
                .withReuse(true);
    }

    @PostConstruct
    public void startMongo() {
        try {
            MONGO.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void stopMongo() {
        try {
            MONGO.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // 여기서 커넥션 스트링 생성후에 몽고 팩토리 클래스 빈으로 등록할거야.

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory mongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }


    private ConnectionString connectionString() {
        String host = MONGO.getHost();
        int port = MONGO.getMappedPort(MONGODB_INNER_PORT);
        return new ConnectionString(String.format("mongodb://%s:%d/%s", host, port, DATABASE_NAME));
    }
}

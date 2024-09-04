package demo.spring.sandbox.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MultiTenantMongoConfig {

    @Autowired
    private Environment env;

    @Value("#{'${mongo.tenants}'.split(',')}")
    private final List<String> tenants;

    private final Map<String, MongoTemplate> tenantTemplateMap = new HashMap<>();

    @PostConstruct
    public void initializeMongoTemplates() {
        // Load tenants from the application properties
        for (String tenant : tenants) {
            String databaseName = env.getProperty("multi-tenant.mongo.tenants[" + tenant + "].database");
            String uri = env.getProperty("mongo.uri");

            // Log each database name for debugging
            log.debug("Creating MongoTemplate for tenant: {} with database: {}", tenant, databaseName);

            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient, databaseName);
            MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);

            tenantTemplateMap.put(tenant, mongoTemplate);
        }
    }

    public MongoTemplate getMongoTemplateForTenant(String tenantId) {
        return tenantTemplateMap.get(tenantId);
    }
}


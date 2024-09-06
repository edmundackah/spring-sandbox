package demo.spring.sandbox.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MultiTenantMongoConfig {

    @Value("${mongo.uri}")
    private String uri;

    @Autowired
    private final MultiTenantProperties tenants;

    private final Map<String, MongoTemplate> tenantTemplateMap = new HashMap<>();

    @PostConstruct
    public void initializeMongoTemplates() {
        // Load tenants from the application properties
        tenants.getTenants().forEach((t) -> {
            log.debug("Creating MongoTemplate for tenant: {} with database: {}", t.getTenant(), t.getDatabase());

            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient, t.getDatabase());
            MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);

            tenantTemplateMap.put(t.getTenant(), mongoTemplate);
        });
    }

    public MongoTemplate getMongoTemplateForTenant(String tenantId) {
        return tenantTemplateMap.get(tenantId);
    }
}


package demo.spring.sandbox.service;

import demo.spring.sandbox.config.MultiTenantMongoConfig;
import demo.spring.sandbox.config.TenantContext;
import demo.spring.sandbox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private MultiTenantMongoConfig mongoConfig;

    public User saveUser(User user) {
        // Get the correct MongoTemplate for the tenant
        String tenant = TenantContext.getTenant();
        MongoTemplate mongoTemplate = mongoConfig.getMongoTemplateForTenant(tenant);

        // Save the user using the tenant-specific MongoTemplate
        return mongoTemplate.save(user);
    }

    public List<User> getAllUsers(String tenantId) {
        // Get the correct MongoTemplate for the tenant
        MongoTemplate mongoTemplate = mongoConfig.getMongoTemplateForTenant(tenantId);

        // Retrieve users from the tenant-specific database
        return mongoTemplate.findAll(User.class);
    }
}

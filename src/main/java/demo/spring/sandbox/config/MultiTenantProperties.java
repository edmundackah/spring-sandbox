package demo.spring.sandbox.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "multi-tenant.mongo")
public class MultiTenantProperties {

    @NotEmpty(message = "The tenants list cannot be empty")
    private List<Tenant> tenants;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Tenant {
        private String tenant;
        private String database;
    }
}

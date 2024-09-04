package demo.spring.sandbox.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the tenant from the header
        String tenant = request.getHeader("X-Tenant-ID");

        // Log the tenant for debugging purposes
        log.debug("Tenant ID from header: {}", tenant);

        // Set the tenant in the TenantContext (or use a default if missing)
        if (tenant != null && !tenant.isEmpty()) {
            TenantContext.setTenant(tenant);
        } else {
            throw new IllegalArgumentException("No Tenant ID found in header.");
        }

        // Proceed with the filter chain
        try {
            filterChain.doFilter(request, response);
        } finally {
            // Clear the TenantContext at the end of the request
            TenantContext.clear();
        }
    }
}

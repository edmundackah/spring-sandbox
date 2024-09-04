package demo.spring.sandbox.controller;

import demo.spring.sandbox.model.User;
import demo.spring.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user,
                                           @RequestHeader("X-Tenant-ID") String tenantId) {
        // The tenant header is automatically handled by TenantFilter,
        // so there's no need to manually handle tenant context here.

        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}

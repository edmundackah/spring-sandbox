package demo.spring.sandbox.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "userDetails")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email;
    private String name;
}

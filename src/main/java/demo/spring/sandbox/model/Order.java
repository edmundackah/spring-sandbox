package demo.spring.sandbox.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private Integer quantity;
}

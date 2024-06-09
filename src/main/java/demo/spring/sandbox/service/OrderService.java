package demo.spring.sandbox.service;

import demo.spring.sandbox.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private static final String ORDER_TOPIC = "order_topic";

    @Autowired
    private KafkaTemplate<String, Order> kafkaOrderTemplate;

    public void sendOrder(Order order) {
        kafkaOrderTemplate.send(ORDER_TOPIC, order);
    }

}

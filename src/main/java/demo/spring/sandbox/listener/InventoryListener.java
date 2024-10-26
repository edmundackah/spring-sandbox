package demo.spring.sandbox.listener;

import demo.spring.sandbox.model.Order;
import demo.spring.sandbox.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryListener {

    @Autowired
    private final InventoryService inventoryService;

    @KafkaListener(
            id = "inventoryListener",
            topics = "order-topic",
            groupId = "${kafka.order.consumer.group.id}",
            containerFactory = "${kafka.order.listener.container}",
            autoStartup = "true"
    )
    public void listen(Order order) {
        System.out.println("Received message: " + order);
        inventoryService.stockCheck(order.getOrderId());
    }
}

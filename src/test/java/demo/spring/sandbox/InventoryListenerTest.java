package demo.spring.sandbox;

import demo.spring.sandbox.config.KafkaTestConfig;
import demo.spring.sandbox.model.Order;
import demo.spring.sandbox.stubs.InventoryClientStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@Import(KafkaTestConfig.class)
@EmbeddedKafka(partitions = 1, topics = {"order-topic"})
public class InventoryListenerTest {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, Order> consumerFactory;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void testOrderListener() throws Exception {
        InventoryClientStub.stubInventoryCall("SK123232", 1);

        Order order = Order.builder()
                .orderId("SK123232")
                .quantity(5)
                .build();

        kafkaTemplate.send("order-topic", order);

        latch.await(5, TimeUnit.SECONDS);

        verify(1, getRequestedFor(urlEqualTo("/item/SK123232"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}

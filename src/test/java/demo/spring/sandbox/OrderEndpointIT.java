package demo.spring.sandbox;

import demo.spring.sandbox.config.KafkaProducerTestConfig;
import demo.spring.sandbox.model.Order;
import demo.spring.sandbox.serializer.OrderDeserializer;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@EmbeddedKafka(partitions = 1, topics = { "order_topic" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(KafkaProducerTestConfig.class)
public class OrderEndpointIT {

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroup;

    @Value("${kafka.topic.order}")
    private String orderTopic;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ProducerFactory<String, Order> producerFactory;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        String baseURI = StringUtils.join("http://localhost:", port);

        RestAssured.baseURI = baseURI;
    }

    @Test
    public void GivenValidInfo_ShouldBeInStock() {
        Order order = new Order();
        order.setOrderId("QK12456");
        order.setQuantity(5);

        RestAssured.baseURI = StringUtils.join("http://localhost:", port);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(consumerGroup, "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class);

        DefaultKafkaConsumerFactory<String, Order> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, Order> consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, orderTopic);
        ConsumerRecord<String, Order> record = KafkaTestUtils.getSingleRecord(consumer, orderTopic);

        assertEquals(record.value(), order);
    }

}

package demo.spring.sandbox.config;

import demo.spring.sandbox.model.Order;
import demo.spring.sandbox.serializer.OrderDeserializer;
import demo.spring.sandbox.serializer.OrderSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class KafkaTestConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.order.consumer.group.id}")
    private String orderConsumerGroupId;

    @Primary
    @Bean("TestProducerFactory")
    public ProducerFactory<String, Order> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Primary
    @Bean("TestKafkaTemplate")
    public KafkaTemplate<String, Order> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Primary
    @Bean("TestConsumerFactory")
    public ConsumerFactory<String, Order> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, orderConsumerGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }


}

package io.fraud.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaService {
    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageConsumer messageConsumer;
    ObjectMapper objectMapper = new ObjectMapper();
    ProjectConfig projectConfig = ConfigFactory.create(ProjectConfig.class);


    public KafkaService() {
        this.kafkaMessageProducer = new KafkaMessageProducer(projectConfig.kafkaBrokers());
        this.messageConsumer = new KafkaMessageConsumer(projectConfig.kafkaBrokers());
    }

    public KafkaMessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public RecordMetadata send(String message) {
        return kafkaMessageProducer.send("test", message);
    }

    public RecordMetadata send(String topic, String message) {
        return kafkaMessageProducer.send(topic, message);
    }

    @SneakyThrows
    public RecordMetadata send(String topic, Object message) {
        return kafkaMessageProducer.send(topic, objectMapper.writeValueAsString(message));
    }

    @SneakyThrows
    public RecordMetadata send(Object message) {
        return kafkaMessageProducer.send(projectConfig.queuinfTopic(), objectMapper.writeValueAsString(message));
    }


    public void subscribe(String topic) {
        messageConsumer.subscribe(topic);
        messageConsumer.consume();
    }

    public void subscribeLegit() {
        subscribe(projectConfig.legitTopic());

    }

    public void subscribeFraud() {
        subscribe(projectConfig.fraudTopic());
    }


    public KafkaRecord waitForMessages(String message) {
        return messageConsumer.waitForMessages(message);
    }
}

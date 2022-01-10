package io.fraud.kafka.producer;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    private static KafkaProducer<String, String> kafkaProducer;
    private final String bootStrapServer;

    public KafkaMessageProducer(String bootStrapServer) {
        this.bootStrapServer = bootStrapServer;
    }

    public KafkaMessageProducer createProducer() {
        Properties properties = createProducerProperties();
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer<>(properties);
        }
        return this;
    }

    private Properties createProducerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootStrapServer);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    @SneakyThrows
    public RecordMetadata send(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
//        try {
        return kafkaProducer.send(record).get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}

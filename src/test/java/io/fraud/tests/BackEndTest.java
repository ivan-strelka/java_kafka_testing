package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BackEndTest {

    @Test
    void testCanWriteMessageToQueTransaction() {
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer("localhost:9092");
        kafkaMessageProducer.createProducer();
        KafkaMessageConsumer messageConsumer = new KafkaMessageConsumer("localhost:9092");
        messageConsumer.subscribe("test");
        messageConsumer.consume();

        kafkaMessageProducer.send("test", "Hello from Java 8");

        KafkaRecord receivedRecords = messageConsumer.waitForMessages("Hello from Java 8");

        System.out.println(receivedRecords);
        assertThat(receivedRecords).isNotNull();

    }


}

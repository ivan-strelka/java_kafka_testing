package io.fraud.tests;

import io.fraud.kafka.producer.KafkaMessageProducer;
import org.junit.jupiter.api.Test;

public class BackEndTest {

    @Test
    void testCanWriteMessageToQueTransaction() {
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer("localhost:9092");
        kafkaMessageProducer.createProducer();
        kafkaMessageProducer.send("test", "Hello from Java");
    }


}

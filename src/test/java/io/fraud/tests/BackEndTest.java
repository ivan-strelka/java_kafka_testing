package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.messages.DealMessage;
import io.fraud.kafka.messages.GeneratorMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BackEndTest extends BaseTest {

    @Test
    void testCanWriteMessageToQueTransaction() {
        kafkaService.subscribe("test");
        kafkaService.send("test", "Hello from Java 8");
        KafkaRecord receivedRecords = kafkaService.waitForMessages("Hello from Java 8");
        System.out.println(receivedRecords);
        assertThat(receivedRecords).isNotNull();

    }

    @Test
    void testAppCanProcessValidMessageLegit() {

        GeneratorMessage generatorMessage = new GeneratorMessage();
        generatorMessage.setStatus("available");
        generatorMessage.setId(Integer.parseInt(RandomStringUtils.randomNumeric(5)));
        generatorMessage.setName("doggie");

        kafkaService.subscribeLegit();

        kafkaService.send("queuing.transactions", generatorMessage);

        DealMessage receivedRecords = kafkaService.waitForMessages(generatorMessage.getName()).valueAs(DealMessage.class);

        assertThat(receivedRecords.getId()).isEqualTo(generatorMessage.getId());
        assertThat(receivedRecords.getName()).isEqualTo(generatorMessage.getName());
        assertThat(receivedRecords.getStatus()).isEqualTo(generatorMessage.getStatus());


    }

    @Test
    void testAppCanProcessValidMessageFraud() {

        GeneratorMessage generatorMessage = new GeneratorMessage();
        generatorMessage.setStatus("available");
        generatorMessage.setId(Integer.parseInt(RandomStringUtils.randomNumeric(5)));
        generatorMessage.setName("doggie");

        kafkaService.subscribeFraud();

        kafkaService.send(generatorMessage);

        DealMessage receivedRecords = kafkaService.waitForMessages(generatorMessage.getName()).valueAs(DealMessage.class);

        assertThat(receivedRecords.getId()).isEqualTo(generatorMessage.getId());
        assertThat(receivedRecords.getName()).isEqualTo(generatorMessage.getName());
        assertThat(receivedRecords.getStatus()).isEqualTo(generatorMessage.getStatus());


    }

    @Test
    void testAppCanProcessValidMessageWithFakerJtwig() {
        kafkaService.subscribeFraud();
        GeneratorMessage generatorMessage = kafkaService.send();
        DealMessage receivedRecords = kafkaService.waitForMessages(generatorMessage.getName())
                .valueAs(DealMessage.class);

        assertThat(receivedRecords.getId()).isEqualTo(generatorMessage.getId());
        assertThat(receivedRecords.getName()).isEqualTo(generatorMessage.getName());
        assertThat(receivedRecords.getStatus()).isEqualTo(generatorMessage.getStatus());


    }

    @Test
    void AppCanSaveFraudMessToDataBase() {

    }
}

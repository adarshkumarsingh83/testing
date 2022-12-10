package com.espark.adarsh;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootEmbeddedKafkaApplicationTests extends KafkaAbstractTestContext {


    @Test
    void contextLoads() {
    }

    @Test
    void kafkaSetup_withTopic_ensureSendMessageIsReceived() throws Exception {
        // Arrange
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        Producer<String, String> producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();

        // Act
        producer.send(new ProducerRecord<>(TOPIC, "epsark-aggredate-id", "{\"event\":\"Espark Test Event\"}"));
        producer.flush();

        // Assert
        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo("epsark-aggredate-id");
        assertThat(singleRecord.value()).isEqualTo("{\"event\":\"Espark Test Event\"}");
    }

}

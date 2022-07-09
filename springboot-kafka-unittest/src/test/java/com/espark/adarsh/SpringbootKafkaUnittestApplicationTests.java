package com.espark.adarsh;

import com.espark.adarsh.event.ApplicationKafkaEvent;
import com.espark.adarsh.service.KafkaMessageProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;

@EmbeddedKafka
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringbootKafkaUnittestApplicationTests {

    @Value("${kafka.test.topic}")
    protected String TOPIC;

    @Value("${kafka.test.group}")
    protected String group;

    @Autowired
    private KafkaMessageProducerService kafkaMessageProducerService;

    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaMessageListenerContainer<String, ApplicationKafkaEvent> container;

    private BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps(group, "false", embeddedKafkaBroker));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer(consumerFactory, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @Test
    public void it_should_send_updated_brand_event() throws InterruptedException, IOException {
        ApplicationKafkaEvent updatedBrandEvent = new ApplicationKafkaEvent(
                "BrandMapCreatedEvent");
        kafkaMessageProducerService.send(updatedBrandEvent);

        ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(updatedBrandEvent);

        //assertThat(received, hasValue(json));

        assertThat(received).has(key(null));
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

}

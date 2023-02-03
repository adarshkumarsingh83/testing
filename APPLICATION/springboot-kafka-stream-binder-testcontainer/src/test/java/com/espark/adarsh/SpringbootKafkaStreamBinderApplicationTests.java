package com.espark.adarsh;

import com.espark.adarsh.bean.Message;
import com.espark.adarsh.config.ApplicationConfig;
import com.espark.adarsh.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;


@Testcontainers
@SpringBootTest(classes = { SpringbootKafkaStreamBinderApplication.class})
class SpringbootKafkaStreamBinderApplicationTests {


    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @Autowired
    TimeUtil timeUtil;

    @Autowired
    private StreamBridge streamBridge;


    @DynamicPropertySource
    static void registerKafka(DynamicPropertyRegistry registry) {
        final String url = kafka.getNetworkAliases().get(0) + ":9092";
        registry.add("spring.cloud.stream.kafka.binder.brokers", () -> new String[]{url});
    }

    @Test
    void contextLoads() {
      //  streamBridge.send(ApplicationConfig.OUT_CHANNEL, new Message("PUBLISHER:-> Welcome to the espark " + timeUtil.getTime()));
    }

}

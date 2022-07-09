package com.espark.adarsh.service;

import com.espark.adarsh.event.ApplicationKafkaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducerService {

    @Value("${kafka.test.topic}")
    protected String TOPIC;

    private final KafkaTemplate<String, ApplicationKafkaEvent> kafkaTemplate;

    @Autowired
    public KafkaMessageProducerService(KafkaTemplate<String, ApplicationKafkaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ApplicationKafkaEvent updatedBrandEvent) {
        kafkaTemplate.send(TOPIC, updatedBrandEvent);
    }
}
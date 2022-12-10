package com.espark.adarsh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SequenceOrderTest {

    @Test
    @Order(1)
    void firstTest() {
        log.info("SequenceOrderTest firstTest()");
    }

    @Test
    @Order(2)
    void secondTest() {
        log.info("SequenceOrderTest secondTest()");
    }

    @Test
    @Order(3)
    void thirdTest() {
        log.info("SequenceOrderTest thirdTest()");
    }
}

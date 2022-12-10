package com.espark.adarsh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {


    @BeforeEach
    void beforeEach() {
        log.info("SpringbootH2DbApplicationTests beforeEach()");
    }

    @AfterEach
    void afterEach() {
        log.info("SpringbootH2DbApplicationTests afterEach()");
    }

    @BeforeAll
    void beforeAll() {
        log.info("SpringbootH2DbApplicationTests beforeAll()");
    }

    @AfterAll
    void afterAll() {
        log.info("SpringbootH2DbApplicationTests afterAll()");
    }

    @Test
    @Tags(@Tag("sample context loading"))
    void contextLoads() {
        log.info("SpringbootH2DbApplicationTests contextLoads()");
    }


    @Test
    @Tags(@Tag("disabled test"))
    @Disabled
    void disableTest() {
        log.info("SpringbootH2DbApplicationTests disableTest()");
    }

}

package com.espark.adarsh;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationTests {

    public ApplicationTests() {

    }

    @Before
    public void beforeEach() {
        System.out.println("SpringbootH2DbApplicationTests Before()");
    }

    @After
    public void afterEach() {
        System.out.println("SpringbootH2DbApplicationTests After()");
    }

    @BeforeClass
    public static void beforeAll() {
        System.out.println("SpringbootH2DbApplicationTests BeforeClass()");
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("SpringbootH2DbApplicationTests AfterClass()");
    }

    @Test
    public void contextLoads() {
        System.out.println("SpringbootH2DbApplicationTests contextLoads()");
    }


    @Test
    @Ignore
    public void disableTest() {
        System.out.println("SpringbootH2DbApplicationTests disableTest()");
    }

}

package com.espark.adarsh;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@Provider("service_provider")
@PactFolder("pacts")
public abstract class SpringbootContractServerApplicationTests {
    private static ConfigurableWebApplicationContext application;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeAll
    public static void start() {
        application = (ConfigurableWebApplicationContext) SpringApplication.run(SpringbootContractServerApplication.class);
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8082, "/spring-rest"));
    }


    @State("Test GET employee with id ")
    public void toGetState() { }


}

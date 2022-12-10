package com.espark.adarsh.test;

import com.espark.adarsh.service.PersonService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PersonServiceTest {

    private PersonService personService;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        System.out.println("Instantiating PersonService");
        personService = new PersonService();
    }

    @Test
    @DisplayName("Should Create Person")
    public void shouldCreatePerson() {
        personService.createPerson("Adarsh", "Kumar", "0123456789");
        assertFalse(personService.getAllPersons().isEmpty());
        assertEquals(1, personService.getAllPersons().size());
    }

    @Test
    @DisplayName("Should Not Create Person When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            personService.createPerson(null, "Kumar", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Person When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            personService.createPerson("Adarsh", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Person When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            personService.createPerson("Adarsh", "Kumar", null);
        });
    }

    @Test
    @DisplayName("Should Create Person")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Should Run only on MAC")
    public void shouldCreatePersonOnMAC() {
        personService.createPerson("Adarsh", "Kumar", "0123456789");
        assertFalse(personService.getAllPersons().isEmpty());
        assertEquals(1, personService.getAllPersons().size());
    }

    @Test
    @DisplayName("Test Person Creation on Developer Machine")
    public void shouldTestPersonCreationOnDEV() {
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        personService.createPerson("Adarsh", "Kumar", "0123456789");
        assertFalse(personService.getAllPersons().isEmpty());
        assertEquals(1, personService.getAllPersons().size());
    }

    @Test
    @DisplayName("Phone Number should start with 0")
    public void shouldTestPhoneNumberFormat() {
        personService.createPerson("Adarsh", "Kumar", "0123456789");
        assertFalse(personService.getAllPersons().isEmpty());
        assertEquals(1, personService.getAllPersons().size());
    }


    @Nested
    class RepeatedTests {
        @DisplayName("Repeat Person Creation Test 5 Times")
        @RepeatedTest(value = 5,
                name = "Repeating Person Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestPersonCreationRepeatedly() {
            personService.createPerson("Adarsh", "Kumar", "0123456789");
            assertFalse(personService.getAllPersons().isEmpty());
            assertEquals(1, personService.getAllPersons().size());
        }
    }

    @Nested
    class ParameterizedTests {
        @DisplayName("Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
            personService.createPerson("Adarsh", "Kumar", phoneNumber);
            assertFalse(personService.getAllPersons().isEmpty());
            assertEquals(1, personService.getAllPersons().size());
        }

        @DisplayName("CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            personService.createPerson("Adarsh", "Kumar", phoneNumber);
            assertFalse(personService.getAllPersons().isEmpty());
            assertEquals(1, personService.getAllPersons().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            personService.createPerson("Adarsh", "Kumar", phoneNumber);
            assertFalse(personService.getAllPersons().isEmpty());
            assertEquals(1, personService.getAllPersons().size());
        }
    }

    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        personService.createPerson("Adarsh", "Kumar", phoneNumber);
        assertFalse(personService.getAllPersons().isEmpty());
        assertEquals(1, personService.getAllPersons().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }

    @Test
    @DisplayName("Test Should Be Disabled")
    @Disabled
    public void shouldBeDisabled() {
        throw new RuntimeException("Test Should Not be executed");
    }


    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("Should be executed at the end of the Test");
    }
}

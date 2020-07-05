package tn.amiri.springbootintegrationteststestcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tn.amiri.springbootintegrationteststestcontainers.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// JUnit 5 example with Spring Boot >= 2.2.6
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAllPersonsIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withPassword("inmemory")
            .withUsername("inmemory");

    @Autowired
    public TestRestTemplate testRestTemplate;

    //If your application makes use of JUnit 5 but is using a Spring Boot version < 2.2.6, you don’t have access to the @DynamicPropertySource feature.
    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    @Sql("/testdata/FILL_FOUR_PERSONS.sql")
    public void testGetAllPersons() {

        ResponseEntity<Person[]> result = testRestTemplate.getForEntity("/api/persons", Person[].class);

        List<Person> resultList = Arrays.asList(result.getBody());

        assertEquals(4, resultList.size());
        assertTrue(resultList.stream().map(Person::getName).collect(Collectors.toList()).containsAll(Arrays.asList
                ("Mike", "Phil", "Duke", "Tom")));

    }
}

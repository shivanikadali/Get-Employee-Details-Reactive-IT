package com.example.Get_Employee_details_Reactive;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

// tells Spring Boot to start the application on a random port for testing.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetEmployeeDetailsReactiveApplicationTests {

    private static WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    // webTestClient used to perform HTTP requests in a reactive way.
    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    public static void setUp() throws IOException {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        System.setProperty("wiremock.port", String.valueOf(wireMockServer.port()));
        configureFor("localhost", wireMockServer.port());

        String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));
        String allEmployeesRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/allEmployees.json")));

        // WireMock intercepts these external calls and returns the predefined responses.
        // Setup stub for the /employee endpoint
        stubFor(post(urlEqualTo("/employee"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(expectedRequest)));

        stubFor(get(urlEqualTo("/employee"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(allEmployeesRequest)));
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testCreateEmployee() throws IOException {
        String newEmployee = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));

        // WebTestClient sends a POST request to the /employee endpoint.
        webTestClient.post().uri("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertNotNull(employee.getEmpNo());
                    assertEquals("jane.smith@example.com", employee.getEmail());
                    assertEquals("reeju", employee.getFirstName());
                    assertEquals("Smith", employee.getLastName());
                });
    }

    @Test
    void testGetAllEmployees() {
        // The application processes these requests, which might involve calling
        // external services (mocked by WireMock).
        webTestClient.get().uri("/employee")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(2) // Update the expected size according to your data
                .contains(new EmployeeDto(1, "Eva", "Brown", "eva.brown@example.com"))
                .contains(new EmployeeDto(2, "reeju", "Smith", "jane.smith@example.com"));
    }
}
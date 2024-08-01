// package com.example.Get_Employee_details_Reactive;

// import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.web.reactive.server.WebTestClient;

// import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
// import com.github.tomakehurst.wiremock.WireMockServer;
// import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

// import jakarta.annotation.PostConstruct;

// // tells Spring Boot to start the application on a random port for testing.
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureWebTestClient(timeout = "10000") // 10 seconds

// class GetEmployeeDetailsReactiveApplicationTests {

//     private static WireMockServer wireMockServer;

//     @LocalServerPort
//     private int port;

//     @Value("${baseUrl}")
//     private String baseurl;

//     private String uri;

//     @PostConstruct
//     public void init() {
//         uri = "http://localhost:" + port;
//     }

//     // webTestClient used to perform HTTP requests in a reactive way.
//     @Autowired
//     private WebTestClient webTestClient;

//     @BeforeAll
//     public static void setUp() throws IOException {
//         wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
//         wireMockServer.start();
//         System.setProperty("wiremock.port", String.valueOf(wireMockServer.port()));
//         configureFor("localhost", wireMockServer.port());
//         String wireMockPort = System.getProperty("wiremock.port");
//         // just to know about
//         // port
//         // wireMockServer.startRecording("http://localhost:9292");
//     }

//     @AfterAll
//     public static void tearDown() {
//         // wireMockServer.stopRecording();
//         wireMockServer.stop();
//     }

//     @DynamicPropertySource
//     static void registerWireMockProperties(DynamicPropertyRegistry registry) {
//         registry.add("employee.baseurl", () -> "http://localhost:" + wireMockServer.port());
//     }

//     @Test
//     void testCreateEmployee() throws IOException {
//         int portvalue = port;

//         String newEmployee = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));

//         // WebTestClient sends a POST request to the /employee endpoint.
//         // it uses the random port not actual port
//         // String baseurl = baseurl + port;

//         webTestClient.post().uri(uri + "/employee")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .bodyValue(newEmployee)
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBody(EmployeeDto.class)
//                 .value(employee -> {
//                     assertNotNull(employee.getEmpNo());
//                     assertEquals("jane.smith@example.com", employee.getEmail());
//                     assertEquals("jane", employee.getFirstName());
//                     assertEquals("Smith", employee.getLastName());
//                 });
//     }

//     @Test
//     void testGetAllEmployees() {
//         // The application processes these requests, which might involve calling
//         // external services (mocked by WireMock).
//         webTestClient.get().uri(uri + "/employee")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBodyList(EmployeeDto.class)
//                 .hasSize(7) // Update the expected size according to your data
//                 .contains(new EmployeeDto(1, "jane", "Smith", "jane.smith@example.com"));
//     }
// }

package com.example.Get_Employee_details_Reactive;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import jakarta.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "10000")
class GetEmployeeDetailsReactiveApplicationTests {

    private static WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    @Value("${baseUrl}")
    private String baseurl;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    public static void setUp() throws IOException {
   
        // This line sets the logging level for the SLF4J SimpleLogger to DEBUG.
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");

        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        System.setProperty("wiremock.port", String.valueOf(wireMockServer.port()));
        configureFor("localhost", wireMockServer.port());
       
        // registers a listener that executes a callback function for every request made
        // to the WireMock server.
        wireMockServer.addMockServiceRequestListener((request, response) -> {
            System.out.println("WireMock received request: " + request.getUrl());
        });
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void registerWireMockProperties(DynamicPropertyRegistry registry) {
        registry.add("baseUrl", () -> "http://localhost:" + wireMockServer.port());
    }

    @Test
    void testCreateEmployee() throws IOException {
        String newEmployee = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));
        webTestClient.post().uri(uri + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .exchange().expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertNotNull(employee.getEmpNo());
                    assertEquals("jane.smith@example.com", employee.getEmail());
                    assertEquals("jane", employee.getFirstName());
                    assertEquals("Smith", employee.getLastName());
                });
    }

    @Test
    void testGetAllEmployees() {
        webTestClient.get().uri(uri + "/employee")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(7)
                .contains(new EmployeeDto(1, "jane", "Smith", "jane.smith@example.com"));
    }
}

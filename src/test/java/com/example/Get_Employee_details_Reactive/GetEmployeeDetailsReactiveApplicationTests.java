// package com.example.Get_Employee_details_Reactive;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.reactive.server.WebTestClient;

// import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
// import com.github.tomakehurst.wiremock.WireMockServer;
// import com.github.tomakehurst.wiremock.client.WireMock;
// import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

// import reactor.core.publisher.Mono;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// class GetEmployeeDetailsReactiveApplicationTests {

// 	private static WireMockServer wireMockServer;

// 	@LocalServerPort
// 	private static int port;

// 	@BeforeAll
// 	public static void setUp() {
// 		wireMockServer = new WireMockServer(WireMockConfiguration.options()
// 				.dynamicPort());
// 		wireMockServer.start();
// 		WireMock.configureFor("localhost", wireMockServer.port());
// 		wireMockServer.startRecording("http://localhost:9090/employee"); // Replace with your actual API URL
// 	}

// 	@AfterAll
// 	public static void tearDown() {
// 		wireMockServer.stopRecording();
// 		wireMockServer.stop();
// 	}

// 	@Autowired
// 	private WebTestClient webTestClient;

// 	@Test
// 	public void testGetAllEmployees() {
// 		webTestClient.get().uri("/employee")
// 				.exchange()
// 				.expectStatus().isOk()
// 				.expectBodyList(EmployeeDto.class)
// 				.hasSize(9)
// 				.contains(new EmployeeDto(1, "Eva", "Brown", "eva.brown@example.com"));
// 	}

// 	@Test
// 	public void testCreateEmployee() {
// 		EmployeeDto newEmployee = new EmployeeDto("Jane", "Smith", "jane.smith@example.com");

// 		webTestClient.post().uri("/employee")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.body(Mono.just(newEmployee), EmployeeDto.class)
// 				.exchange()
// 				.expectStatus().isOk()
// 				.expectBody(EmployeeDto.class)
// 				.value(employee -> {
// 					assertNotNull(employee.getEmpNo());
// 					assertEquals("jane.smith@example.com", employee.getEmail());
// 					assertEquals("Jane", employee.getFirstName());
// 					assertEquals("Smith", employee.getLastName());
// 				});
// 	}
// }

package com.example.Get_Employee_details_Reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetEmployeeDetailsReactiveApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateEmployee() {
        EmployeeDto newEmployee = new EmployeeDto("Jane", "Smith", "jane.smith@example.com");

        webTestClient.post().uri("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    assertNotNull(employee.getEmpNo());
                    assertEquals("jane.smith@example.com", employee.getEmail());
                    assertEquals("Jane", employee.getFirstName());
                    assertEquals("Smith", employee.getLastName());
                });
    }

    @Test
    void testGetAllEmployees() {
        webTestClient.get().uri("/employee")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(24) // Update the expected size according to your data
                .contains(new EmployeeDto(1, "Eva", "Brown", "eva.brown@example.com"));
    }
}
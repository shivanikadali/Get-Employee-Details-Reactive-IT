// package com.example.Get_Employee_details_Reactive.unittesting;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.reactive.server.WebTestClient;
// import org.springframework.web.reactive.function.BodyInserters;

// import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
// import com.example.Get_Employee_details_Reactive.service.ClientService;

// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;

// @ExtendWith(MockitoExtension.class)
// @AutoConfigureWebTestClient
// public class ClientServiceTest {

//     @InjectMocks
//     ClientService clientService;

//     // instead of using webclient use webtestclient
//     @Autowired
//     private WebTestClient webTestClient;

//     EmployeeDto employee1;
//     EmployeeDto employee2;

//     List<EmployeeDto> employees;

//     @BeforeEach
//     public void init() {
//         MockitoAnnotations.openMocks(this);

//         employee1 = new EmployeeDto("rani", "yadav", "rani@gmail.com");
//         employee2 = new EmployeeDto("jhansi", "yadav", "jhansi@gmail.com");
//         employees = Arrays.asList(employee1, employee2);
//     }

//     @Test
//     public void testGetAllEmployeeDetails() {
//         // Mocking clientService behavior
//         when(clientService.getAllEmployeeDetails()).thenReturn(Flux.fromIterable(employees));

//         // Perform a GET request using WebTestClient
//         webTestClient.get().uri("/employee")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBodyList(EmployeeDto.class)
//                 .isEqualTo(employees);
//     }

//     @Test
//     public void testCreateEmployee() {
//         EmployeeDto employeeToCreate = new EmployeeDto("rani", "yadav", "rani@gmail.com");

//         // Mocking clientService behavior
//         when(clientService.createEmployee(any(EmployeeDto.class))).thenReturn(Mono.just(employeeToCreate));

//         // Perform a POST request using WebTestClient
//         webTestClient.post().uri("/employee")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(BodyInserters.fromValue(employeeToCreate))
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBody(EmployeeDto.class)
//                 .isEqualTo(employeeToCreate);
//     }
// }
// package com.example.Get_Employee_details_Reactive.unittesting;
package com.example.Get_Employee_details_Reactive.unittesting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.example.Get_Employee_details_Reactive.service.ClientService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpecMock;
    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // // Mock WebClient behavior for GET
        // when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        // when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);
        // when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        // when(responseSpecMock.bodyToFlux(EmployeeDto.class))
        // .thenReturn(Flux.fromIterable(Arrays.asList(
        // new EmployeeDto("John", "Doe", "john.doe@example.com"),
        // new EmployeeDto("Jane", "Doe", "jane.doe@example.com")
        // )));

        // Mock WebClient behavior for POST
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(any(String.class))).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.body(any(), any(Class.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(EmployeeDto.class))
                .thenReturn(Mono.just(new EmployeeDto("Jane", "Doe", "jane.doe@example.com")));
    }

    // @Test
    // public void testGetAllEmployees() {
    // Flux<EmployeeDto> employeeFlux = clientService.getAllEmployeeDetails();

    // StepVerifier.create(employeeFlux)
    // .expectNextMatches(employee -> employee.getFirstName().equals("John"))
    // .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
    // .verifyComplete();
    // }

    @Test
    public void testCreateEmployee() {
        EmployeeDto newEmployee = new EmployeeDto("Jane", "Doe", "jane.doe@example.com");
        Mono<EmployeeDto> employeeMono = clientService.createEmployee(newEmployee);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
                .verifyComplete();
    }
}
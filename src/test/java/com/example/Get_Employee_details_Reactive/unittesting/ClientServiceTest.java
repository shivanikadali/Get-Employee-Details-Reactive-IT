// package com.example.Get_Employee_details_Reactive.unittesting;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.util.Arrays;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
// import com.example.Get_Employee_details_Reactive.service.ClientService;

// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// @ExtendWith(MockitoExtension.class)
// public class ClientServiceTest {

//     @InjectMocks
//     private ClientService clientService;

//     @Mock
//     private WebClient webClientMock;

//     @Mock
//     private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock;

//     @Mock
//     private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

//     @Mock
//     private WebClient.RequestHeadersSpec<?> requestHeadersSpecMock;

//     @Mock
//     private WebClient.ResponseSpec responseSpecMock;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // // Mock WebClient behavior for GET
//         // when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
//         // when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);
//         // when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
//         // when(responseSpecMock.bodyToFlux(EmployeeDto.class))
//         //         .thenReturn(Flux.fromIterable(Arrays.asList(
//         //                 new EmployeeDto("John", "Doe", "john.doe@example.com"),
//         //                 new EmployeeDto("Jane", "Doe", "jane.doe@example.com"))));

//         // Mock WebClient behavior for POST
//         when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
//         when(requestBodyUriSpecMock.uri(any(String.class))).thenReturn(requestBodyUriSpecMock);
//         when(requestBodyUriSpecMock.body(any(), any(Class.class))).thenReturn(requestHeadersSpecMock);
//         when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
//         when(responseSpecMock.bodyToMono(EmployeeDto.class))
//                 .thenReturn(Mono.just(new EmployeeDto("Jane", "Doe", "jane.doe@example.com")));
//     }

//     @Test
//     public void testGetAllEmployees() {
//         Flux<EmployeeDto> employeeFlux = clientService.getAllEmployeeDetails();

//         StepVerifier.create(employeeFlux)
//                 .expectNextMatches(employee -> employee.getFirstName().equals("John"))
//                 .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
//                 .verifyComplete();
//     }

//     @Test
//     public void testCreateEmployee() {
//         EmployeeDto newEmployee = new EmployeeDto("Jane", "Doe", "jane.doe@example.com");
//         Mono<EmployeeDto> employeeMono = clientService.createEmployee(newEmployee);

//         StepVerifier.create(employeeMono)
//                 .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
//                 .verifyComplete();
//     }
// }
package com.example.Get_Employee_details_Reactive.unittesting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.example.Get_Employee_details_Reactive.service.ClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {



    @InjectMocks
    private ClientService clientService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private RequestHeadersUriSpec<?> requestHeadersUriSpecMock;

    @Mock
    private RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpecMock;

    @Mock
    private ResponseSpec responseSpecMock;

    @BeforeEach
    public void setUp() {

    }


    // @Test
    // public void testGetAllEmployeeDetails() {
    //     // Mock WebClient behavior for GET
    //     WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
    //     WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
    //     WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

    //     when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
    //     when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);
    //     when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    //     when(responseSpecMock.bodyToFlux(EmployeeDto.class)).thenReturn(Flux.fromIterable(Arrays.asList(
    //             new EmployeeDto("John", "Doe", "john.doe@example.com"),
    //             new EmployeeDto("Jane", "Doe", "jane.doe@example.com"))));

    //     Flux<EmployeeDto> employeeFlux = clientService.getAllEmployeeDetails();

    //     StepVerifier.create(employeeFlux)
    //             .expectNextMatches(employee -> employee.getFirstName().equals("John"))
    //             .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
    //             .verifyComplete();
    // }

    @SuppressWarnings("unchecked")

    @Test
    public void testCreateEmployee() {
        // Mock WebClient behavior for POST
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(any(String.class))).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.body(any(), any(Class.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(EmployeeDto.class))
                .thenReturn(Mono.just(new EmployeeDto("Jane", "Doe", "jane.doe@example.com")));

        EmployeeDto employeeToCreate = new EmployeeDto("Jane", "Doe", "jane.doe@example.com");

        Mono<EmployeeDto> employeeMono = clientService.createEmployee(employeeToCreate);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getFirstName().equals("Jane"))
                .verifyComplete();
    }
}

package com.example.Get_Employee_details_Reactive.unittesting;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.Get_Employee_details_Reactive.controller.ClientController;
import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.example.Get_Employee_details_Reactive.service.ClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
// org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'com.example.Get_Employee_details_Reactive.unittesting.ClientControllerTest': Unsatisfied dependency expressed through field 'webTestClient': No qualifying bean of type 'org.springframework.test.web.reactive.server.WebTestClient' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

@WebFluxTest(ClientController.class)
@ExtendWith(SpringExtension.class)
public class ClientControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    private EmployeeDto employee1;
    private EmployeeDto employee2;
    private List<EmployeeDto> employees = null;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employee1 = new EmployeeDto(1, "priyanka", "Reddy", "priya@gmail.com");
        employee2 = new EmployeeDto(2, "nandhini", "Reddy", "nandhini@gmail.com");
        employees = Arrays.asList(employee1, employee2);
    }

    @Test
    public void getAllEmployeeDetails() {
        // Arrange
        when(clientService.getAllEmployeeDetails()).thenReturn(Flux.fromIterable(employees));
        // Act
        webTestClient.get().uri("/employee")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(2)
                .contains(employee1, employee2);
    }

    @Test
    public void testCreateEmployee() {
        when(clientService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(Mono.just(employee1));

        webTestClient.post().uri("/employee")
                .bodyValue(employee1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(employee1);
    }

}
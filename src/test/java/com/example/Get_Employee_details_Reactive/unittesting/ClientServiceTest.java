package com.example.Get_Employee_details_Reactive.unittesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.example.Get_Employee_details_Reactive.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    public static MockWebServer mockBackEnd;

    // @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    ClientService clientService;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    public void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        clientService = new ClientService(baseUrl);
    }

    @Test
    public void getEmployees() throws JsonProcessingException, InterruptedException {
        EmployeeDto mockEmployee = new EmployeeDto(1, "john", "bush", "john@gmail.com");
        EmployeeDto mockEmployee2 = new EmployeeDto(1, "john", "bush", "jack@gmail.com");
        List<EmployeeDto> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(mockEmployee);
        listOfEmployees.add(mockEmployee2);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(listOfEmployees))
                .addHeader("Content-Type", "application/json"));
        Flux<EmployeeDto> employeeFlux = clientService.getAllEmployeeDetails();

        StepVerifier.create(employeeFlux)
                .expectNextMatches(employee -> employee.getEmail()
                        .equals(mockEmployee.getEmail()))
                .expectNextMatches(employee -> employee.getEmail()
                        .equals(mockEmployee2.getEmail()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/employee", recordedRequest.getPath());
    }

    @Test
    public void createEmployees() throws JsonProcessingException, InterruptedException {
        EmployeeDto mockEmployee = new EmployeeDto(1, "john", "bush", "john@gmail.com");
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockEmployee))
                .addHeader("Content-Type", "application/json"));
        Mono<EmployeeDto> employeeMono = clientService.createEmployee(mockEmployee);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmail()
                        .equals(mockEmployee.getEmail()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/employee", recordedRequest.getPath());
    }
}
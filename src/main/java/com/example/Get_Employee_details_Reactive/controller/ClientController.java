package com.example.Get_Employee_details_Reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;
import com.example.Get_Employee_details_Reactive.service.ClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employee")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return clientService.getAllEmployeeDetails();
    }

    @PostMapping
    public Mono<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return clientService.createEmployee(employeeDto);
    }
}
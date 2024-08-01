package com.example.Get_Employee_details_Reactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Get_Employee_details_Reactive.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ClientService {

    @Autowired
    private WebClient webClientBean;

    public ClientService(String baseUrl) {
        this.webClientBean = WebClient.create(baseUrl);
    }
    
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public Flux<EmployeeDto> getAllEmployeeDetails() {
        return webClientBean.get()
                .uri("/employee")
                .retrieve()
                .bodyToFlux(EmployeeDto.class);
    }

    public Mono<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        logger.info("Creating employee with data: {}", employeeDto);
        // response
        return webClientBean.post()
                .uri("/employee")
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .retrieve()
                .bodyToMono(EmployeeDto.class);
    }
}

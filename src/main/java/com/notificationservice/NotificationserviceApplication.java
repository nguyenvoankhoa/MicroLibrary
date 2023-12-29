package com.notificationservice;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationserviceApplication.class, args);
    }

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(NotificationserviceApplication.class);

    private final WebClient.Builder webClientBuilder;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public NotificationserviceApplication(WebClient.Builder webClientBuilder, CircuitBreakerFactory circuitBreakerFactory) {
        this.webClientBuilder = webClientBuilder;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @StreamListener(Sink.INPUT)
    public void consumeMessage(Message message) {

        EmployeeResponseModel Employeemodel = circuitBreakerFactory.create("getEmployee").run(
                () -> {
                    EmployeeResponseModel model = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:9002/api/v1/employees/" + message.getEmployeeId())
                            .retrieve()
                            .bodyToMono(EmployeeResponseModel.class)
                            .block();
                    return model;
                },
                t -> {
                    EmployeeResponseModel model = new EmployeeResponseModel();
                    model.setFirstName("Anonymous");
                    model.setLastName("Employee");
                    return model;
                }
        );

        if (Employeemodel != null) {
            logger.info("Consume Payload: " + Employeemodel.getFirstName() + " " + Employeemodel.getLastName() + " " + message.getMessage());
        }
    }

}

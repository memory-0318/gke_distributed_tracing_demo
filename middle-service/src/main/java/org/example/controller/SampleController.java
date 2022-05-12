package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.model.ResponseWrapper;
import org.example.model.ServiceResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Brian Su <brian.su@tpisoftware.com>
 * @description:
 * @date: 2022/5/2
 */
@RestController
@RequiredArgsConstructor
public class SampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
    private final Integer UPPER_INTEGER_BOUND = 10;

    private Random random = new Random();

    @Value("${back-service-url}")
    private String backServiceUrl;

    private final RestTemplate restTemplate;

    @GetMapping(value = { "middle-entry" })
    public String frontEntry() {
        LOGGER.info("middle service receive request");

        try {
            LOGGER.info("Middle service receive request");
            LOGGER.info(String.format("Sending request to %s", this.backServiceUrl));

            this.sleepForRandomPeriod();
            this.randomlyThrowException();

            ServiceResponseDTO responseObject = this.restTemplate.getForObject(this.backServiceUrl,
                ServiceResponseDTO.class);

            LOGGER.info(String.format("Middle service receive response: %s",
                new ObjectMapper().writeValueAsString(responseObject)));

            return new ObjectMapper().writeValueAsString(
                ResponseWrapper.builder()
                    .setSuccess(true)
                    .setData("middle entry")
                    .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    @SneakyThrows
    private void sleepForRandomPeriod() {
        TimeUnit.SECONDS.sleep(this.random.nextInt(UPPER_INTEGER_BOUND));
    }

    private void randomlyThrowException() {
        if (this.random.nextInt(UPPER_INTEGER_BOUND) == 0) {
            throw new RuntimeException("Get random exception from back service");
        }
    }
}

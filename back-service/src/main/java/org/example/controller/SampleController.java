package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.model.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = { "back-entry" })
    public String backEntry() {
        try {
            LOGGER.info("Back service receive request");

            this.sleepForRandomPeriod();
            this.randomlyThrowException();

            return new ObjectMapper().writeValueAsString(
                ResponseWrapper.builder()
                    .setSuccess(true)
                    .setData("back entry")
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

package com.emr.example.controller;

import com.emr.example.ExampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.ContainerLaunchException;

@Slf4j
@SpringBootTest(classes = {ExampleApplication.class})
public class IntegrationTestBase {

    static PostgreSQLContainer postgreSQLContainer;

    static {
        try {
            postgreSQLContainer = new PostgreSQLContainer();
            postgreSQLContainer.start();
        } catch (ContainerLaunchException e) {
            log.info("Unable to start test container");
        }
    }
}

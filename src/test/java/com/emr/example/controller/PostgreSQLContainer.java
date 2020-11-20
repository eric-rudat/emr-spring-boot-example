package com.emr.example.controller;

import org.testcontainers.containers.GenericContainer;

class PostgreSQLContainer extends GenericContainer<PostgreSQLContainer> {

    private static final int HOST_PORT = 5433;
    private static final int CONTAINER_PORT = 5432;
    private static final String USER_NAME = "test";
    private static final String PASSWORD = "test";
    private static final String DB = "test";
    private static final String DEFAULT_IMAGE = "postgres";

    PostgreSQLContainer() {
        super(DEFAULT_IMAGE);

        addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
        addEnv("POSTGRES_USER", USER_NAME);
        addEnv("POSTGRES_PASSWORD", PASSWORD);
        addEnv("POSTGRES_DB", DB);
    }
}

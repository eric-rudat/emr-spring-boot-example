package com.emr.example;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class TestDisplayNameGenerator extends DisplayNameGenerator.Standard {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return super.generateDisplayNameForClass(testClass) + "...";
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return testMethod.getName()
                .replaceAll("([A-Z])", " $1")
                .replaceAll("([0-9]+)", " $1")
                .replace("_", " ")
                .toLowerCase()
                .replace("throws", "(╯°□°)╯︵ ┻━┻");
    }
}

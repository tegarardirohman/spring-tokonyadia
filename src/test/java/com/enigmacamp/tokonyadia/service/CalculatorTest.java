package com.enigmacamp.tokonyadia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {

    @Test
    void sumTest() {
        Integer actual = 5;
        Integer expected = 5;
        Assertions.assertEquals(actual,expected);
    }

    @CsvSource({
            "0, 1, 1",
            "1, 2, 3",
            "-5, -5, -10"
    })


    @ParameterizedTest
    void sumTest(int a, int b, int expected) {
        Assertions.assertEquals(expected,a+b);
    }
}

package com.amoguspro;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import com.amoguspro.function.FunctionSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FunctionSystemTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00000001");
    private static final int DEFAULT_SCALE = 8;

    @Test
    void shouldNotAcceptNullArgument() {
        final FunctionSystem system = new FunctionSystem();
        assertThrows(NullPointerException.class, () -> system.calculate(null, DEFAULT_PRECISION));
    }

    @Test
    void shouldNotAcceptNullPrecision() {
        final FunctionSystem system = new FunctionSystem();
        assertThrows(NullPointerException.class, () -> system.calculate(new BigDecimal(-2), null));
    }

    @ParameterizedTest
    @MethodSource("illegalPrecisions")
    void shouldNotAcceptIncorrectPrecisions(final BigDecimal precision) {
        final FunctionSystem system = new FunctionSystem();
        assertThrows(ArithmeticException.class, () -> system.calculate(new BigDecimal(-2), precision));
    }

    @Test
    void shouldCalculateForPositiveValue() {
        final FunctionSystem system = new FunctionSystem();
        final BigDecimal expected = new BigDecimal("0.36505078");
        assertEquals(expected, system.calculate(new BigDecimal(1.75), DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForNegativeValue() {
        final FunctionSystem system = new FunctionSystem();
        final BigDecimal expected = new BigDecimal("2.00000000");
        assertEquals(expected, system.calculate(new BigDecimal(-1.13), DEFAULT_PRECISION));
    }

    @Test
    void shouldNotCalculateForZero() {
        final FunctionSystem system = new FunctionSystem();
        assertThrows(ArithmeticException.class, () -> system.calculate(new BigDecimal(0), DEFAULT_PRECISION));
    }

    @Test
    void shouldNotCalculateForOne() {
        final FunctionSystem system = new FunctionSystem();
        assertThrows(ArithmeticException.class, () -> system.calculate(new BigDecimal(1), DEFAULT_PRECISION));
    }

    private static Stream<Arguments> illegalPrecisions() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(1)),
                Arguments.of(BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(1.01)),
                Arguments.of(BigDecimal.valueOf(-0.01)));
    }
}
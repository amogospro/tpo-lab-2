package com.amoguspro;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.amoguspro.function.SeriesExpandableFunction;
import com.amoguspro.logariphmic.Ln;
import com.amoguspro.logariphmic.Log;
import com.amoguspro.trigonometric.Cos;
import com.amoguspro.trigonometric.Csc;
import com.amoguspro.trigonometric.Sec;
import com.amoguspro.trigonometric.Sin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SeriesExpandableFunctionTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.000001");

    @ParameterizedTest
    @MethodSource("functions")
    void shouldNotAcceptNullArgument(final SeriesExpandableFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(null, DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @MethodSource("functions")
    void shouldNotAcceptNullPrecision(final SeriesExpandableFunction function) {
        assertThrows(NullPointerException.class, () -> function.calculate(ONE, null));
    }

    private static Stream<Arguments> functions() {
        return Stream.of(
                Arguments.of(new Sin()),
                Arguments.of(new Cos()),
                Arguments.of(new Csc()),
                Arguments.of(new Sec()),
                Arguments.of(new Ln()),
                Arguments.of(new Log(2)),
                Arguments.of(new Log(10)));
    }
}

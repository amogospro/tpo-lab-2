package com.amoguspro;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import com.amoguspro.trigonometric.Sec;
import com.amoguspro.trigonometric.Cos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0001");

    @Mock
    private Cos mockCos;

    @Spy
    private Cos spyCos = new Cos(new com.amoguspro.trigonometric.Sin());

    @Test
    void shouldCallCosFunction() {
        final Sec sec = new Sec(spyCos);
        sec.calculate(new BigDecimal(6), new BigDecimal("0.001"));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalculateWithMockCos() {
        final BigDecimal arg = new BigDecimal("5");
        // For Sec, sec(x) = 1 / cos(x).
        // In this test we simulate a known cosine value.
        final BigDecimal cosResult = new BigDecimal("0.283662");
        when(mockCos.calculate(eq(arg), any(BigDecimal.class))).thenReturn(cosResult);

        final Sec sec = new Sec(mockCos);
        BigDecimal expected = ONE.divide(cosResult, DEFAULT_PRECISION.scale() + 5, HALF_EVEN)
                .setScale(DEFAULT_PRECISION.scale(), HALF_EVEN);
        assertEquals(expected, sec.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForZero() {
        // For x = 0, cos(0) should be 1, hence sec(0) = 1.
        final Cos cos = new Cos(new com.amoguspro.trigonometric.Sin());
        final Sec sec = new Sec(cos);
        BigDecimal expected = ONE.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN);
        assertEquals(expected, sec.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void shouldThrowWhenCosIsZero() {
        // If cos(x) returns 0, then sec(x) should throw an exception.
        final BigDecimal arg = new BigDecimal("1.5708"); // approximately π/2
        when(mockCos.calculate(eq(arg), any(BigDecimal.class))).thenReturn(ZERO);
        final Sec sec = new Sec(mockCos);
        assertThrows(ArithmeticException.class, () -> sec.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPeriodic() {
        // Test periodicity: using a large negative angle.
        final Cos cos = new Cos(new com.amoguspro.trigonometric.Sin());
        final Sec sec = new Sec(cos);
        BigDecimal expected = new BigDecimal("-1.1368");
        assertEquals(expected, sec.calculate(new BigDecimal("-543"), DEFAULT_PRECISION));
    }
}
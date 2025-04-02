package com.amoguspro;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.math.BigDecimal;
import java.math.MathContext;

import com.amoguspro.trigonometric.Csc;
import com.amoguspro.trigonometric.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CscTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0001");

    @Mock private Sin mockSin;
    @Spy private Sin spySin;

    @Test
    void shouldCallSinFunction() {
        Csc csc = new Csc(spySin);
        csc.calculate(new BigDecimal(6), DEFAULT_PRECISION);

        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void shouldCalculateWithMockSin() {
        BigDecimal arg = new BigDecimal(5);
        BigDecimal sinResult = new BigDecimal("0.5");
        when(mockSin.calculate(eq(arg), any(BigDecimal.class))).thenReturn(sinResult);

        Csc csc = new Csc(mockSin);
        BigDecimal expected = ONE.divide(sinResult, DECIMAL128.getPrecision(), HALF_EVEN)
                .setScale(DEFAULT_PRECISION.scale(), HALF_EVEN);

        assertEquals(expected, csc.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPiDividedByTwo() {
        Csc csc = new Csc();
        MathContext mc = new MathContext(DECIMAL128.getPrecision());
        BigDecimal arg = BigDecimalMath.pi(mc).divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN);
        BigDecimal expected = new BigDecimal("1.0000");

        assertEquals(expected, csc.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPiDividedBySix() {
        Csc csc = new Csc();
        MathContext mc = new MathContext(DECIMAL128.getPrecision());
        BigDecimal arg = BigDecimalMath.pi(mc).divide(new BigDecimal(6), DECIMAL128.getPrecision(), HALF_EVEN);
        BigDecimal expected = new BigDecimal("2.0000");

        assertEquals(expected, csc.calculate(arg, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForOne() {
        Csc csc = new Csc();
        BigDecimal expected = new BigDecimal("1.1884"); // Precomputed 1/sin(1) ≈ 1.1884
        assertEquals(expected, csc.calculate(ONE, DEFAULT_PRECISION));
    }

    @Test
    void shouldCalculateForPeriodic() {
        Csc csc = new Csc();
        MathContext mc = new MathContext(DECIMAL128.getPrecision());
        BigDecimal arg = new BigDecimal(-543);
        BigDecimal expected = new BigDecimal("-2.1030");

        assertEquals(expected, csc.calculate(arg, DEFAULT_PRECISION));
    }
}
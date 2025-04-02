package com.amoguspro.trigonometric;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.amoguspro.function.LimitedIterationsExpandableFunction;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cos extends LimitedIterationsExpandableFunction {

    private final Sin sin;

    public Cos() {
        super();
        this.sin = new Sin();
    }

    public Cos(final Sin sin) {
        super();
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision)
            throws ArithmeticException {
        checkValidity(x, precision);
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        // Приведение x к диапазону [0, 2π) с помощью BigDecimalMath
        BigDecimal twoPi = BigDecimalMath.pi(mc).multiply(new BigDecimal("2"));
        BigDecimal correctedX = x.remainder(twoPi);
        // Если remainder отрицательный, приводим его к положительному диапазону
        if (correctedX.compareTo(ZERO) < 0) {
            correctedX = correctedX.add(twoPi);
        }
        // cos(x) = sin(π/2 - x)
        BigDecimal argument = BigDecimalMath.pi(mc)
                .divide(new BigDecimal("2"), DECIMAL128.getPrecision(), HALF_EVEN)
                .subtract(correctedX);
        BigDecimal result = sin.calculate(argument, precision);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}

package com.amoguspro.trigonometric;

import com.amoguspro.function.LimitedIterationsExpandableFunction;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class Csc extends LimitedIterationsExpandableFunction {

    private final Sin sin;

    public Csc() {
        super();
        this.sin = new Sin();
    }

    public Csc(final Sin sin) {
        super();
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) throws ArithmeticException {
        checkValidity(x, precision);
        BigDecimal sinValue = sin.calculate(x, precision);
        if (sinValue.compareTo(ZERO) == 0) {
            throw new ArithmeticException("Деление на ноль при вычислении csc(x): sin(x) равен нулю");
        }
        return ONE.divide(sinValue, precision.scale() + 5, HALF_EVEN)
                .setScale(precision.scale(), HALF_EVEN);
    }
}

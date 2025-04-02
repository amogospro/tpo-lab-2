package com.amoguspro.trigonometric;

import com.amoguspro.function.LimitedIterationsExpandableFunction;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class Sec extends LimitedIterationsExpandableFunction {

    private final Cos cos;

    public Sec() {
        super();
        this.cos = new Cos(new Sin());
    }

    public Sec(final Cos cos) {
        super();
        this.cos = cos;
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) throws ArithmeticException {
        checkValidity(x, precision);
        BigDecimal cosValue = cos.calculate(x, precision);
        if (cosValue.compareTo(ZERO) == 0) {
            throw new ArithmeticException("Деление на ноль при вычислении sec(x): cos(x) равен нулю");
        }
        // Дополнительная точность при делении, затем округление до нужного масштаба
        return ONE.divide(cosValue, precision.scale() + 5, HALF_EVEN)
                .setScale(precision.scale(), HALF_EVEN);
    }
}

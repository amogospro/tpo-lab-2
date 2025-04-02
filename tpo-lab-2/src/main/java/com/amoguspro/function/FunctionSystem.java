package com.amoguspro.function;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.amoguspro.trigonometric.Cos;
import com.amoguspro.trigonometric.Sin;
import com.amoguspro.trigonometric.Sec;
import com.amoguspro.trigonometric.Csc;
import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionSystem implements SeriesExpandableFunction {

    private final Cos cos;
    private final Sin sin;
    private final Sec sec;
    private final Csc csc;

    public FunctionSystem() {
        this.sin = new Sin();
        this.cos = new Cos(sin);
        this.sec = new Sec(cos);
        this.csc = new Csc(sin);
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) {
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        // Приводим x к диапазону [0, 2π)
        BigDecimal twoPi = BigDecimalMath.pi(mc).multiply(new BigDecimal("2"));
        BigDecimal correctedX = x.remainder(twoPi);
        if (correctedX.compareTo(ZERO) < 0) {
            correctedX = correctedX.add(twoPi);
        }
        if (x.compareTo(ZERO) <= 0) {
            // Вычисляем f(x) для x ≤ 0 по формуле:
            // f(x)= (((cos(x)+cos(x))/cos(x)) * (sec(x)/cos(x))) *
            //       ((csc(x)-sin(x))/(csc(x)/sin(x))) * csc(x)
            BigDecimal cosVal = cos.calculate(correctedX, precision);
            BigDecimal sinVal = sin.calculate(correctedX, precision);
            BigDecimal secVal = sec.calculate(correctedX, precision);
            BigDecimal cscVal = csc.calculate(correctedX, precision);

            // (cos(x) + cos(x)) / cos(x)
            BigDecimal part1 = (cosVal.add(cosVal))
                    .divide(cosVal, precision.scale() + 5, HALF_EVEN);
            // sec(x) / cos(x)
            BigDecimal part2 = secVal.divide(cosVal, precision.scale() + 5, HALF_EVEN);
            BigDecimal firstProduct = part1.multiply(part2);

            // (csc(x) - sin(x)) / (csc(x)/sin(x))
            BigDecimal numerator = cscVal.subtract(sinVal);
            BigDecimal denominator = cscVal.divide(sinVal, precision.scale() + 5, HALF_EVEN);
            BigDecimal secondProduct = numerator.divide(denominator, precision.scale() + 5, HALF_EVEN);

            // Итоговое умножение с csc(x)
            BigDecimal result = firstProduct.multiply(secondProduct).multiply(cscVal);
            return result.setScale(precision.scale(), HALF_EVEN);
        } else {
            // Логарифмическая ветвь не реализована
            throw new UnsupportedOperationException("Логарифмическая ветвь не реализована");
        }
    }
}

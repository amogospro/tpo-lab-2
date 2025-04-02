package com.amoguspro.function;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.amoguspro.trigonometric.Cos;
import com.amoguspro.trigonometric.Sin;
import com.amoguspro.trigonometric.Sec;
import com.amoguspro.trigonometric.Csc;
import com.amoguspro.logariphmic.Ln;
import com.amoguspro.logariphmic.Log;
import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionSystem implements SeriesExpandableFunction {

    private final Cos cos;
    private final Sin sin;
    private final Sec sec;
    private final Csc csc;
    private final Ln ln;
    private final Log log2;
    private final Log log10;

    public FunctionSystem() {
        this.sin = new Sin();
        this.cos = new Cos(sin);
        this.sec = new Sec(cos);
        this.csc = new Csc(sin);
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log10 = new Log(10);
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) {
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        if (x.compareTo(ZERO) <= 0) {
            // Тригонометрическая ветвь для x ≤ 0:
            // Приводим x к диапазону [0, 2π)
            BigDecimal twoPi = BigDecimalMath.pi(mc).multiply(new BigDecimal("2"));
            BigDecimal correctedX = x.remainder(twoPi);
            if (correctedX.compareTo(ZERO) < 0) {
                correctedX = correctedX.add(twoPi);
            }
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

            BigDecimal result = firstProduct.multiply(secondProduct).multiply(cscVal);
            return result.setScale(precision.scale(), HALF_EVEN);
        } else {
            // Логарифмическая ветвь для x > 0:
            // f(x) = ((ln(x) - log₂(x))³ + ln(x)) - ((ln(x) + log₁₀(x)) * (log₂(x))³) + log₁₀(x)
            BigDecimal A = ln.calculate(x, precision);
            BigDecimal B = log2.calculate(x, precision);
            BigDecimal C = log10.calculate(x, precision);
            BigDecimal part1 = (A.subtract(B)).pow(3);
            BigDecimal part2 = A;
            BigDecimal part3 = (A.add(C)).multiply(B.pow(3));
            BigDecimal result = part1.add(part2).subtract(part3).add(C);
            return result.setScale(precision.scale(), HALF_EVEN);
        }
    }
}

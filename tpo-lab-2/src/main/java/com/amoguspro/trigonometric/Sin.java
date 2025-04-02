package com.amoguspro.trigonometric;

import com.amoguspro.function.LimitedIterationsExpandableFunction;

import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;

public class Sin extends LimitedIterationsExpandableFunction {

    public Sin() {
        super();
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision)
            throws ArithmeticException {

        double X = x.doubleValue();
        double PI2 = Math.PI * 2;

        // Приводим угол к диапазону [0, 2π)
        if (X < 0) {
            while (X < 0) {
                X += PI2;
            }
        } else {
            while (X > PI2) {
                X -= PI2;
            }
        }

        int i = 0;
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal prev;
        // Будем сравнивать разность с требуемой точностью
        BigDecimal threshold = precision;

        do {
            if(i >= maxIterations) {
                throw new ArithmeticException("Превышено максимальное число итераций при вычислении sin(x)");
            }
            prev = sum;
            // Вычисляем i-ый член ряда: (-1)^i * X^(2i+1) / (2i+1)!
            BigDecimal term = minusOnePow(i).multiply(prod(X, 2 * i + 1));
            sum = sum.add(term);
            i++;
        } while (prev.subtract(sum).abs().compareTo(threshold) > 0);

        return sum.setScale(precision.scale(), HALF_EVEN);
    }

    /**
     * Возвращает (-1)^n.
     */
    private static BigDecimal minusOnePow(int n){
        return BigDecimal.valueOf(n % 2 == 0 ? 1 : -1);
    }

    /**
     * Вычисляет произведение x^n / n! для заданного n.
     * Используется double-представление x.
     */
    private static BigDecimal prod(double x, int n){
        BigDecimal accum = BigDecimal.ONE;
        for (int i = 1; i <= n; i++){
            accum = accum.multiply(BigDecimal.valueOf(x / i));
        }
        return accum;
    }
}

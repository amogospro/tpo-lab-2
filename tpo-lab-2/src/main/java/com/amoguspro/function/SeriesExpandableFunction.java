package com.amoguspro.function;

import java.math.BigDecimal;

public interface SeriesExpandableFunction {

    BigDecimal calculate(final BigDecimal x, final BigDecimal precision);

}

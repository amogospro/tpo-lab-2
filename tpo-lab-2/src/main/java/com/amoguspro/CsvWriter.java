package com.amoguspro;

import com.amoguspro.function.SeriesExpandableFunction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CsvWriter {

    public static void write(
            final String filename,
            final SeriesExpandableFunction function,
            final BigDecimal from,
            final BigDecimal to,
            final BigDecimal step,
            final BigDecimal precision)
            throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (BigDecimal current = from; current.compareTo(to) <= 0; current = current.add(step)) {
            String s = current + ",";
            try {
                s += function.calculate(current, precision);
            } catch (Exception e) {
            }
            printWriter.println(s);
        }
        printWriter.close();
    }

}

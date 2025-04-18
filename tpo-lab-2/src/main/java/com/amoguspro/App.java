package com.amoguspro;

import com.amoguspro.function.FunctionSystem;
import com.amoguspro.logariphmic.Ln;
import com.amoguspro.logariphmic.Log;
import com.amoguspro.trigonometric.Cos;
import com.amoguspro.trigonometric.Csc;
import com.amoguspro.trigonometric.Sec;
import com.amoguspro.trigonometric.Sin;
import java.io.IOException;
import java.math.BigDecimal;

public class App {

    public static void main(String[] args) throws IOException {
        // Экспорт значений косинуса в CSV (файл csv/cos.csv)
        final Cos cos = new Cos();
        CsvWriter.write(
                "csv/cos.csv",
                cos,
                new BigDecimal(-10),
                new BigDecimal(10),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001")
        );

        // Экспорт значений синуса в CSV (файл csv/sin.csv)
        final Sin sin = new Sin();
        CsvWriter.write(
                "csv/sin.csv",
                sin,
                new BigDecimal(-10),
                new BigDecimal(10),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001")
        );

        // Экспорт значений секанса в CSV (файл csv/sec.csv)
        final Sec sec = new Sec(cos);
        CsvWriter.write(
                "csv/sec.csv",
                sec,
                new BigDecimal(-10),
                new BigDecimal(10),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001")
        );

        // Экспорт значений косеканса в CSV (файл csv/csc.csv)
        final Csc csc = new Csc(sin);
        CsvWriter.write(
                "csv/csc.csv",
                csc,
                new BigDecimal(-10),
                new BigDecimal(10),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001")
        );

        final Ln ln = new Ln();
        CsvWriter.write(
                "csv/ln.csv",
                ln,
                new BigDecimal(0),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001")
        );

        final Log log2 = new Log(2);
        CsvWriter.write(
                "csv/log2.csv",
                log2,
                new BigDecimal(0),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001")
        );

        final Log log10 = new Log(10);
        CsvWriter.write(
                "csv/log10.csv",
                log10,
                new BigDecimal(0),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001")
        );

        // Экспорт значений системы функций в CSV (файл csv/func.csv)
        // Обратите внимание: в нашей реализации FunctionsSystem определена только ветвь x ≤ 0,
        // поэтому диапазон выбран от -2 до 0.
        final FunctionSystem func = new FunctionSystem();
        CsvWriter.write(
                "csv/func.csv",
                func,
                new BigDecimal(-10),
                new BigDecimal(10),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001")
        );
    }
}

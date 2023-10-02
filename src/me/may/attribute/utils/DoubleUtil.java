package me.may.attribute.utils;

import java.text.DecimalFormat;

public class DoubleUtil {

    public static double formatDouble(double data, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return Double.valueOf(df.format(data));
    }
}

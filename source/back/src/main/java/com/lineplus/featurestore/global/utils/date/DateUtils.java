package com.lineplus.featurestore.global.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String convertDateToStringFormat(Date date, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);
        return result;
    }
}

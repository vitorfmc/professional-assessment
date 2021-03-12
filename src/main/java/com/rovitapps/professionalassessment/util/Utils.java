package com.rovitapps.professionalassessment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {

    public static Date strToDate(String date) throws ParseException {
        return (new SimpleDateFormat("dd/MM/yyyy")).parse(date);
    }

    public static String getUuid(){
        return UUID.randomUUID().toString();
    }

}

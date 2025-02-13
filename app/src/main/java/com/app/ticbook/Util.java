package com.app.ticbook;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static String timeFormat(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", new Locale("id"));
        //SimpleDateFormat dateFormatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatTimeOnly = new SimpleDateFormat("hh:mm", new Locale("id"));

        String time = "";

        try {
            Date date = dateFormat.parse(value);

            if (date != null) {
                time = dateFormatTimeOnly.format(date);
            }
        } catch (ParseException e) {
            Log.d("2504", e.getMessage(), e);
        }
        return time;
    }

    public static String dateFormat(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", new Locale("id"));
        SimpleDateFormat dateFormatTimeOnly = new SimpleDateFormat("dd/MM/yyyy", new Locale("id"));

        String dates = "";

        try {
            Date date = dateFormat.parse(value);

            if (date != null) {
                dates = dateFormatTimeOnly.format(date);
            }
        } catch (ParseException e) {
            Log.d("2504", e.getMessage(), e);
        }
        return dates;
    }
}

package com.iitr.cfd.aasha.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Harjot on 07-Feb-17.
 */

public class Utils {

    public static boolean isOlder(String time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        Date appointmentTime = null;
        try {
            appointmentTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (appointmentTime != null && new Date().after(appointmentTime)) {
            return true;
        }
        return false;
    }

    public static boolean isOlder(String time1, String time2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(time1);
            date2 = formatter.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 != null && date2 != null && date2.after(date1)) {
            return true;
        }
        return false;
    }

}

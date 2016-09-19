package rashjz.info.bakuposter.com.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rasha_000 on 10/18/2015.
 */
public class DateUtil {
    public static String dateToString(Date date) {
        Format formatter = new SimpleDateFormat("MM-dd-YYYY");
        String s = formatter.format(date);
        return s;
    }

    public static Date strToDate(String sdate) {
        Date date = null;
        try {
//            System.out.println(sdate);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            date = formatter.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date dayBeforeDate(Date dt, int days) {//not in use
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days); //days may me minus and plus
        dt = c.getTime();
        return c.getTime();
    }

    public static String dateToString(Date date, String format) {
        Format formatter = new SimpleDateFormat(format);
        String s = formatter.format(date.getTime());
        return s;
    }

    public static void main(String[] args) {
        System.out.println(strToDate(  "9/22/2012"));
    }



}

package ru.it.sd.hp.Utils;

import com.hp.itsm.ssp.beans.SdClientBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class DateUtils {
    private transient Field f_date_calc;
    private transient Field f_timeZone;
    private transient Method m_sd_date2java_local_date;
    private transient Method m_sd_date2java_date;
    private transient Method m_java_date2sd_date;
    private transient Method m_current_tz_instance;

    /**
     * Конвертирует дату(28.06.1994) в double (41789,785)
     * @param date дата
     * @return double(формат времени SD) кол дней с 1900/1/1
     */
    public static double toSDDate(Date date){
        try {
            Field f_date_calc = SdClientBean.class.getDeclaredField("date_calc");
            Method toSDDATE =  f_date_calc.getType().getDeclaredMethod("java_date2sd_date", Date.class);
            toSDDATE.setAccessible(true);
            return (Double)toSDDATE.invoke(null, date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not get SD date");
        }
    }
}

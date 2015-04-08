package io.ignit.bebumhelper.utils;

import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {

	private static final String DATE_FORMAT = "HH:mm dd/MM/yyyy";

	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT,
				Locale.getDefault());
		return sdf.format(date);
	}

	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT,
				Locale.getDefault());
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static boolean isDatePickerSetTo(DatePicker picker, Calendar calendar) {
		if (picker.getDayOfMonth() != calendar.get(Calendar.DAY_OF_MONTH)) {
			return false;
		}
		if (picker.getMonth() != calendar.get(Calendar.MONTH)) {
			return false;
		}
		if (picker.getYear() != calendar.get(Calendar.YEAR)) {
			return false;
		}
		return true;
	}

	public static int countHoursToNow (Calendar from){
		Calendar calendar = Calendar.getInstance();
		Date a = calendar.getTime();
		Date b = from.getTime();
	    long result	 = a.getTime() - b.getTime();
	    return (int)TimeUnit.MILLISECONDS.toHours(result);
	}
}

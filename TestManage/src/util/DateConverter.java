/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 *
 * @author Administrator
 */
public class DateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class clazz) {
		String timeString = values[0];
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd-HH:mm",Locale.CHINESE);
		Date date = null;
		try {
			 date = df.parse(timeString);
		} catch (ParseException ex) {

		}
		return date;

	}

	@Override
	public String convertToString(Map context, Object object) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd-HH:mm",Locale.CHINESE);
		Date date = (Date)object;
		return df.format(date);
	}

}

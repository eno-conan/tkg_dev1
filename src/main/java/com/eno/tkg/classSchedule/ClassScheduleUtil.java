package com.eno.tkg.classSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClassScheduleUtil {
	
	// 文字列型の日付をDate型に変更
	static Date convertStrDateToDateType(final String dateStr) {
		String strDate = dateStr.replace("-", "/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}

package com.eno.tkg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UseOverFunction {
	
	// 取得データをJson形式にする
	public static String getDataToJsonFormat(Object returnJsonLiteral) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String strJson = "";
		strJson = mapper.writeValueAsString(returnJsonLiteral);
		return strJson;
	}
	
	//日付を文字列型の日付に
	public static String dateToDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	// 文字列型の日付をDate型に変更
	public static Date convertStrDateToDateType(final String dateStr) {
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

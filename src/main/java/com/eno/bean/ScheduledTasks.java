package com.eno.bean;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	// 月曜日から金曜日の間、5秒間隔で実行する
//	@Scheduled(cron = "${scheduler.cron}")
//	public void task4() {
//		Date now = new Date();
//		System.out.println(now);
//	}

}

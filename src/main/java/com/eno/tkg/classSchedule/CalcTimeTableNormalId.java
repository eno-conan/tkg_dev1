package com.eno.tkg.classSchedule;

import java.util.LinkedHashMap;
import java.util.Map;

import com.eno.tkg.classSchedule.item.DayOfWeekTimeTableNormal;
import com.eno.tkg.classSchedule.item.PeriodTimeTableNormal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class CalcTimeTableNormalId {

	private int dayOfWeekNumber;
	private int period;
	Map<Integer, Integer> dayOfWeekTimeTableNormalMap;
	Map<Integer, Integer> periodTimeTableNormalMap;

	public CalcTimeTableNormalId(int dayOfWeekNumber, int period) {
		composeDayOfWeekMap();
		composePeriodMap();
		this.dayOfWeekNumber = dayOfWeekNumber;
		this.period = period;
	}

	/**
	 * time_table_normalのIDを計算 各曜日の6コマのID：1,4,7,10,13,16（月曜日スタート）
	 * 各曜日の7コマのID：6コマの値に+1 各曜日の8コマのID：6コマの値に+2
	 *
	 */
	public int calcResult() {
		return getValueFromdayOfWeekMap() + getValueFromdayPeriodMap();
	}

	/**
	 * 週の何日目か変換を行う
	 */
	private void composeDayOfWeekMap() {
		dayOfWeekTimeTableNormalMap = new LinkedHashMap<>();
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.MONDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.MONDAY.getElementTimeTableNormalId());
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.TUESDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.TUESDAY.getElementTimeTableNormalId());
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.WEDNESDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.WEDNESDAY.getElementTimeTableNormalId());
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.THURSDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.THURSDAY.getElementTimeTableNormalId());
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.FRIDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.FRIDAY.getElementTimeTableNormalId());
		dayOfWeekTimeTableNormalMap.put(DayOfWeekTimeTableNormal.SATURDAY.getDayOfWeekNumber(),
				DayOfWeekTimeTableNormal.SATURDAY.getElementTimeTableNormalId());
	}

	/**
	 * 何コマ目か変換を行う
	 */
	private void composePeriodMap() {
		periodTimeTableNormalMap = new LinkedHashMap<>();
		periodTimeTableNormalMap.put(PeriodTimeTableNormal.PERIOD_6.getInputPeriod(),
				PeriodTimeTableNormal.PERIOD_6.getElementTimeTableNormalId());
		periodTimeTableNormalMap.put(PeriodTimeTableNormal.PERIOD_7.getInputPeriod(),
				PeriodTimeTableNormal.PERIOD_7.getElementTimeTableNormalId());
		periodTimeTableNormalMap.put(PeriodTimeTableNormal.PERIOD_8.getInputPeriod(),
				PeriodTimeTableNormal.PERIOD_8.getElementTimeTableNormalId());
	}

	private int getValueFromdayOfWeekMap() {
		return dayOfWeekTimeTableNormalMap.get(this.dayOfWeekNumber);
	}

	private int getValueFromdayPeriodMap() {
		return periodTimeTableNormalMap.get(this.period);
	}

}

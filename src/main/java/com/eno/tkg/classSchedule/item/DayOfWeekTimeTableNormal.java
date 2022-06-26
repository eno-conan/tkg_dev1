package com.eno.tkg.classSchedule.item;

public enum DayOfWeekTimeTableNormal {

	MONDAY(2, 1), TUESDAY(3, 4), WEDNESDAY(4, 7), THURSDAY(5, 10), FRIDAY(6, 13), SATURDAY(7, 16);

	DayOfWeekTimeTableNormal(int dayOfWeekNumber, int elementTimeTableNormalId) {
		this.dayOfWeekNumber = dayOfWeekNumber;
		this.elementTimeTableNormalId = elementTimeTableNormalId;
	}

	private int dayOfWeekNumber;
	private int elementTimeTableNormalId;

	public int getDayOfWeekNumber() {
		return dayOfWeekNumber;
	}

	public int getElementTimeTableNormalId() {
		return elementTimeTableNormalId;
	}

}

package com.eno.tkg.classSchedule;

public enum PeriodTimeTableNormal {

	PERIOD_6(6, 0), PERIOD_7(7, 1), PERIOD_8(8, 2);

	PeriodTimeTableNormal(int inputPeriod, int elementTimeTableNormalId) {
		this.inputPeriod = inputPeriod;
		this.elementTimeTableNormalId = elementTimeTableNormalId;
	}

	private int inputPeriod;
	private int elementTimeTableNormalId;

	public int getInputPeriod() {
		return inputPeriod;
	}

	public int getElementTimeTableNormalId() {
		return elementTimeTableNormalId;
	}

}

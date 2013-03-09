package com.amcgavin.playerlogger;

public class Timesheet {

	private int base;
	private long time;
	private int id;
	public Timesheet(int id, int base, long time) {
		this.id = id;
		this.base = base;
		this.time = time;
	}
	
	public int getId() {
		return this.id;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public int getBase() {
		return this.base;
	}
}

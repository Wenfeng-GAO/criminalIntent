package com.wenfeng.officecrime;

import java.util.Date;
import java.util.UUID;

public class Crime {

	private UUID crimeId;
	private String title;
	private Date date;
	private boolean solved;
	
	public Crime() {
		crimeId = UUID.randomUUID();
		date = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UUID getCrimeId() {
		return crimeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}
	
	
}

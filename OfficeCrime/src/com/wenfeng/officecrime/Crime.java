package com.wenfeng.officecrime;

import java.util.UUID;

public class Crime {

	private UUID crimeId;
	private String title;
	
	public Crime() {
		crimeId = UUID.randomUUID();
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
	
	
}

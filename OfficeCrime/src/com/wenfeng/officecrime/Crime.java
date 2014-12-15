package com.wenfeng.officecrime;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";

	private UUID crimeId;
	private String title;
	private Date date;
	private boolean solved;
	private Photo photo;
	
	public Crime() {
		crimeId = UUID.randomUUID();
		date = new Date();
	}

	public Crime(JSONObject json) throws JSONException {
		crimeId = UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLE)) {
			title = json.getString(JSON_TITLE);
		}
		solved = json.getBoolean(JSON_SOLVED);
		date = new Date(json.getLong(JSON_DATE));
		
		if (json.has(JSON_PHOTO)) {
			photo = new Photo(json.getJSONObject(JSON_PHOTO));
		}
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
	
	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return title;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, crimeId.toString());
		json.put(JSON_TITLE, title);
		json.put(JSON_SOLVED, solved);
		json.put(JSON_DATE, date.getTime());
		if (photo != null) {
			json.put(JSON_PHOTO, photo.toJSON()); 
		}
		return json;
	}
}

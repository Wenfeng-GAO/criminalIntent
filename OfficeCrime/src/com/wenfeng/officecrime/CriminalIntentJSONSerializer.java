package com.wenfeng.officecrime;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class CriminalIntentJSONSerializer {
	
	private Context context;
	private String filename;
	
	public CriminalIntentJSONSerializer(Context context, String filename) {
		this.context = context;
		this.filename = filename;
	}
	
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try {
			// open and read the file into a StringBuilder
			InputStream inputStream = context.openFileInput(filename);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			// parse JSON using JSONTokener 
			JSONArray jsonArray = (JSONArray) new JSONTokener(
					jsonString.toString()).nextValue();
			// build the array of crimes from JSONObjects
			for (int i = 0; i < jsonArray.length(); i++) {
				crimes.add(new Crime(jsonArray.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			// ignore this one; it happens when starting fresh
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		return crimes;
	}
	
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
		// build an array in JSON
		JSONArray jsonArray = new JSONArray();
		for(Crime crime : crimes) {
			jsonArray.put(crime.toJSON());
		}
		// write the file to disk
		Writer writer = null;
		try {
			OutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
			writer = new OutputStreamWriter(outputStream);
			writer.write(jsonArray.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}

}

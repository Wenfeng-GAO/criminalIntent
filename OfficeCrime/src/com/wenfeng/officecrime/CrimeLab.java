package com.wenfeng.officecrime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = CrimeLab.class.getSimpleName();
	private static final String FILENAME = "crimes.json";
//	private static final String FILENAME = Environment.getExternalStorageDirectory().getPath();
	
	private CriminalIntentJSONSerializer mSerializer;
	
	private static CrimeLab sCrimeLab;
	private Context mContext;
	private ArrayList<Crime> crimes;
	
	private CrimeLab(Context context) {
		mContext = context;
		mSerializer = new CriminalIntentJSONSerializer(mContext, FILENAME);
		
		try {
			crimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			crimes = new ArrayList<Crime>();
			Log.e(TAG, "Error loading crimes: ", e);
		}
	}
	
	public static CrimeLab get(Context context) {
		if(sCrimeLab == null) {
			sCrimeLab = new CrimeLab(context.getApplicationContext());
		}
		return sCrimeLab;
	}
	
	public void addCrime(Crime crime) {
		crimes.add(crime);
	}
	
	public ArrayList<Crime> getCrimes() {
		return crimes;
	}
	
	public Crime getCrime(UUID crimeId) {
		for(Crime crime : crimes) {
			if(crime.getCrimeId().equals(crimeId)) {
				return crime;
			}
		}
		return null;
	}
	
	public void deleteCrime(Crime crime) {
		crimes.remove(crime);
	}
	
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(crimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (JSONException e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		} catch (IOException e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
	
}

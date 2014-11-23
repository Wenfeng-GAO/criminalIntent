package com.wenfeng.officecrime;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	private static CrimeLab sCrimeLab;
	private Context mContext;
	private ArrayList<Crime> crimes;
	
	private CrimeLab(Context context) {
		mContext = context;
		crimes = new ArrayList<Crime>();
		
		for(int i = 0; i < 100; i++) {
			Crime crime = new Crime();
			crime.setTitle("Crime #" + i);
			crime.setSolved(i%2 == 0);
			crimes.add(crime);
		}
	}
	
	public static CrimeLab get(Context context) {
		if(sCrimeLab == null) {
			sCrimeLab = new CrimeLab(context.getApplicationContext());
		}
		return sCrimeLab;
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
	
}

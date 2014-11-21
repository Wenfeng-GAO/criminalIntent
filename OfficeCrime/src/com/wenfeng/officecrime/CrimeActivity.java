package com.wenfeng.officecrime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class CrimeActivity extends FragmentActivity {
	private static final String TAG = CrimeActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
		if(fragment == null) {
			fragment = new CrimeFragment();
			fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
			Log.d(TAG, "fragment == null");
		}
	}


}

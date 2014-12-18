package com.wenfeng.officecrime;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

	@Override
	protected Fragment createFragment() {
		return new CrimeListFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}

	@Override
	public void onCrimeSelected(Crime crime) {
		if (findViewById(R.id.fragmentDetailContainer) == null) {
			// Start an instance of CrimePagerActivity
			Intent intent = new Intent(this, CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.INTENT_EXTRA_KEY_CRIME_ID, crime.getCrimeId());
			startActivity(intent);
		} else {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.fragmentDetailContainer);
			Fragment newDetail = CrimeFragment.newInstance(crime.getCrimeId());
			
			if (oldDetail != null) {
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.fragmentDetailContainer, newDetail);
			ft.commit();
		}
	}

	@Override
	public void onCrimeUpdated(Crime crime) {
		FragmentManager fm = getSupportFragmentManager();
		CrimeListFragment listFragment = (CrimeListFragment) fm.findFragmentById(R.id.fragment_container);
		listFragment.updateUI();
	}
	
	
}

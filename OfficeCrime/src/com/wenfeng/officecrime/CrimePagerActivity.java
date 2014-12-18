package com.wenfeng.officecrime;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CrimePagerActivity extends FragmentActivity implements CrimeFragment.Callbacks {
	private ViewPager viewPager;
	private ArrayList<Crime> crimes;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		viewPager = new ViewPager(getApplicationContext());
		viewPager.setId(R.id.view_pager);
		setContentView(viewPager);
		
		crimes = CrimeLab.get(this).getCrimes();
		
		viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return crimes.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				return CrimeFragment.newInstance(crimes.get(position).getCrimeId());
			}
		});
		
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.INTENT_EXTRA_KEY_CRIME_ID);
		for(int i = 0; i < crimes.size(); i++) {
			if(crimes.get(i).getCrimeId().equals(crimeId)) {
				viewPager.setCurrentItem(i);
				setTitle(crimes.get(i).getTitle());
				break;
			}
		}
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				setTitle(crimes.get(position).getTitle());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	@Override
	public void onCrimeUpdated(Crime crime) {
		// TODO Auto-generated method stub
		
	}

}

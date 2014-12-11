package com.wenfeng.officecrime;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CrimeListFragment extends ListFragment {
	private ArrayList<Crime> crimes;
	private boolean isSubtitleVisible;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.crime_list_title);
		crimes = CrimeLab.get(getActivity()).getCrimes();
		setListAdapter(new CrimeAdapter(crimes));
		setRetainInstance(true);
		isSubtitleVisible = false;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if(isSubtitleVisible && showSubtitle != null) {
			showSubtitle.setTitle(R.string.menubar_hide_subtitle);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_crime, container, false);
		View emptyView = view.findViewById(android.R.id.empty);
		Button button = (Button) emptyView.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Crime crime = new Crime();
				CrimeLab.get(getActivity()).addCrime(crime);
				Intent intent = new Intent(getActivity().getApplicationContext(), CrimePagerActivity.class);
				intent.putExtra(CrimeFragment.INTENT_EXTRA_KEY_CRIME_ID, crime.getCrimeId());
				startActivityForResult(intent, 0);
			}
		});
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(isSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		return view;
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent intent = new Intent(getActivity().getApplicationContext(), CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.INTENT_EXTRA_KEY_CRIME_ID, crime.getCrimeId());
			startActivityForResult(intent, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.menubar_hide_subtitle);
				isSubtitleVisible = true;
			} else {
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.menubar_show_subtitle);
				isSubtitleVisible = false;
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
		
		Intent intent = new Intent(getActivity().getApplicationContext(), CrimePagerActivity.class);
		intent.putExtra(CrimeFragment.INTENT_EXTRA_KEY_CRIME_ID, crime.getCrimeId());
		startActivity(intent);
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.crime_list_item, null);
			}
			
			TextView textViewCrimeTitle = (TextView) convertView.findViewById(R.id.textview_crime_title);
			TextView textViewCrimeDate = (TextView) convertView.findViewById(R.id.textview_crime_date);
			CheckBox checkBoxCrimeSolved = (CheckBox) convertView.findViewById(R.id.checkbox_crime_solved);
			textViewCrimeTitle.setText(getItem(position).getTitle());
			textViewCrimeDate.setText(DateFormat.format("yyy MMM dd [k:m]" +
					"", getItem(position).getDate()));
			checkBoxCrimeSolved.setChecked(getItem(position).isSolved());
			
			return convertView;
		}
		
	}
	
}

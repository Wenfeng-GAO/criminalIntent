package com.wenfeng.officecrime;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	private ArrayList<Crime> crimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crime_list_title);
		crimes = CrimeLab.get(getActivity()).getCrimes();
		setListAdapter(new CrimeAdapter(crimes));
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

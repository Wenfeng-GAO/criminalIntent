package com.wenfeng.officecrime;

import java.text.SimpleDateFormat;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	public static final String INTENT_EXTRA_KEY_CRIME_ID = "com.wenfeng.offcecrime.crimefragment.crimeId";
	private Crime crime;
	private EditText editTextCrimeTitle;
	private Button buttonCrimeDate;
	private CheckBox checkBoxCrimeSolved;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID) getArguments().getSerializable(INTENT_EXTRA_KEY_CRIME_ID);
		crime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(INTENT_EXTRA_KEY_CRIME_ID, crimeId);
		CrimeFragment crimeFragment = new CrimeFragment();
		crimeFragment.setArguments(args);
		return crimeFragment;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, container, false);
		
		// title editText
		editTextCrimeTitle = (EditText) view.findViewById(R.id.edittext_crime_title);
		editTextCrimeTitle.setText(crime.getTitle());
		editTextCrimeTitle.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				crime.setTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// date button
		buttonCrimeDate = (Button) view.findViewById(R.id.button_crime_date);
		buttonCrimeDate.setText(new SimpleDateFormat("EEEE, MMM dd, yyyy").format(crime.getDate()));
		buttonCrimeDate.setEnabled(false);
		
		// solved checkBox
		checkBoxCrimeSolved = (CheckBox) view.findViewById(R.id.checkbox_crime_solved);
		checkBoxCrimeSolved.setChecked(crime.isSolved());
		checkBoxCrimeSolved.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				crime.setSolved(isChecked);
			}
		});
		
		return view;
	}
	

}

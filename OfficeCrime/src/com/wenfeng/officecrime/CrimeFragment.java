package com.wenfeng.officecrime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	private static final String TAG = CrimeFragment.class.getSimpleName();
	public static final String INTENT_EXTRA_KEY_CRIME_ID = "com.wenfeng.offcecrime.crimefragment.crimeId";
	private static final String DIALOG_DATA = "com.wenfeng.offcecrime.date";
	private static final int DIALOG_REQUEST_CODE = 1;
	
	private Crime crime;
	private EditText editTextCrimeTitle;
	private Button buttonCrimeDate;
	private CheckBox checkBoxCrimeSolved;
	
	private Button buttonCrimeTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		UUID crimeId = (UUID) getArguments().getSerializable(INTENT_EXTRA_KEY_CRIME_ID);
		crime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(INTENT_EXTRA_KEY_CRIME_ID, crimeId);
		CrimeFragment crimeFragment = new CrimeFragment();
		crimeFragment.setArguments(args);
		return crimeFragment;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, container, false);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
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
		buttonCrimeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				DataPickerFragment dateDialog = DataPickerFragment.newInstance(crime.getDate());
				dateDialog.setTargetFragment(CrimeFragment.this, DIALOG_REQUEST_CODE);
				dateDialog.show(getActivity().getSupportFragmentManager(), DIALOG_DATA);
			}
		});
		
		// time button
		buttonCrimeTime = (Button) view.findViewById(R.id.button_crime_time);
		updateButtonText();
		buttonCrimeTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
//				TimePickerFragment timeDialog = TimePickerFragment.newInstance(crime.getDate());
//				timeDialog.setTargetFragment(CrimeFragment.this, DIALOG_REQUEST_CODE);
//				timeDialog.show(getActivity().getSupportFragmentManager(), DIALOG_DATA);
				
				// alert the choice dialog
				ChooseDialogFragment chooseDialog = ChooseDialogFragment.newInstance(crime.getDate());
				chooseDialog.setTargetFragment(CrimeFragment.this, DIALOG_REQUEST_CODE);
				chooseDialog.show(getActivity().getSupportFragmentManager(), DIALOG_DATA);
			}
		});
		
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

	@SuppressLint("SimpleDateFormat")
	private void updateButtonText() {
		buttonCrimeDate.setText(new SimpleDateFormat("EEEE, MMM dd, yyyy").format(crime.getDate()));
		buttonCrimeTime.setText(new SimpleDateFormat("HH:mm").format(crime.getDate()));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			if(requestCode == DIALOG_REQUEST_CODE) {
				Date date = (Date) data.getSerializableExtra(DataPickerFragment.EXTRA_KEY_DATE);
				crime.setDate(date);
				updateButtonText();
			}
		}
	}
	

}

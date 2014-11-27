package com.wenfeng.officecrime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DataPickerFragment extends DialogFragment {
	public static final String EXTRA_KEY_DATE = "com.wenfeng.officecrime.extrakey.date";
	private Date date;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		date = (Date) getArguments().getSerializable(EXTRA_KEY_DATE);
		
		// create a calendar to get year, month and day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		// init the date of DatePicker
		DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				date = new GregorianCalendar(year, month, day).getTime();
				getArguments().putSerializable(EXTRA_KEY_DATE, date);
			}
		});
		
		
		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle(R.string.date_picker_dialog_title)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
	
	public static DataPickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_KEY_DATE, date);
		DataPickerFragment dpf = new DataPickerFragment();
		dpf.setArguments(args);
		
		return dpf;
	}
	
	private void sendResult(int resultCode) {
		if(getTargetFragment() != null) {
			Intent intent = new Intent();
			intent.putExtra(EXTRA_KEY_DATE, date);
			getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
		}
	}

}

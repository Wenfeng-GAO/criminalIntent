package com.wenfeng.officecrime;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	Date mDate;
	TimePicker mTimePicker;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		mDate = (Date) getArguments().getSerializable(DataPickerFragment.EXTRA_KEY_DATE);
		
		// initialize the time
		mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
		mTimePicker.setIs24HourView(true);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(minute);
		
		
		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle("Time of crime: ")
			.setPositiveButton(android.R.string.ok, null)
			.create();
	}

	public static TimePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(DataPickerFragment.EXTRA_KEY_DATE, date);
		TimePickerFragment tpf = new TimePickerFragment();
		tpf.setArguments(args);
		
		return tpf;
	}
}

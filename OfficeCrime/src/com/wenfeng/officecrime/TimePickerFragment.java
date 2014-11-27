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
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimePickerFragment extends DialogFragment {
	private static final String TAG = TimePickerFragment.class.getSimpleName();
	private Date mDate;
	private TimePicker mTimePicker;
	private Calendar calendar;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		mDate = (Date) getArguments().getSerializable(DataPickerFragment.EXTRA_KEY_DATE);
		
		// initialize the time
		mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
		mTimePicker.setIs24HourView(true);
		calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(minute);
		mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				mDate = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute)
				.getTime();
				getArguments().putSerializable(DataPickerFragment.EXTRA_KEY_DATE, mDate);
				Log.d(TAG, "Time:" + hour + ":" + minute);
				
			}
		});
		
		
		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle("Time of crime: ")
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}

	public static TimePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(DataPickerFragment.EXTRA_KEY_DATE, date);
		TimePickerFragment tpf = new TimePickerFragment();
		tpf.setArguments(args);
		
		return tpf;
	}
	
	private void sendResult(int resultCode) {
		if(!(getTargetFragment() == null)) {
			Intent intent = new Intent();
			intent.putExtra(DataPickerFragment.EXTRA_KEY_DATE, mDate);
			getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
		}
	}
	
}

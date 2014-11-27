package com.wenfeng.officecrime;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseDialogFragment extends DialogFragment {
	private static final int DIALOG_REQUEST_CODE = 1;
	private static final String CHOOSEDIALOGFRAGMENT_SHOW_TAG = "com.wenfeng.officecrime.choosedialogfragment.show.tag";
	private Date date;
	private Button buttonDate, buttonTime;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.choose_dialog, null);
		date = (Date) getArguments().getSerializable(DataPickerFragment.EXTRA_KEY_DATE);
		
		buttonDate = (Button) view.findViewById(R.id.button_date);
		buttonDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DataPickerFragment dpf = DataPickerFragment.newInstance(date);
				dpf.setTargetFragment(ChooseDialogFragment.this, DIALOG_REQUEST_CODE);
				dpf.show(getActivity().getSupportFragmentManager(), CHOOSEDIALOGFRAGMENT_SHOW_TAG);
			}
		});
		
		buttonTime = (Button) view.findViewById(R.id.button_time);
		buttonTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerFragment tpf = TimePickerFragment.newInstance(date);
				tpf.setTargetFragment(ChooseDialogFragment.this, DIALOG_REQUEST_CODE);
				tpf.show(getActivity().getSupportFragmentManager(), CHOOSEDIALOGFRAGMENT_SHOW_TAG);
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle("You wanna change the Date or the Time?")
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}

	public static ChooseDialogFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(DataPickerFragment.EXTRA_KEY_DATE, date);
		ChooseDialogFragment dialog = new ChooseDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
	}
	
	private void sendResult(int resultCode) {
		Intent intent = new Intent();
		intent.putExtra(DataPickerFragment.EXTRA_KEY_DATE, date);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			date = (Date) data.getSerializableExtra(DataPickerFragment.EXTRA_KEY_DATE);
		}
	}
	
	
}

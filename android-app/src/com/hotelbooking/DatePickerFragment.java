package com.hotelbooking;

import java.util.Calendar;
import java.util.Date;

import com.hotelbooking.handler.DatePickerHandler;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener{

	private Context context;
	private DatePickerHandler handler;
	
	public DatePickerFragment() {
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setHandler(DatePickerHandler handler) {
		this.handler = handler;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(context, this, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, monthOfYear, dayOfMonth);
		handler.onSetDate(calendar);
	}

}

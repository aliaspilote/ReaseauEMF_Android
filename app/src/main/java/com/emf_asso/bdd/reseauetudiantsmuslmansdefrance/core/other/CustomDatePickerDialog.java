package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by taha on 20/11/2015.
 */
public class CustomDatePickerDialog extends DatePickerDialog implements DatePicker.OnDateChangedListener {

    private DatePickerDialog mDatePicker;

    @SuppressLint("NewApi")
    public CustomDatePickerDialog(Context context, int theme, OnDateSetListener callBack,
                                  int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);
        mDatePicker = new DatePickerDialog(context, theme, callBack, year, monthOfYear, dayOfMonth);
        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        mDatePicker.getDatePicker().init(y, m, d, this);

        updateTitle(year, monthOfYear, dayOfMonth);

    }

    public void onDateChanged(DatePicker view, int year,
                              int month, int day) {
        updateTitle(year, month, day);
    }

    private void updateTitle(int year, int month, int day) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        mDatePicker.setTitle(getFormat().format(mCalendar.getTime()));

    }

    public DatePickerDialog getPicker() {

        return this.mDatePicker;
    }

    /*
     * the format for dialog tile,and you can override this method
     */
    public SimpleDateFormat getFormat() {
        return new SimpleDateFormat(DataContext.dateDisplayFormat);
    }

    ;
}
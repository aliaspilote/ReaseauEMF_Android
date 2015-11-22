package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * Created by taha on 22/11/2015.
 */
public class CreateDate extends Activity {
    public ImageButton ib;
    public Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private DatePickerDialog.OnDateSetListener datePickerListener;

    public CreateDate() {
    }

    public void initDate() {
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                et.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                        + selectedYear);
            }
        };

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

       /* ib.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                CustomDatePickerDialog dp = new CustomDatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,  datePickerListener, year, month, day);

                DatePickerDialog obj = dp.getPicker();

                obj.show();
            }
        });*/
    }

    public ImageButton getIb() {
        return ib;
    }

    public void setIb(ImageButton ib) {
        this.ib = ib;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public EditText getEt() {
        return et;
    }

    public void setEt(EditText et) {
        this.et = et;
    }

    public DatePickerDialog.OnDateSetListener getDatePickerListener() {
        return datePickerListener;
    }

    public void setDatePickerListener(DatePickerDialog.OnDateSetListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

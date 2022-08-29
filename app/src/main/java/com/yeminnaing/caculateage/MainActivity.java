package com.yeminnaing.caculateage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.yeminnaing.caculateage.databinding.ActivityMainBinding;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        binding.btToday.setText(date);

        binding.btBirth.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener1, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                binding.btBirth.setText(date);
            }
        };
        binding.btnCalculate.setOnClickListener(v -> {
            String sDate = binding.btBirth.getText().toString();
            String eDate = binding.btToday.getText().toString();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date1 = simpleDateFormat1.parse(sDate);
                Date date2 = simpleDateFormat1.parse(eDate);
                long startdate = date1.getTime();
                long endDate = date2.getTime();
                if (startdate <= endDate) {
                    org.joda.time.Period period = new Period(startdate, endDate, PeriodType.yearMonthDay());
                    int years = period.getYears();
                    int months = period.getMonths();
                    int days = period.getDays();

                    // show the final output
                    binding.tvResult.setText(years + " Years |" + months + "Months |" + days + "Days");
                } else {
                    // show message
                    Toast.makeText(MainActivity.this, "BirthDate should not be larger than today's date!", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });//btn caculate


    }
}
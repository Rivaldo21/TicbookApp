package com.app.ticbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDialogFragment extends DialogFragment {

    public interface OnDateSelectedListener {
        void onDateSelected(String date);
    }

    private OnDateSelectedListener listener;

    public static CalendarDialogFragment newInstance(String resourceType, OnDateSelectedListener listener) {
        CalendarDialogFragment fragment = new CalendarDialogFragment();

        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar, container, false);

        String resourceType = "", timeStart = "";
        Boolean isStartTime = true;

        Bundle mArgs = getArguments();
        resourceType = mArgs.getString("resourceType", "");
        isStartTime = mArgs.getBoolean("isStartTime", true);
        timeStart = mArgs.getString("timeStart", "");
        String desc;
        if (resourceType == "exe") {
            desc = mArgs.getString("desc", "");
        } else {
            desc = "";
        }

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        TextView tvTitlePickDate = view.findViewById(R.id.tvTitlePickDate);
        TextView tvDescPickDate = view.findViewById(R.id.tvDescPickDate);

        if (!isStartTime){
            final Calendar cMin = Calendar.getInstance();
            int year = 0;
            int month = 0;
            int day = 0;

//            2025-01-02T11:44:00Z
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

            SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date dateStart = dateFormatNew.parse(timeStart);

                year = Integer.parseInt(yearFormat.format(dateStart));
                month = Integer.parseInt(monthFormat.format(dateStart));
                day = Integer.parseInt(dayFormat.format(dateStart));

            } catch (Exception e){

            }
            cMin.set(year,(month - 1),day);
            datePicker.setMinDate(cMin.getTimeInMillis());
        }

        if (resourceType.equals("Vehicle")){
            tvTitlePickDate.setText("Data no oras sai?");
            tvDescPickDate.setText("Prenxe data no oras sai.");
        }

        buttonContinue.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String selectedDate = day + "/" + month + "/" + year;

            if (listener != null) {
                listener.onDateSelected(selectedDate);
            }
            dismiss();
        });

        imgClose.setOnClickListener(V->{
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}






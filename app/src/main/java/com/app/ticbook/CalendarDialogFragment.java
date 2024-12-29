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

import java.util.Calendar;

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

        String resourceType = "";
        Bundle mArgs = getArguments();
        resourceType = mArgs.getString("resourceType", "");

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        TextView tvTitlePickDate = view.findViewById(R.id.tvTitlePickDate);
        TextView tvDescPickDate = view.findViewById(R.id.tvDescPickDate);

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






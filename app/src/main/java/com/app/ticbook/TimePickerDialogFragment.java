package com.app.ticbook;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {

    public interface OnTimeSelectedListener {
        void onTimeSelected(String time);
    }

    private OnTimeSelectedListener listener;

    String resourceType = "";
    boolean isStartTime;

    public static TimePickerDialogFragment newInstance(String resourceType, String selectedDate, boolean isStartTime, OnTimeSelectedListener listener) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.listener = listener;
        fragment.resourceType = resourceType;
        fragment.isStartTime = isStartTime;
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("selectedDate", selectedDate);
        args.putBoolean("isStartTime", isStartTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_time_picker, container, false);

        TimePicker timePicker = view.findViewById(R.id.timePicker);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvDesc = view.findViewById(R.id.tvDesc);

        if (resourceType.equals("exe") && isStartTime) {
            tvTitle.setText("Oras komesa?");
            tvDesc.setText("Hili oras komesa enkontru tuku hira..");
        } else if (resourceType.equals("exe") && !isStartTime) {
            tvTitle.setText("Oras komesa?");
            tvDesc.setText("Hili data no oras remata iha kraik.");
        }

        buttonContinue.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String selectedTime = hour + ":" + minute;

            if (listener != null) {
                listener.onTimeSelected(selectedTime);
            }
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





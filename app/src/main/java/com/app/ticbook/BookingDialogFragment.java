package com.app.ticbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class BookingDialogFragment extends DialogFragment {

    private String resourceType; // Room or Vehicle
    private Calendar startDateTime;
    private Calendar endDateTime;
    private String selectedDepartment;
    private String description;

    public static BookingDialogFragment newInstance(String resourceType) {
        BookingDialogFragment dialog = new BookingDialogFragment();
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_booking, container, false);

        resourceType = getArguments() != null ? getArguments().getString("resourceType") : "Room";


        // Initialize UI components
//        Button btnSelectDate = view.findViewById(R.id.btnSelectDate);
//        Button btnSelectTime = view.findViewById(R.id.btnSelectTime);
//        Button btnContinue = view.findViewById(R.id.btnContinue);
//        TextView txtStepDescription = view.findViewById(R.id.txtStepDescription);

        // Update description based on resource type
//        txtStepDescription.setText("Booking " + resourceType);

        // Select date
//        btnSelectDate.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
//                    (view1, year, month, dayOfMonth) -> {
//                        if (startDateTime == null) startDateTime = Calendar.getInstance();
//                        startDateTime.set(year, month, dayOfMonth);
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//            datePickerDialog.show();
//        });

        // Select time
//        btnSelectTime.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
//                    (view12, hourOfDay, minute) -> {
//                        if (startDateTime == null) startDateTime = Calendar.getInstance();
//                        startDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                        startDateTime.set(Calendar.MINUTE, minute);
//                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
//            timePickerDialog.show();
//        });
//
//        // Continue to next step
//        btnContinue.setOnClickListener(v -> {
//            if (startDateTime == null) {
//                Toast.makeText(requireContext(), "Please select a start date and time", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            openNextStep();
//        });

        return view;
    }

    private void openNextStep() {
        // Replace with the next step logic (e.g., select type, department, etc.)
        Toast.makeText(requireContext(), "Proceeding to the next step", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}

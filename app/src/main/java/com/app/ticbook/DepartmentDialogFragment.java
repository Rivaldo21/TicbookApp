package com.app.ticbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DepartmentDialogFragment extends DialogFragment {

    public interface OnDepartmentSelectedListener {
        void onDepartmentSelected(String selectedDepartment);
    }

    private OnDepartmentSelectedListener listener;

    public static DepartmentDialogFragment newInstance(
            String resourceType,
            String selectedDate,
            String selectedTime,
            String selectedRoomType,
            OnDepartmentSelectedListener listener
    ) {
        DepartmentDialogFragment fragment = new DepartmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("selectedDate", selectedDate);
        args.putString("selectedTime", selectedTime);
        args.putString("selectedRoomType", selectedRoomType);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_department, container, false);

        Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);

        // Set up spinner (gunakan daftar departemen dari resources)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.departments_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);

        buttonContinue.setOnClickListener(v -> {
            String selectedDepartment = spinnerDepartment.getSelectedItem().toString();
            if (listener != null) {
                listener.onDepartmentSelected(selectedDepartment);
            }
            dismiss();
        });

        return view;
    }
}



package com.app.ticbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DescriptionDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(String description);
    }

    private OnDescriptionEnteredListener listener;

    public static DescriptionDialogFragment newInstance(
            String resourceType,
            String selectedDate,
            String selectedTime,
            String selectedRoomType,
            String selectedDepartment,
            OnDescriptionEnteredListener listener
    ) {
        DescriptionDialogFragment fragment = new DescriptionDialogFragment();
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("selectedDate", selectedDate);
        args.putString("selectedTime", selectedTime);
        args.putString("selectedRoomType", selectedRoomType);
        args.putString("selectedDepartment", selectedDepartment);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_description, container, false);

        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(v -> {
            String description = editTextDescription.getText().toString();
            if (listener != null) {
                listener.onDescriptionEntered(description);
            }
            dismiss();
        });

        return view;
    }
}




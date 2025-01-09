package com.app.ticbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class DescriptionDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(String destination, String description);
    }

    private OnDescriptionEnteredListener listener;
    String resourceType = "";

    public static DescriptionDialogFragment newInstance(
            String resourceType,
            String selectedDate,
            String selectedTime,
            int selectedRoomType,
            int selectedDepartment,
            OnDescriptionEnteredListener listener
    ) {
        DescriptionDialogFragment fragment = new DescriptionDialogFragment();
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("selectedDate", selectedDate);
        args.putString("selectedTime", selectedTime);
        args.putInt("selectedRoomType", selectedRoomType);
        args.putInt("selectedDepartment", selectedDepartment);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_description, container, false);

        resourceType = "";
        Bundle mArgs = getArguments();
        resourceType = mArgs.getString("resourceType", "");

        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        EditText editTextDestination = view.findViewById(R.id.editTextDestination);

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);
        TextInputLayout tilDestination = view.findViewById(R.id.tilDestination);
        TextInputLayout tilDetail = view.findViewById(R.id.tilDetail);
        ConstraintLayout clBottom = view.findViewById(R.id.clBottom);

        if (resourceType.equals("Vehicle")) {
            tilDestination.setVisibility(View.VISIBLE);
            clBottom.setVisibility(View.VISIBLE);
        }

        buttonContinue.setOnClickListener(v -> {
            String description = editTextDescription.getText().toString();
            String destination = editTextDestination.getText().toString();

            if (listener != null) {
                listener.onDescriptionEntered(destination, description);
            }
            dismiss();
        });

        imgClose.setOnClickListener(v->{
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




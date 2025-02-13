package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionExeDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(String description);
    }

    private OnDescriptionEnteredListener listener;

    public static DescriptionExeDialogFragment newInstance(
            OnDescriptionEnteredListener listener
    ) {
        DescriptionExeDialogFragment fragment = new DescriptionExeDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_description_exe, container, false);

        EditText editTextDescription = view.findViewById(R.id.editTextDescription);

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        buttonContinue.setOnClickListener(v -> {
            String description = editTextDescription.getText().toString();

            if (description.isEmpty()) {

            } else {
                if (listener != null) {
                    listener.onDescriptionEntered(description);
                }
                dismiss();
            }

        });

        imgClose.setOnClickListener(v-> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}




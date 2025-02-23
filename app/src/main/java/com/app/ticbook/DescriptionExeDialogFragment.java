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
        void onDescriptionEntered(String selectPurpose);
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

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        Spinner spinnerPurpose = view.findViewById(R.id.spinnerPurpose);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getPurpose().enqueue(new Callback<PurposeResponse>() {
            @Override
            public void onResponse(Call<PurposeResponse> call, Response<PurposeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> listString = new ArrayList<>();

//                    listString.add("Hili Purpose");
                    for (Result data: response.body().getResults()){
                        listString.add(data.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listString);
                    spinnerPurpose.setAdapter(adapter); // this will set list of values to spinner

                } else {
                    Log.e("TAG", "Error fetching vehicles: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PurposeResponse> call, Throwable t) {
                Log.e("TAG", "Failure fetching vehicles: " + t.getMessage());
            }
        });


        buttonContinue.setOnClickListener(v -> {
            String selectPurpose = spinnerPurpose.getSelectedItem().toString();

            if (listener != null) {
                listener.onDescriptionEntered(selectPurpose);
            }
            dismiss();
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




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

public class DescriptionDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(String destination, String description, long id);
    }

    private OnDescriptionEnteredListener listener;
    String resourceType = "";
    List<Result>purposes;


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

        List<String> listString = new ArrayList<>();
        purposes = new ArrayList<>();



        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        EditText editTextDestination = view.findViewById(R.id.editTextDestination);

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);
        TextInputLayout tilDestination = view.findViewById(R.id.tilDestination);
        TextInputLayout tilDetail = view.findViewById(R.id.tilDetail);
        ConstraintLayout clBottom = view.findViewById(R.id.clBottom);
        Spinner spinnerBottom = view.findViewById(R.id.spinnerRoomType);

        if (resourceType.equals("Vehicle")) {
            tilDestination.setVisibility(View.VISIBLE);
            clBottom.setVisibility(View.VISIBLE);
        }

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);


        apiService.getPurpose().enqueue(new Callback<PurposeResponse>() {
            @Override
            public void onResponse(Call<PurposeResponse> call, Response<PurposeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    purposes = response.body().getResults();
                    for (Result data: response.body().getResults()){
                        listString.add(data.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listString);
                    spinnerBottom.setAdapter(adapter); // this will set list of values to spinner

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
            String description = editTextDescription.getText().toString();
            String destination = editTextDestination.getText().toString();
            String selectedType = spinnerBottom.getSelectedItem().toString();

            long id = 0;

            for (Result data: purposes){
                if (data.getName().equals(selectedType)){
                    id = data.getID();
                    break;
                }
            }

            if (listener != null) {
                listener.onDescriptionEntered(destination, description,id);
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




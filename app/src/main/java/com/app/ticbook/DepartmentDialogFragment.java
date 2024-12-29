package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentDialogFragment extends DialogFragment {

    public interface OnDepartmentSelectedListener {
        void onDepartmentSelected(int selectedIdDepartment);
    }

    private OnDepartmentSelectedListener listener;
    private List<String> listDepartmentString = new ArrayList<>();
    private List<Departement> listDepartment = new ArrayList<>();

    public static DepartmentDialogFragment newInstance(
            String resourceType,
            String selectedDate,
            String selectedTime,
            int selectedRoomType,
            OnDepartmentSelectedListener listener
    ) {
        DepartmentDialogFragment fragment = new DepartmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("selectedDate", selectedDate);
        args.putString("selectedTime", selectedTime);
        args.putInt("selectedRoomType", selectedRoomType);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_department, container, false);

        listDepartmentString = new ArrayList<>();
        listDepartment = new ArrayList<>();

        Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        // Set up spinner (gunakan daftar departemen dari resources)
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                requireContext(),
//                R.array.departments_array,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDepartment.setAdapter(adapter);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getDepartements().enqueue(new Callback<DepartementResponse>() {
            @Override
            public void onResponse(Call<DepartementResponse> call, Response<DepartementResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listDepartment = new ArrayList<>(response.body().getResults());
                    for (Departement data: listDepartment){
                        listDepartmentString.add(data.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listDepartmentString);
                    spinnerDepartment.setAdapter(adapter); // this will set list of values to spinner

                } else {
                    Log.e("TAG", "Error fetching departements: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DepartementResponse> call, Throwable t) {
                Log.e("TAG", "Failure fetching departements: " + t.getMessage());
            }
        });

        buttonContinue.setOnClickListener(v -> {
            String selectedDepartment = spinnerDepartment.getSelectedItem().toString();
            
            int selectedIdDepartment = 0;
            for (Departement data: listDepartment){
                if (data.getName().equals(selectedDepartment)){
                    selectedIdDepartment = data.getId();
                    break;
                }
            }
            
            if (listener != null) {
                listener.onDepartmentSelected(selectedIdDepartment);
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



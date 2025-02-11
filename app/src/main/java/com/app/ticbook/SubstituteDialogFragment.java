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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.puskal.multiselectspinner.MultiSelectSpinnerView;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubstituteDialogFragment extends DialogFragment {

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(List<Integer> integers);
    }

    private OnDescriptionEnteredListener listener;
    private List<String> listString = new ArrayList<>();
    private List<SubstituteResult> list = new ArrayList<>();
    private List<Integer> listInt = new ArrayList<>();

    public static SubstituteDialogFragment newInstance(
            OnDescriptionEnteredListener listener
    ) {
        SubstituteDialogFragment fragment = new SubstituteDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_substitute_exe, container, false);

        listString = new ArrayList<>();
        list = new ArrayList<>();
        listInt = new ArrayList<>();

        MultiSelectSpinnerView multiSelectSpinnerView = view.findViewById(R.id.multiSelectSpinner);

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        String auth = "Token " + token;

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getSubstituteExecutives(auth).enqueue(new Callback<SubstituteExecutivesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubstituteExecutivesResponse> call, @NonNull Response<SubstituteExecutivesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = new ArrayList<>(response.body().getResults());
                    for (SubstituteResult data: list){
                        listString.add(data.getFirstName() + " " + data.getLastName());
                    }

                    multiSelectSpinnerView.buildCheckedSpinner((ArrayList<String>) listString, (integers, s) -> {
                        listInt.clear();
                        for(int i : integers) {
                            listInt.add(Math.toIntExact(list.get(i).getID()));
                        }
                        return null;
                    });

                } else {
                    Log.e("TAG", "Error fetching departements: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubstituteExecutivesResponse> call, @NonNull Throwable t) {
                Log.e("TAG", "Failure fetching departements: " + t.getMessage());
            }
        });


        buttonContinue.setOnClickListener(v -> {

            if (listener != null) {
                for(int i : listInt) {
                    Log.d("2504", String.valueOf(i));
                }
                listener.onDescriptionEntered(listInt);
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




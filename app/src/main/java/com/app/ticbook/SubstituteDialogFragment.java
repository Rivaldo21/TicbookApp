package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.ticbook.databinding.DialogSubstituteExeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubstituteDialogFragment extends DialogFragment implements SpinnerAdapter.Selected {

    @Override
    public void onSelected(List<Integer> data, List<String> s) {
        listInt.clear();
        listInt.addAll(data);

        binding.spinner.selectedTxt.setText(String.join(", ", s));
    }

    public interface OnDescriptionEnteredListener {
        void onDescriptionEntered(List<Integer> integers);
    }

    private OnDescriptionEnteredListener listener;
    private List<SpinnerData> listString = new ArrayList<>();
    private List<SubstituteResult> list = new ArrayList<>();
    private List<Integer> listInt = new ArrayList<>();
    boolean isExpand = false;
    private DialogSubstituteExeBinding binding;

    public static SubstituteDialogFragment newInstance(
            OnDescriptionEnteredListener listener
    ) {
        SubstituteDialogFragment fragment = new SubstituteDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listString = new ArrayList<>();
        list = new ArrayList<>();
        listInt = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSubstituteExeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        String auth = "Token " + token;

        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        binding.spinner.constraintLayout.setOnClickListener(v -> {
            if (isExpand) {
                binding.spinner.constraintLayout.setBackgroundResource(R.drawable.bg_spinner);
                binding.spinner.expand.setVisibility(View.GONE);
                binding.spinner.icon.setImageResource(R.drawable.arrow_down_ic);
                isExpand = false;
            } else {
                binding.spinner.constraintLayout.setBackgroundResource(R.drawable.bg_spinner2);
                binding.spinner.expand.setVisibility(View.VISIBLE);
                binding.spinner.icon.setImageResource(R.drawable.arrow_up_ic_white);

                isExpand = true;
            }
        });

        binding.spinner.valueRv.setLayoutManager(new LinearLayoutManager(requireContext()));

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getSubstituteExecutives(auth).enqueue(new Callback<SubstituteExecutivesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubstituteExecutivesResponse> call, @NonNull Response<SubstituteExecutivesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = new ArrayList<>(response.body().getResults());
                    for (SubstituteResult data: list){
                        listString.add(new SpinnerData(data.getFirstName() + " " + data.getLastName(), data.getID(), false));
                    }

                    binding.spinner.valueRv.setAdapter(new SpinnerAdapter(listString, SubstituteDialogFragment.this));

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
                listener.onDescriptionEntered(listInt);
            }
            dismiss();
        });

        imgClose.setOnClickListener(v-> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}




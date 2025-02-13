package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ticbook.response.BookingResponse;
import com.app.ticbook.response.ExecutiveResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBookingFragment extends Fragment {

    BookingListAdapter bookingListAdapter;
    private ExeAdapter exeAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_booking, container, false);

        // Konfigura status bar kor default ba DashboardFragment
        requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor, null));
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_VISIBLE
        );


        ImageView imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            Fragment mFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
        });

        // Inisializa RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rv_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inisializa Adapter ho Lista Mamuk
        bookingListAdapter = new BookingListAdapter(new ArrayList<>());
        exeAdapter = new ExeAdapter(new ArrayList<>());


        if (getArguments() != null) {
            String check = getArguments().getString("check");
            Log.d("2504", check);
            if (Objects.equals(check, "exe")) {
                Log.d("2504", "ee");
                recyclerView.setAdapter(exeAdapter);
                fetchAllExe(1, new ArrayList<>());
            } else {
                recyclerView.setAdapter(bookingListAdapter);
                fetchAllBookings(1, new ArrayList<>());
            }
        }

        // Fetch data


        // Inflasaun layout ba fragment
        return view;
    }

    private void fetchAllBookings(int page, List<BookingResponse.BookingResult> allBookings) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getBookings(page).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookingResponse> call, @NonNull Response<BookingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBookings.addAll(response.body().getResults());

                    if (response.body().getNext() != null) {
                        fetchAllBookings(page + 1, allBookings);
                    } else {
                        bookingListAdapter.addList(allBookings);
                    }
                } else {
                    Log.e("TAG", "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingResponse> call, @NonNull Throwable t) {
                Log.e("TAG", "Failure fetching bookings: " + t.getMessage());
            }
        });
    }

    private void fetchAllExe(int page, List<ResultExecutive> allBookings) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getExecutive(page).enqueue(new Callback<ExecutiveResponse>() {
            @Override
            public void onResponse(@NonNull Call<ExecutiveResponse> call, @NonNull Response<ExecutiveResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBookings.addAll(response.body().getResults());

                    if (response.body().getNext() != null) {
                        fetchAllExe(page + 1, allBookings);
                    } else {
                        exeAdapter.addList(allBookings);
                    }
                } else {
                    //Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ExecutiveResponse> call, @NonNull Throwable t) {
                //Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideBottomBar(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).hideBottomBar(false);
    }
}


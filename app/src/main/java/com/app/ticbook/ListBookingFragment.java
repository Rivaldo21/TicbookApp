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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBookingFragment extends Fragment {

    BookingListAdapter bookingListAdapter;

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
        recyclerView.setAdapter(bookingListAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());

        // Inflasaun layout ba fragment
        return view;
    }

    private void fetchAllBookings(int page, List<Booking> allBookings) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getBookings(page).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBookings.addAll(response.body().getResults());

                    if (response.body().getNext() != null) {
                        fetchAllBookings(page + 1, allBookings);
                    } else {
                        bookingListAdapter.setBookings(allBookings);
                    }
                } else {
                    Log.e("TAG", "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e("TAG", "Failure fetching bookings: " + t.getMessage());
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


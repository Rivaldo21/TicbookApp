package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private BookingListAdapter bookingAdapter;
    private static final String TAG = "DashboardFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Konfigura status bar kor default ba DashboardFragment
        requireActivity().getWindow().setStatusBarColor(
            getResources().getColor(R.color.statusBarColor, null)
        );
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
             View.SYSTEM_UI_FLAG_VISIBLE
        );

        // Inisializa RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBookings);
        LinearLayout roomL = view.findViewById(R.id.room_l);
        LinearLayout karetaL = view.findViewById(R.id.kareta_l);
        LinearLayout executiveL = view.findViewById(R.id.executive_l);
        View view1 = view.findViewById(R.id.view);
        View view2 = view.findViewById(R.id.view2);
        View view3 = view.findViewById(R.id.view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        roomL.setOnClickListener(v -> {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            fetchAllBookings(1, new ArrayList<>());
        });

        karetaL.setOnClickListener(v -> {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.GONE);
            fetchAllBookings(1, new ArrayList<>());
        });

        executiveL.setOnClickListener(v -> {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.VISIBLE);

        });

        // Inisializa Adapter ho Lista Mamuk
        bookingAdapter = new BookingListAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookingAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());

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
                        bookingAdapter.setBookings(allBookings);
                    }
                } else {
                    Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
    }


}

package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ticbook.databinding.FragmentDashboardBinding;
import com.app.ticbook.response.BookingResponse;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private BookingListAdapter bookingAdapter;
    private static final String TAG = "DashboardFragment";
    FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        // Konfigura status bar kor default ba DashboardFragment
        requireActivity().getWindow().setStatusBarColor(
            getResources().getColor(R.color.statusBarColor, null)
        );
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
             View.SYSTEM_UI_FLAG_VISIBLE
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerViewBookings.setLayoutManager(new LinearLayoutManager(requireContext()));

        bookingAdapter = new BookingListAdapter(new ArrayList<>());
        binding.recyclerViewBookings.setAdapter(bookingAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());
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
                        bookingAdapter.addList(allBookings);
                    }
                } else {
                    Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
    }


}

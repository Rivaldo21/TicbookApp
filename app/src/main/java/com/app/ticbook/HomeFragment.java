package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends Fragment {

    private BookingAdapter bookingAdapter;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Konfigurasi Status Bar Transparan
        setupTransparentStatusBar();

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inisialisasi Adapter dengan List Kosong
        bookingAdapter = new BookingAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookingAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());

        // OnClickListener untuk Room
        view.findViewById(R.id.iconRoom).setOnClickListener(v -> openBookingDialog("Room"));

        // OnClickListener untuk Vehicle
        view.findViewById(R.id.iconVehicle).setOnClickListener(v -> openBookingDialog("Vehicle"));

        return view;
    }

    private void setupTransparentStatusBar() {
        requireActivity().getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void openBookingDialog(String resourceType) {
        CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
            // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
            showTimePickerDialog(resourceType, selectedDate);
        });
        datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
    }

    private void showTimePickerDialog(String resourceType, String selectedDate) {
        TimePickerDialogFragment timePickerDialog = TimePickerDialogFragment.newInstance(resourceType, selectedDate, selectedTime -> {
            // Setelah memilih waktu, tampilkan dialog untuk memilih tipe ruangan/kendaraan
            showRoomTypeDialog(resourceType, selectedDate, selectedTime);
        });
        timePickerDialog.show(getParentFragmentManager(), "TimePickerDialog");
    }

    private void showRoomTypeDialog(String resourceType, String selectedDate, String selectedTime) {
        RoomTypeDialogFragment roomTypeDialog = RoomTypeDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType -> {
            // Setelah memilih tipe ruangan/kendaraan, tampilkan dialog untuk memilih departemen
            showDepartmentDialog(resourceType, selectedDate, selectedTime, selectedRoomType);
        });
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showDepartmentDialog(String resourceType, String selectedDate, String selectedTime, String selectedRoomType) {
        DepartmentDialogFragment departmentDialog = DepartmentDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment -> {
            // Setelah memilih departemen, tampilkan dialog untuk memasukkan deskripsi
            showDescriptionDialog(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment);
        });
        departmentDialog.show(getParentFragmentManager(), "DepartmentDialog");
    }

    private void showDescriptionDialog(String resourceType, String selectedDate, String selectedTime, String selectedRoomType, String selectedDepartment) {
        DescriptionDialogFragment descriptionDialog = DescriptionDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment, description -> {
            // Setelah memasukkan deskripsi, proses data final
            addBooking(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment, description);
        });
        descriptionDialog.show(getParentFragmentManager(), "DescriptionDialog");
    }


    private void addBooking(String resourceType, String date, String time, String roomType, String department, String description) {
        Toast.makeText(requireContext(), "Booking berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        // Tambahkan logika penyimpanan atau API call di sini
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

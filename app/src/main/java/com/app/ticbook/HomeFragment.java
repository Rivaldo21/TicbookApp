package com.app.ticbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private BookingListAdapter bookingAdapter;
    private View view;

    private static final String TAG = "HomeFragment";
    private SessionManager sessionManager;

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textGreeting = view.findViewById(R.id.textGreeting);
        String greetingMessage = getGreetingMessage();
        textGreeting.setText(greetingMessage);

        // Initialize SessionManager
        sessionManager = new SessionManager(requireContext());

        // Set up the username in the TextView
        String username = sessionManager.getUsername(); // Assuming getUsername() fetches the logged-in user's name
        TextView textSubGreeting = view.findViewById(R.id.textSubGreeting);
        if (username != null && !username.isEmpty()) {
            textSubGreeting.setText(username);
        } else {
            textSubGreeting.setText("Guest"); // Fallback if username is not available
        }

        // Konfigurasi Status Bar Transparan
        setupTransparentStatusBar();

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inisialisasi Adapter dengan List Kosong
        bookingAdapter = new BookingListAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookingAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());

        // OnClickListener untuk Room
        view.findViewById(R.id.iconRoom).setOnClickListener(v -> openBookingDialog("Room"));

        // OnClickListener untuk Vehicle
        view.findViewById(R.id.iconVehicle).setOnClickListener(v -> openBookingDialog("Vehicle"));

        view.findViewById(R.id.iconExecutive).setOnClickListener(v -> openBookingDialog("Executive"));

        return view;
    }

    private void setupTransparentStatusBar() {
        requireActivity().getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void openBookingDialog(String resourceType) {
        selectDateStart = "";
        selectedTimeStart = "";

        selectDateEnd = "";
        selectedTimeEnd = "";

        CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
            // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
            showTimePickerDialog(resourceType, selectedDate);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putBoolean("isStartTime", true);
        datePickerDialog.setArguments(args);
        datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
    }

    String selectDateStart, selectedTimeStart = "";
    String selectDateEnd, selectedTimeEnd = "";

    private void showTimePickerDialog(String resourceType, String selectedDate) {
        TimePickerDialogFragment timePickerDialog = TimePickerDialogFragment.newInstance(resourceType, selectedDate, selectedTime -> {
            // Setelah memilih waktu, tampilkan dialog untuk memilih tipe ruangan/kendaraan
//            showRoomTypeDialog(resourceType, selectedDate, selectedTime);
            selectDateStart = selectedDate;
            selectedTimeStart = selectedTime;

            openBookingDialogEnd(resourceType);

        });
        timePickerDialog.show(getParentFragmentManager(), "TimePickerDialog");
    }

    private void openBookingDialogEnd(String resourceType) {
        CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
            // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
            showTimePickerDialogEnd(resourceType, selectedDate);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putBoolean("isStartTime", false);
        args.putString("timeStart", selectDateStart);
        datePickerDialog.setArguments(args);
        datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
    }

    private void showTimePickerDialogEnd(String resourceType, String selectedDate) {
        TimePickerDialogFragment timePickerDialog = TimePickerDialogFragment.newInstance(resourceType, selectedDate, selectedTime -> {
            // Setelah memilih waktu, tampilkan dialog untuk memilih tipe ruangan/kendaraan
            selectDateEnd = selectedDate;
            selectedTimeEnd = selectedTime;

            showRoomTypeDialog(resourceType, selectedDate, selectedTime);
        });
        timePickerDialog.show(getParentFragmentManager(), "TimePickerDialog");
    }

    private void showRoomTypeDialog(String resourceType, String selectedDate, String selectedTime) {
        RoomTypeDialogFragment roomTypeDialog = RoomTypeDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType -> {
            // Setelah memilih tipe ruangan/kendaraan, tampilkan dialog untuk memilih departemen
            showDepartmentDialog(resourceType, selectedDate, selectedTime, selectedRoomType);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("dateStart", selectDateStart + " " + selectedTimeStart);
        args.putString("dateEnd", selectDateEnd + " " + selectedTimeEnd);

        roomTypeDialog.setArguments(args);
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showDepartmentDialog(String resourceType, String selectedDate, String selectedTime, int selectedRoomType) {
        DepartmentDialogFragment departmentDialog = DepartmentDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment -> {
            // Setelah memilih departemen, tampilkan dialog untuk memasukkan deskripsi
            showDescriptionDialog(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment);
        });
        departmentDialog.show(getParentFragmentManager(), "DepartmentDialog");
    }

    private void showDescriptionDialog(String resourceType, String selectedDate, String selectedTime, int selectedRoomType, int selectedDepartment) {
        DescriptionDialogFragment descriptionDialog = DescriptionDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment, (destination, description, id) -> {
            // Setelah memasukkan deskripsi, proses data final
            addBooking(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment, destination, description, id);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        descriptionDialog.setArguments(args);
        descriptionDialog.show(getParentFragmentManager(), "DescriptionDialog");
    }

    private String getGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Dili"));

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour >= 5 && hour < 12) {
            return "Bom Dia!";
        } else if ((hour == 18 && minute < 50) || (hour >= 12 && hour < 18)) {
            return "Boa Tarde!";
        } else {
            return "Boa Noite!";
        }
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

    private void addBooking(String resourceType, String date, String time, int roomOrVehicleType, int department, String destination,String description, long id) {

        // Tambahkan logika penyimpanan atau API call di sini
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setResourceType(resourceType);
        if (resourceType.equals("Room")){
            bookingRequest.setRoom(roomOrVehicleType);
        } else {
            bookingRequest.setVehicle(roomOrVehicleType);
        }

        bookingRequest.setDepartement(department);
        bookingRequest.setRequesterName("");

//        yyyy-MM-dd'T'HH:mm:ss.fffffff'Z'
        String stTime = "", endTime = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat dateFormatNew = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            Date dateStart = dateFormat.parse(selectDateStart + " " + selectedTimeStart);
            Date dateEnd = dateFormat.parse(selectDateEnd + " " + selectedTimeEnd);

            stTime = dateFormatNew.format(dateStart);
            endTime = dateFormatNew.format(dateEnd);

//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
//            Date dateEnd = dateFormat.parse(time);

//            Calendar c = Calendar.getInstance();
//            c.setTime(dates);
            //30 for add 30 minutes or -30 to minus 30 minutes
//            c.add(Calendar.MINUTE, 120);
//            Date timeMinus30 = c.getTime();
//            endTime = dateFormatNew.format(timeMinus30);

//            endTime = dateFormatNew.format(dates);

        } catch (ParseException e) {
        }

        bookingRequest.setStartTime(stTime);
        bookingRequest.setEndTime(endTime);
        bookingRequest.setDestinationAddress(destination);
        bookingRequest.setDescription(description);

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("resource_type", resourceType);

            if (resourceType.equals("Room")) {
                jsonBody.put("room", roomOrVehicleType);
                jsonBody.put("vehicle", null);

                jsonBody.put("destination_address", null);
            }

            if (resourceType.equals("Executive")) {
                showDepartmentDialog(resourceType, "", "", 0);
            }

            if (resourceType.equals("Vehicle")) {
                jsonBody.put("room", null);
                jsonBody.put("vehicle", roomOrVehicleType);

                if (destination.isEmpty()){
                    destination = "-";
                }
                jsonBody.put("destination_address", destination);
            }

            if (description.isEmpty()){
                description = "-";
            }
            jsonBody.put("purpose", id);
            jsonBody.put("description", description);

            jsonBody.put("departement", department);
            jsonBody.put("requester_name", "Duvia");
            jsonBody.put("start_time", stTime);
            jsonBody.put("end_time", endTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        String auth = "Token " + token;

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonBody));
        Log.d("2504", jsonBody.toString());
        apiService.createBooking(auth, body).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                } else {
                    Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
        Toast.makeText(requireContext(), "Booking aumenta ona ho susesu!", Toast.LENGTH_SHORT).show();

        Fragment mFragment = new ListBookingFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).addToBackStack(HomeFragment.TAG).commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).hideBottomBar(false);
    }
}

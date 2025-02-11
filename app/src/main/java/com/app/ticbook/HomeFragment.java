package com.app.ticbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private BookingListAdapter bookingAdapter;
    private ExeAdapter exeAdapter;

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Konfigurasi Status Bar Transparan
        setupTransparentStatusBar();

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBookings);
        RecyclerView exeRv = view.findViewById(R.id.recyclerViewExe);
        LinearLayout roomL = view.findViewById(R.id.room_l);
        LinearLayout karetaL = view.findViewById(R.id.kareta_l);
        LinearLayout executiveL = view.findViewById(R.id.executive_l);
        View view1 = view.findViewById(R.id.view);
        View view2 = view.findViewById(R.id.view2);
        View view3 = view.findViewById(R.id.view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        exeRv.setLayoutManager(new LinearLayoutManager(requireContext()));


        roomL.setOnClickListener(v -> {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            exeRv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            fetchAllBookings(1, new ArrayList<>());
        });

        karetaL.setOnClickListener(v -> {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.GONE);
            exeRv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            fetchAllBookings(1, new ArrayList<>());
        });

        executiveL.setOnClickListener(v -> {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.VISIBLE);
            exeRv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            fetchAllExe(1, new ArrayList<>());
        });

        TextView name = view.findViewById(R.id.textGreeting);

        SessionManager sessionManager = new SessionManager(requireContext());
        name.setText("Halo " + sessionManager.getUser().getUsername());

        if (sessionManager.getUser().getUserID() == 111305) {
            sessionManager.sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        // Inisialisasi Adapter dengan List Kosong
        bookingAdapter = new BookingListAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookingAdapter);

        exeAdapter = new ExeAdapter(new ArrayList<>());
        exeRv.setAdapter(exeAdapter);

        // Fetch data
        fetchAllBookings(1, new ArrayList<>());

        // OnClickListener untuk Room
        view.findViewById(R.id.iconRoom).setOnClickListener(v -> openBookingDialog("Room"));

        // OnClickListener untuk Vehicle
        view.findViewById(R.id.iconVehicle).setOnClickListener(v -> openBookingDialog("Vehicle"));

        view.findViewById(R.id.cardExe).setOnClickListener(v -> openBookingDialog("exe"));

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

        if (Objects.equals(resourceType, "exe")) {
            DescriptionExeDialogFragment descDialog = DescriptionExeDialogFragment.newInstance( desc -> {
                CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
                    // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
                    showTimePickerDialog(resourceType, selectedDate, desc);
                });
                Bundle args = new Bundle();
                args.putString("resourceType", resourceType);
                args.putString("desc", desc);
                args.putBoolean("isStartTime", true);
                datePickerDialog.setArguments(args);
                datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
            });
            descDialog.show(getParentFragmentManager(), "DatePickerDialog");
        } else {
            CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
                // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
                showTimePickerDialog(resourceType, selectedDate, "");
            });
            Bundle args = new Bundle();
            args.putString("resourceType", resourceType);
            args.putBoolean("isStartTime", true);
            datePickerDialog.setArguments(args);
            datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
        }
    }

    String selectDateStart, selectedTimeStart = "";
    String selectDateEnd, selectedTimeEnd = "";

    private void showTimePickerDialog(String resourceType, String selectedDate, String desc) {
        TimePickerDialogFragment timePickerDialog = TimePickerDialogFragment.newInstance(resourceType, selectedDate, selectedTime -> {
            // Setelah memilih waktu, tampilkan dialog untuk memilih tipe ruangan/kendaraan
//            showRoomTypeDialog(resourceType, selectedDate, selectedTime);
            selectDateStart = selectedDate;
            selectedTimeStart = selectedTime;

            openBookingDialogEnd(resourceType,desc);

        });
        timePickerDialog.show(getParentFragmentManager(), "TimePickerDialog");
    }

    private void openBookingDialogEnd(String resourceType, String desc) {
        CalendarDialogFragment datePickerDialog = CalendarDialogFragment.newInstance(resourceType, selectedDate -> {
            // Setelah memilih tanggal, tampilkan dialog untuk memilih waktu
            showTimePickerDialogEnd(resourceType, selectedDate, desc);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putBoolean("isStartTime", false);
        args.putString("timeStart", selectDateStart);
        datePickerDialog.setArguments(args);
        datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
    }

    private void showTimePickerDialogEnd(String resourceType, String selectedDate, String desc) {
        TimePickerDialogFragment timePickerDialog = TimePickerDialogFragment.newInstance(resourceType, selectedDate, selectedTime -> {
            // Setelah memilih waktu, tampilkan dialog untuk memilih tipe ruangan/kendaraan
            selectDateEnd = selectedDate;
            selectedTimeEnd = selectedTime;

            showRoomTypeDialog(resourceType, selectedDate, selectedTime, desc);
        });
        timePickerDialog.show(getParentFragmentManager(), "TimePickerDialog");
    }

    private void showRoomTypeDialog(String resourceType, String selectedDate, String selectedTime, String desc) {
        RoomTypeDialogFragment roomTypeDialog = RoomTypeDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType -> {
            // Setelah memilih tipe ruangan/kendaraan, tampilkan dialog untuk memilih departemen
            if (Objects.equals(resourceType, "exe")) {
                showRoomTypeDialog2("Vehicle", selectedDate, selectedTime, desc, selectedRoomType);
            } else {
                showDepartmentDialog(resourceType, selectedDate, selectedTime, selectedRoomType);
            }
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("dateStart", selectDateStart + " " + selectedTimeStart);
        args.putString("dateEnd", selectDateEnd + " " + selectedTimeEnd);

        roomTypeDialog.setArguments(args);
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showRoomTypeDialog2(String resourceType, String selectedDate, String selectedTime, String desc, int selectedRoomTypes) {
        RoomTypeDialogFragment roomTypeDialog = RoomTypeDialogFragment.newInstance(resourceType, selectedDate, selectedTime, selectedRoomType -> {
            // Setelah memilih tipe ruangan/kendaraan, tampilkan dialog untuk memilih departemen
            showLoc("exe", selectedDate,selectedTime,selectedRoomTypes, desc, selectedRoomType);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        args.putString("dateStart", selectDateStart + " " + selectedTimeStart);
        args.putString("dateEnd", selectDateEnd + " " + selectedTimeEnd);

        roomTypeDialog.setArguments(args);
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showSubstitute(String resourceType, String selectedDate, String selectedTime, int selectedRoomType, String desc, int selectedVehicleType, String loc, String obs) {
        SubstituteDialogFragment roomTypeDialog = SubstituteDialogFragment.newInstance( (s) -> {
            showParticipants(resourceType, selectedDate, selectedTime, selectedRoomType, desc, selectedVehicleType,loc,obs,s);
        });
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showParticipants(String resourceType, String selectedDate, String selectedTime, int selectedRoomType,
                                  String desc, int selectedVehicleType,
                                  String loc, String obs, List<Integer> integers) {
        ParticipantsDialogFragment roomTypeDialog = ParticipantsDialogFragment.newInstance( (s) -> {
            addExeBooking(resourceType, selectedDate, selectedTime, selectedRoomType, desc, selectedVehicleType,loc,obs, integers,s);
        });
        roomTypeDialog.show(getParentFragmentManager(), "RoomTypeDialog");
    }

    private void showLoc(String resourceType, String selectedDate, String selectedTime, int selectedRoomType, String desc, int selectedVehicleType) {
        LocNoteDialogFragment departmentDialog = LocNoteDialogFragment.newInstance((loc, obs) -> {
            showSubstitute(resourceType, selectedDate, selectedTime, selectedRoomType, desc, selectedVehicleType,loc,obs);
        });
        departmentDialog.show(getParentFragmentManager(), "DepartmentDialog");
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
            addBooking(resourceType, selectedDate, selectedTime, selectedRoomType, selectedDepartment, destination, description,id);
        });
        Bundle args = new Bundle();
        args.putString("resourceType", resourceType);
        descriptionDialog.setArguments(args);
        descriptionDialog.show(getParentFragmentManager(), "DescriptionDialog");
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

    private void addExeBooking(String resourceType, String selectedDate, String selectedTime, int selectedRoomType,
                               String desc, int selectedVehicleType,
                               String loc, String obs, List<Integer> integers, List<Integer> subs) {

        // Tambahkan logika penyimpanan atau API call di sini
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        String auth = "Token " + token;

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

        JSONObject jsonBody = new JSONObject();
        try {
            JSONArray p = new JSONArray();
            JSONArray s = new JSONArray();

            for(int i : integers) {
                p.put(i);
            }

            for (int i: subs) {
                s.put(i);
            }

            jsonBody.put("description", desc);
            jsonBody.put("requester_name", sessionManager.getUser().getUserID());
            jsonBody.put("location", loc);
            jsonBody.put("participants", p);
            jsonBody.put("room", selectedRoomType);
            jsonBody.put("vehicle", selectedVehicleType);
            jsonBody.put("substitute_executive", s);
            jsonBody.put("start_time", stTime);
            jsonBody.put("end_time", endTime);
            jsonBody.put("status", "Pending");
            jsonBody.put("obs", obs);
            jsonBody.put("purpose", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonBody));
        apiService.createExecutive(auth, body).enqueue(new Callback<ExecutiveResponse>() {
            @Override
            public void onResponse(@NonNull Call<ExecutiveResponse> call, @NonNull Response<ExecutiveResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                } else {
                    Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ExecutiveResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
        Toast.makeText(requireContext(), "Booking berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

        Fragment mFragment = new ListBookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("check", resourceType);
        mFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).addToBackStack(HomeFragment.TAG).commit();
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
        bookingRequest.setTravelDescription(description);

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("resource_type", resourceType);

            if (resourceType.equals("Room")) {
                jsonBody.put("room", roomOrVehicleType);
                jsonBody.put("vehicle", null);

                jsonBody.put("destination_address", null);
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
            jsonBody.put("travel_description", description);

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
        Toast.makeText(requireContext(), "Booking berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

        Fragment mFragment = new ListBookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("check", resourceType);
        mFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).addToBackStack(HomeFragment.TAG).commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).hideBottomBar(false);
    }

    private void fetchAllExe(int page, List<ResultExecutive> allBookings) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getExecutive(page).enqueue(new Callback<ExecutiveResponse>() {
            @Override
            public void onResponse(Call<ExecutiveResponse> call, Response<ExecutiveResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBookings.addAll(response.body().getResults());

                    if (response.body().getNext() != null) {
                        fetchAllExe(page + 1, allBookings);
                    } else {
                        exeAdapter.setBookings(allBookings);
                    }
                } else {
                    Log.e(TAG, "Error fetching bookings: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ExecutiveResponse> call, Throwable t) {
                Log.e(TAG, "Failure fetching bookings: " + t.getMessage());
            }
        });
    }
}

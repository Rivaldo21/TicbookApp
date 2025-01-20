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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomTypeDialogFragment extends DialogFragment {

    public interface OnRoomTypeSelectedListener {
        void onRoomTypeSelected(int selectIdRoomOrVehicle);
    }

    private OnRoomTypeSelectedListener listener;
    private List<String> listString = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();
    String resourceType = "", dateStart = "" , dateEnd = "";

    public static RoomTypeDialogFragment newInstance(String resourceType, String selectedDate, String selectedTime, OnRoomTypeSelectedListener listener) {
        RoomTypeDialogFragment fragment = new RoomTypeDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_room_type, container, false);

        resourceType = "";
        Bundle mArgs = getArguments();
        resourceType = mArgs.getString("resourceType", "");
        dateStart = mArgs.getString("dateStart", "");
        dateEnd = mArgs.getString("dateEnd", "");

        Spinner spinnerRoomType = view.findViewById(R.id.spinnerRoomType);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        TextView tvTitleRoom = view.findViewById(R.id.tvTitleRoom);
        TextView tvTitleSpinner = view.findViewById(R.id.tvTitleSpinner);

        TextView tvDateStart = view.findViewById(R.id.tvDateStart);
        TextView tvTimeStart = view.findViewById(R.id.tvTimeStart);

        TextView tvDateEnd = view.findViewById(R.id.tvDateEnd);
        TextView tvTimeEnd = view.findViewById(R.id.tvTimeEnd);

        if (!dateStart.isEmpty()){
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try {
                Date dateStartDate = dateFormatNew.parse(dateStart);
                Date dateEndDate = dateFormatNew.parse(dateEnd);

                dateStart = dayFormat.format(dateStartDate);
                tvDateStart.setText(dateStart);

                String timeStart = timeFormat.format(dateStartDate);
                tvTimeStart.setText(timeStart);

                dateEnd = dayFormat.format(dateEndDate);
                tvDateEnd.setText(dateEnd);

                String timeEnd = timeFormat.format(dateEndDate);
                tvTimeEnd.setText(timeEnd);

            } catch (Exception e){

            }
        }

        if (resourceType.equals("Vehicle")){
            tvTitleRoom.setText("Hili tipu kareta?");
            tvTitleSpinner.setText("Hili kareta");
        }

        listString = new ArrayList<>();
        roomList = new ArrayList<>();
        vehicleList = new ArrayList<>();

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        if (resourceType.equals("Vehicle")) {
            apiService.getVehicles().enqueue(new Callback<VehicleResponse>() {
                @Override
                public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        vehicleList = new ArrayList<>(response.body().getResults());
                        for (Vehicle data: vehicleList){
                            listString.add(data.getName());
                        }

                        VehicleSpinnerAdapter adapter = new VehicleSpinnerAdapter(requireContext(), (ArrayList<Vehicle>) vehicleList);
                        spinnerRoomType.setAdapter(adapter); // this will set list of values to spinner

                    } else {
                        Log.e("TAG", "Error fetching vehicles: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<VehicleResponse> call, Throwable t) {
                    Log.e("TAG", "Failure fetching vehicles: " + t.getMessage());
                }
            });

        } else {
            apiService.getRooms().enqueue(new Callback<RoomResponse>() {
                @Override
                public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        roomList = new ArrayList<>(response.body().getResults());
                        for (Room data: roomList){
                            listString.add(data.getName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listString);
                        spinnerRoomType.setAdapter(adapter); // this will set list of values to spinner

                    } else {
                        Log.e("TAG", "Error fetching rooms: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<RoomResponse> call, Throwable t) {
                    Log.e("TAG", "Failure fetching rooms: " + t.getMessage());
                }
            });

        }

        buttonContinue.setOnClickListener(v -> {
            String selectedType;
            if (resourceType.equals("Vehicle")) {
                selectedType = ((Vehicle) spinnerRoomType.getSelectedItem()).getName();
            } else {
                selectedType = spinnerRoomType.getSelectedItem().toString();
            }

            int idSelect = 0;

            if (resourceType.equals("Vehicle")){
                for (Vehicle data: vehicleList){
                    if (data.getName().equals(selectedType)){
                        idSelect = data.getId();
                        break;
                    }
                }
            } else {
                for (Room data: roomList){
                    if (data.getName().equals(selectedType)){
                        idSelect = data.getId();
                        break;
                    }
                }
            }


            if (listener != null) {
                Log.d("2504", String.valueOf(idSelect));
                listener.onRoomTypeSelected(idSelect);
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






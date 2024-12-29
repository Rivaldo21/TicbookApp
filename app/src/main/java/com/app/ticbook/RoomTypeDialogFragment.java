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

import java.util.ArrayList;
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
    String resourceType = "";

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

        Spinner spinnerRoomType = view.findViewById(R.id.spinnerRoomType);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        TextView tvTitleRoom = view.findViewById(R.id.tvTitleRoom);
        TextView tvTitleSpinner = view.findViewById(R.id.tvTitleSpinner);

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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listString);
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
            String selectedType = spinnerRoomType.getSelectedItem().toString();

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






package com.app.ticbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RoomTypeDialogFragment extends DialogFragment {

    public interface OnRoomTypeSelectedListener {
        void onRoomTypeSelected(String roomType);
    }

    private OnRoomTypeSelectedListener listener;

    public static RoomTypeDialogFragment newInstance(String resourceType, String selectedDate, String selectedTime, OnRoomTypeSelectedListener listener) {
        RoomTypeDialogFragment fragment = new RoomTypeDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_room_type, container, false);

        Spinner spinnerRoomType = view.findViewById(R.id.spinnerRoomType);
        Button buttonContinue = view.findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(v -> {
            String selectedRoomType = spinnerRoomType.getSelectedItem().toString();
            if (listener != null) {
                listener.onRoomTypeSelected(selectedRoomType);
            }
            dismiss();
        });

        return view;
    }
}






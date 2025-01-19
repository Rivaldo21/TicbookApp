package com.app.ticbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VehicleSpinnerAdapter extends ArrayAdapter<Vehicle> {
    public VehicleSpinnerAdapter(@NonNull Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.titles);
        TextView subtitle = convertView.findViewById(R.id.subtitle);
        Vehicle currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            title.setText(currentItem.getName() + "(" + currentItem.getDriver_name() + ")");
            subtitle.setText(currentItem.getCapacity() + " Passengers");
        }
        return convertView;
    }
}

package com.app.ticbook;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ticbook.databinding.ItemListBookingBinding;
import com.app.ticbook.response.BookingResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    List<BookingResponse.BookingResult> bookingList;
    int check = 0;
    public BookingListAdapter(List<BookingResponse.BookingResult> bookingList) {
        this.bookingList = bookingList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addList(List<BookingResponse.BookingResult> list) {
        bookingList.clear();
        bookingList.addAll(list);
        Log.d("2504", String.valueOf(bookingList.size()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBookingBinding binding = ItemListBookingBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingResponse.BookingResult booking = bookingList.get(position);

        holder.binding.date2Txt.setText(Util.dateFormat(booking.getFormattedStartTime()));
        holder.binding.liveTxt.setText(Util.timeFormat(booking.getFormattedStartTime()));

        holder.binding.nameTxt.setText(booking.getPurposeDetails().getName());
        holder.binding.statusTxt.setText(booking.getStatus());

        if (booking.getRoomDetails() == null) {
            holder.binding.roomTxt.setText(booking.getVehicleDetails().getName());
        } else {
            holder.binding.roomTxt.setText(booking.getRoomDetails().getName());
        }

        if (check == 0) {
            holder.binding.iconi.setImageResource(R.drawable.arrow_down_icc);
            holder.binding.expandLayout.setVisibility(View.GONE);
            holder.binding.collapseLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.iconi.setImageResource(R.drawable.arrow_up_ic);
            holder.binding.expandLayout.setVisibility(View.VISIBLE);
            holder.binding.collapseLayout.setVisibility(View.GONE);
        }


        holder.binding.departmentTxt.setText(booking.getDepartementDetails().getName());
        holder.binding.requesterNameTxt.setText(String.valueOf(booking.getRequesterName()));
        holder.binding.dateTxt.setText(Util.dateFormat(booking.getFormattedStartTime()));
        holder.binding.timeTxt.setText(Util.timeFormat(booking.getFormattedStartTime()) + "-" + Util.timeFormat(booking.getFormattedEndTime()));
        holder.binding.descTxt.setText(booking.getDescription());

        holder.binding.iconi.setOnClickListener(v -> {
            if (check == 0) {
                holder.binding.iconi.setImageResource(R.drawable.arrow_up_ic);
                holder.binding.expandLayout.setVisibility(View.VISIBLE);
                holder.binding.collapseLayout.setVisibility(View.GONE);
                check--;
            } else {
                holder.binding.iconi.setImageResource(R.drawable.arrow_down_icc);
                holder.binding.expandLayout.setVisibility(View.GONE);
                holder.binding.collapseLayout.setVisibility(View.VISIBLE);
                check++;

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemListBookingBinding binding;
        public ViewHolder(ItemListBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}

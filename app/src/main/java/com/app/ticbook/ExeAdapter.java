package com.app.ticbook;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ticbook.databinding.ItemListBookingBinding;
import com.app.ticbook.response.BookingResponse;

import java.util.List;

public class ExeAdapter extends RecyclerView.Adapter<ExeAdapter.ViewHolder> {

    List<ResultExecutive> bookingList;
    int check = 0;
    public ExeAdapter(List<ResultExecutive> bookingList) {
        this.bookingList = bookingList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addList(List<ResultExecutive> list) {
        bookingList.addAll(list);
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
        ResultExecutive booking = bookingList.get(position);

        holder.binding.date2Txt.setText(Util.dateFormat(booking.getFormattedStartTime()));
        holder.binding.liveTxt.setText(Util.timeFormat(booking.getFormattedStartTime()));

        holder.binding.nameTxt.setText(booking.getDescription());
        holder.binding.statusTxt.setText(booking.getStatus());

        holder.binding.roomTxt.setText("-");
        holder.binding.departmentTxt.setText("-");
        holder.binding.requesterNameTxt.setText("-");
        holder.binding.dateTxt.setText(Util.dateFormat(booking.getFormattedStartTime()));
        holder.binding.timeTxt.setText(Util.timeFormat(booking.getFormattedStartTime()) + "-" + Util.timeFormat(booking.getFormattedEndTime()));
        holder.binding.descTxt.setText(booking.getDescription());

        if (check == 0) {
            holder.binding.iconi.setImageResource(R.drawable.arrow_down_icc);
            holder.binding.expandLayout.setVisibility(View.GONE);
            holder.binding.collapseLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.iconi.setImageResource(R.drawable.arrow_up_ic);
            holder.binding.expandLayout.setVisibility(View.VISIBLE);
            holder.binding.collapseLayout.setVisibility(View.GONE);
        }

        holder.binding.iconi.setOnClickListener(v -> {
            if (check == 0) {
                holder.binding.iconi.setImageResource(R.drawable.arrow_up_ic);
                holder.binding.expandLayout.setVisibility(View.VISIBLE);
                holder.binding.collapseLayout.setVisibility(View.GONE);
                check++;
            } else {
                holder.binding.iconi.setImageResource(R.drawable.arrow_down_icc);
                holder.binding.expandLayout.setVisibility(View.GONE);
                holder.binding.collapseLayout.setVisibility(View.VISIBLE);

                check--;
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

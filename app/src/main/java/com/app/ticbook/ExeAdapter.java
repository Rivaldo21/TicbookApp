package com.app.ticbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExeAdapter extends RecyclerView.Adapter<ExeAdapter.BookingViewHolder> {

    private List<ResultExecutive> bookingList;

    public ExeAdapter(List<ResultExecutive> bookingList) {
        this.bookingList = bookingList != null ? bookingList : new ArrayList<>();
    }

    public void setBookings(List<ResultExecutive> bookings) {
        this.bookingList = bookings != null ? bookings : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        ResultExecutive booking = bookingList.get(position);

        // Set title
        holder.textMeetingTitle.setText(
               booking.getDescription()
        );

        holder.textExpMeetingTitle.setText(
                booking.getDescription()
        );

        holder.textMeetingTitleExp.setText(
                booking.getDescription()
        );

        // Toggle expandable content
        holder.lyIcon.setOnClickListener(v -> {
            boolean isExpanded = holder.expandableContent.getVisibility() == View.VISIBLE;
            holder.expandableContent.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            holder.clItem.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            holder.chevronIcon.setImageResource(isExpanded ? R.drawable.ic_chevron_down : R.drawable.ic_chevron_up);
        });

        // Set requester name
//        holder.textRequesterName.setText("Requester: " + booking.getRequester_name());

        // Department Details
//        holder.textDepartment.setText(
//                "Department: " +
//                        (booking.getDepartement_details() != null ? booking.getDepartement_details().getName() : "Unknown")
//        );

        // Set status
//        holder.textMeetingStatus.setText("Status: " + booking.getStatus());

        // Set date and time

        String dateOnly = "", time = "", timeEnd = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat dateFormatDateOnly = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatTimeOnly = new SimpleDateFormat("hh:mm");

        try {
            Date date = dateFormat.parse(booking.getFormattedStartTime());
            Date date2 = dateFormat.parse(booking.getFormattedEndTime());

            dateOnly = dateFormatDateOnly.format(date);
            time = dateFormatTimeOnly.format(date);

            timeEnd = dateFormatTimeOnly.format(date2);

        } catch (ParseException e) {
        }

        holder.textMeetingDate.setText(dateOnly);
        holder.textMeetingTime.setText(time);

        holder.textMeetingDateExp.setText(dateOnly);
        holder.textMeetingTimeExp.setText(time + " - " + timeEnd);

        holder.tvDescExp.setText(booking.getObs());

//        holder.textEndTime.setText("End: " + booking.getFormatted_end_time());

        // Set destination (only for Vehicle)
//        if ("Vehicle".equalsIgnoreCase(booking.getResource_type())) {
//            holder.textDestination.setVisibility(View.VISIBLE);
//            holder.textDestination.setText("Destination: " + booking.getDestination_address());
//            holder.textDriver.setVisibility(View.VISIBLE);
//            holder.textDriver.setText("Driver: " + booking.getVehicle_details().getDriver_name());
//        } else {
//            holder.textDestination.setVisibility(View.GONE);
//            holder.textDriver.setVisibility(View.GONE);
//        }

        // Set description
//        holder.textMeetingDescription.setText("Description: " + booking.getTravel_description());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView textMeetingTitle, textExpMeetingTitle, textMeetingTitleExp, textMeetingTimeExp, textMeetingDateExp, tvDescExp, textRequesterName, textDepartment, textMeetingDate, textMeetingTime, textEndTime, textDestination, textMeetingDescription, textDriver, textMeetingStatus;
        ImageView chevronIcon;
        View expandableContent;
        LinearLayout clItem, lyIcon;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textMeetingTitle = itemView.findViewById(R.id.textMeetingTitle);
            textExpMeetingTitle = itemView.findViewById(R.id.textExpMeetingTitle);
            textMeetingTitleExp = itemView.findViewById(R.id.tvTitleExp);

            textMeetingDate = itemView.findViewById(R.id.textMeetingDate);
            textMeetingTime = itemView.findViewById(R.id.textMeetingTime);

            textMeetingTimeExp = itemView.findViewById(R.id.textExpMeetingTime);
            textMeetingDateExp = itemView.findViewById(R.id.textExpMeetingDate);

            tvDescExp = itemView.findViewById(R.id.tvDescExp);

//            textRequesterName = itemView.findViewById(R.id.textRequesterName);
//            textDepartment = itemView.findViewById(R.id.textDepartment);

//            textEndTime = itemView.findViewById(R.id.textEndTime);
//            textDestination = itemView.findViewById(R.id.textDestination);
//            textMeetingDescription = itemView.findViewById(R.id.textMeetingDescription);
//            textDriver = itemView.findViewById(R.id.textDriver);
//            textMeetingStatus = itemView.findViewById(R.id.textMeetingStatus);
            chevronIcon = itemView.findViewById(R.id.chevronIcon);
            expandableContent = itemView.findViewById(R.id.expandableContent);
            clItem = itemView.findViewById(R.id.clItem);
            lyIcon = itemView.findViewById(R.id.lyIcon);
        }
    }
}

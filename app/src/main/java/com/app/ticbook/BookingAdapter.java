package com.app.ticbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList != null ? bookingList : new ArrayList<>();
    }

    public void setBookings(List<Booking> bookings) {
        this.bookingList = bookings != null ? bookings : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        // Set title
        holder.textMeetingTitle.setText(
                booking.getResource_type().equalsIgnoreCase("Room") ?
                        "Room Booking: " + (booking.getRoom_details() != null ? booking.getRoom_details().getName() : "Unknown Room") :
                        "Vehicle Booking: " + (booking.getVehicle_details() != null ? booking.getVehicle_details().getName() : "Unknown Vehicle")
        );

        // Toggle expandable content
        holder.chevronIcon.setOnClickListener(v -> {
            boolean isExpanded = holder.expandableContent.getVisibility() == View.VISIBLE;
            holder.expandableContent.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            holder.chevronIcon.setImageResource(isExpanded ? R.drawable.ic_chevron_down : R.drawable.ic_chevron_up);
        });

        // Set requester name
        holder.textRequesterName.setText("Requester: " + booking.getRequester_name());

        // Department Details
        holder.textDepartment.setText(
                "Department: " +
                        (booking.getDepartement_details() != null ? booking.getDepartement_details().getName() : "Unknown")
        );

        // Set status
        holder.textMeetingStatus.setText("Status: " + booking.getStatus());

        // Set date and time
        holder.textMeetingDate.setText("Date: " + booking.getFormatted_start_time());
        holder.textMeetingTime.setText("Start: " + booking.getFormatted_start_time());
        holder.textEndTime.setText("End: " + booking.getFormatted_end_time());

        // Set destination (only for Vehicle)
        if ("Vehicle".equalsIgnoreCase(booking.getResource_type())) {
            holder.textDestination.setVisibility(View.VISIBLE);
            holder.textDestination.setText("Destination: " + booking.getDestination_address());
            holder.textDriver.setVisibility(View.VISIBLE);
            holder.textDriver.setText("Driver: " + booking.getVehicle_details().getDriver_name());
        } else {
            holder.textDestination.setVisibility(View.GONE);
            holder.textDriver.setVisibility(View.GONE);
        }

        // Set description
        holder.textMeetingDescription.setText("Description: " + booking.getTravel_description());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView textMeetingTitle, textRequesterName, textDepartment, textMeetingDate, textMeetingTime, textEndTime, textDestination, textMeetingDescription, textDriver, textMeetingStatus;
        ImageView chevronIcon;
        View expandableContent;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textMeetingTitle = itemView.findViewById(R.id.textMeetingTitle);
            textRequesterName = itemView.findViewById(R.id.textRequesterName);
            textDepartment = itemView.findViewById(R.id.textDepartment);
            textMeetingDate = itemView.findViewById(R.id.textMeetingDate);
            textMeetingTime = itemView.findViewById(R.id.textMeetingTime);
            textEndTime = itemView.findViewById(R.id.textEndTime);
            textDestination = itemView.findViewById(R.id.textDestination);
            textMeetingDescription = itemView.findViewById(R.id.textMeetingDescription);
            textDriver = itemView.findViewById(R.id.textDriver);
            textMeetingStatus = itemView.findViewById(R.id.textMeetingStatus);
            chevronIcon = itemView.findViewById(R.id.chevronIcon);
            expandableContent = itemView.findViewById(R.id.expandableContent);
        }
    }
}

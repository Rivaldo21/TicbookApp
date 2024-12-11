package com.app.ticbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/rooms/")
    Call<List<Room>> getRooms();

    @GET("api/vehicles/")
    Call<List<Vehicle>> getVehicles();

    @GET("api/bookings/")
    Call<BookingResponse> getBookings(@Query("page") int page);

    @POST("bookings/")
    Call<BookingResponse> createBooking(@Body BookingRequest bookingRequest);


}

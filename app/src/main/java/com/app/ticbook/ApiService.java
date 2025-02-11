package com.app.ticbook;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/rooms/")
    Call<RoomResponse> getRooms();

    @GET("api/vehicles/")
    Call<VehicleResponse> getVehicles();

    @GET("api/purpose/")
    Call<PurposeResponse> getPurpose();

    @GET("api/bookings/")
    Call<BookingResponse> getBookings(@Query("page") int page);

    @GET("api/executive-meetings/")
    Call<ExecutiveResponse> getExecutive(@Query("page") int page);

    @POST("api/executive-meetings/")
    Call<ExecutiveResponse> createExecutive(@Header("authorization") String auth,@Body RequestBody body);

    @POST("api/bookings/")
    Call<BookingResponse> createBooking(@Header("authorization") String auth, @Body RequestBody params);

    @GET("api/substitute-executives/")

    Call<SubstituteExecutivesResponse> getSubstituteExecutives(@Header("authorization") String auth);

    @GET("api/participants-users/")
    Call<SubstituteExecutivesResponse> getParticipantUsers(@Header("authorization") String auth);

    @GET("api/participants-departments/")
    Call<OtherResponse> getParticipantDep();

    @GET("api/departements/")
    Call<DepartementResponse> getDepartements();

    @POST("api/login/")
    Call<LoginResponse> login(@Body RequestBody params);

    @GET("api/user/")
    Call<UserResponse> user(@Header("authorization") String auth);
}

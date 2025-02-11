package com.app.ticbook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultExecutive {
    private long id;
    private String description;
    private String location;
    private List<Participant> participants;
    private Long room;
    private Object vehicle;

    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("formatted_start_time")
    private String formattedStartTime;
    @SerializedName("formatted_end_time")
    private String formattedEndTime;
    private String status;
    private String obs;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }



    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public List<Participant> getParticipants() { return participants; }
    public void setParticipants(List<Participant> value) { this.participants = value; }

    public Long getRoom() { return room; }
    public void setRoom(Long value) { this.room = value; }

    public Object getVehicle() { return vehicle; }
    public void setVehicle(Object value) { this.vehicle = value; }



    public String getStartTime() { return startTime; }
    public void setStartTime(String value) { this.startTime = value; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String value) { this.endTime = value; }

    public String getFormattedStartTime() { return formattedStartTime; }
    public void setFormattedStartTime(String value) { this.formattedStartTime = value; }

    public String getFormattedEndTime() { return formattedEndTime; }
    public void setFormattedEndTime(String value) { this.formattedEndTime = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getObs() { return obs; }
    public void setObs(String value) { this.obs = value; }
}

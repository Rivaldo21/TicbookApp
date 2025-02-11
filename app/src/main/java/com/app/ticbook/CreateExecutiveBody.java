package com.app.ticbook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateExecutiveBody {
    String description;
    @SerializedName("requester_name")
    String requesterName;
    String location;
    List<Integer> participants;
    int room;
    int vehicle;
    @SerializedName("substitute_executive")
    List<Integer> substituteExecutive;
    @SerializedName("start_time")
    String startTime;
    @SerializedName("end_time")
    String endTime;
    String status;
    String obs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public List<Integer> getSubstituteExecutive() {
        return substituteExecutive;
    }

    public void setSubstituteExecutive(List<Integer> substituteExecutive) {
        this.substituteExecutive = substituteExecutive;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}

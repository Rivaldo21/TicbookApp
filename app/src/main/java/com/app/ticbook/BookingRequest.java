package com.app.ticbook;

public class BookingRequest {
    private String resource_type;
    private int room;
    private int vehicle;
    private int departement;
    private String requester_name;
    private String start_time;
    private String end_time;
    private String destination_address;
    private String travel_description;

    // Add constructor, getters, and setters

    public String getResourceType() {
        return resource_type;
    }

    public void setResourceType(String resourceType) {
        this.resource_type = resourceType;
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

    public int getDepartement() {
        return departement;
    }

    public void setDepartement(int departement) {
        this.departement = departement;
    }

    public String getRequesterName() {
        return requester_name;
    }

    public void setRequesterName(String requesterName) {
        this.requester_name = requesterName;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String startTime) {
        this.start_time = startTime;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String endTime) {
        this.end_time = endTime;
    }

    public String getDestinationAddress() {
        return destination_address;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destination_address = destinationAddress;
    }

    public String getTravelDescription() {
        return travel_description;
    }

    public void setTravelDescription(String travelDescription) {
        this.travel_description = travelDescription;
    }

    public BookingRequest(){};

    public BookingRequest(String resourceType, int room, int vehicle, int departement, String requesterName, String startTime, String endTime, String destinationAddress, String travelDescription) {
        this.resource_type = resourceType;
        this.room = room;
        this.vehicle = vehicle;
        this.departement = departement;
        this.requester_name = requesterName;
        this.start_time = startTime;
        this.end_time = endTime;
        this.destination_address = destinationAddress;
        this.travel_description = travelDescription;
    }
}

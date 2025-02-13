package com.app.ticbook.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
    private long count;
    private Object next;
    private Object previous;
    private List<BookingResult> results;

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }

    public Object getNext() { return next; }
    public void setNext(Object value) { this.next = value; }

    public Object getPrevious() { return previous; }
    public void setPrevious(Object value) { this.previous = value; }

    public List<BookingResult> getResults() { return results; }
    public void setResults(List<BookingResult> value) { this.results = value; }

    public static class BookingResult {
        private int id;
        @SerializedName("resource_type")
        private String resourceType;
        private int purpose;
        @SerializedName("purpose_details")
        private PurposeDetail purposeDetails;
        private int room;
        @SerializedName("room_details")
        private RoomDetails roomDetails;
        private int vehicle;
        private int departement;
        @SerializedName("departement_details")
        private PurposeDetail departementDetails;
        @SerializedName("requester_name")
        private int requesterName;
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("end_time")
        private String endTime;
        @SerializedName("formatted_start_time")
        private String formattedStartTime;
        @SerializedName("formatted_end_time")
        private String formattedEndTime;
        @SerializedName("destination_address")
        private String destinationAddress;
        private String description;
        private String status;
        @SerializedName("vehicle_details")
        private VehicleDetails vehicleDetails;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public int getPurpose() {
            return purpose;
        }

        public void setPurpose(int purpose) {
            this.purpose = purpose;
        }

        public PurposeDetail getPurposeDetails() {
            return purposeDetails;
        }

        public void setPurposeDetails(PurposeDetail purposeDetails) {
            this.purposeDetails = purposeDetails;
        }

        public int getRoom() {
            return room;
        }

        public void setRoom(int room) {
            this.room = room;
        }

        public RoomDetails getRoomDetails() {
            return roomDetails;
        }

        public void setRoomDetails(RoomDetails roomDetails) {
            this.roomDetails = roomDetails;
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

        public PurposeDetail getDepartementDetails() {
            return departementDetails;
        }

        public void setDepartementDetails(PurposeDetail departementDetails) {
            this.departementDetails = departementDetails;
        }

        public int getRequesterName() {
            return requesterName;
        }

        public void setRequesterName(int requesterName) {
            this.requesterName = requesterName;
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

        public String getFormattedStartTime() {
            return formattedStartTime;
        }

        public void setFormattedStartTime(String formattedStartTime) {
            this.formattedStartTime = formattedStartTime;
        }

        public String getFormattedEndTime() {
            return formattedEndTime;
        }

        public void setFormattedEndTime(String formattedEndTime) {
            this.formattedEndTime = formattedEndTime;
        }

        public String getDestinationAddress() {
            return destinationAddress;
        }

        public void setDestinationAddress(String destinationAddress) {
            this.destinationAddress = destinationAddress;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public VehicleDetails getVehicleDetails() {
            return vehicleDetails;
        }

        public void setVehicleDetails(VehicleDetails vehicleDetails) {
            this.vehicleDetails = vehicleDetails;
        }

        public static class RoomDetails {
            private int id;
            @SerializedName("in_use")
            private boolean inUse;
            private String name;
            private int capacity;
            private String status;

            public int getID() { return id; }
            public void setID(int value) { this.id = value; }

            public boolean getInUse() { return inUse; }
            public void setInUse(boolean value) { this.inUse = value; }

            public String getName() { return name; }
            public void setName(String value) { this.name = value; }

            public int getCapacity() { return capacity; }
            public void setCapacity(int value) { this.capacity = value; }

            public String getStatus() { return status; }
            public void setStatus(String value) { this.status = value; }
        }

        public static class VehicleDetails {
            private int id;
            @SerializedName("in_use")
            private boolean inUse;
            @SerializedName("driver_name")
            private String driverName;
            private String name;
            private String type;
            private int capacity;
            private String status;
            private int driver;

            public int getID() { return id; }
            public void setID(int value) { this.id = value; }

            public boolean getInUse() { return inUse; }
            public void setInUse(boolean value) { this.inUse = value; }

            public String getDriverName() { return driverName; }
            public void setDriverName(String value) { this.driverName = value; }

            public String getName() { return name; }
            public void setName(String value) { this.name = value; }

            public String getType() { return type; }
            public void setType(String value) { this.type = value; }

            public int getCapacity() { return capacity; }
            public void setCapacity(int value) { this.capacity = value; }

            public String getStatus() { return status; }
            public void setStatus(String value) { this.status = value; }

            public long getDriver() { return driver; }
            public void setDriver(int value) { this.driver = value; }
        }

        public static class PurposeDetail {
            private int id;
            private String name;

            public int getID() { return id; }
            public void setID(int value) { this.id = value; }

            public String getName() { return name; }
            public void setName(String value) { this.name = value; }
        }
    }


}








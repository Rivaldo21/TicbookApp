package com.app.ticbook;

public class Booking {
    private int id;
    private String resource_type;
    private String requester_name;
    private String start_time;
    private String end_time;
    private String formatted_start_time;
    private String formatted_end_time;
    private String destination_address;
    private String description;
    private String driver_name;
    private String status;

//    private String description;

    // Additional fields for Room and Vehicle
    private RoomDetails room_details;
    private VehicleDetails vehicle_details;
    private DepartementDetails departement_details;

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public String getRequester_name() {
        return requester_name;
    }

    public String getFormatted_start_time() {
        return formatted_start_time;
    }

    public void setFormatted_start_time(String formatted_start_time) {
        this.formatted_start_time = formatted_start_time;
    }

    public String getFormatted_end_time() {
        return formatted_end_time;
    }

    public void setFormatted_end_time(String formatted_end_time) {
        this.formatted_end_time = formatted_end_time;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public String getTravel_description() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public RoomDetails getRoom_details() {
        return room_details;
    }

    public void setRoom_details(RoomDetails room_details) {
        this.room_details = room_details;
    }

    public VehicleDetails getVehicle_details() {
        return vehicle_details;
    }

    public void setVehicle_details(VehicleDetails vehicle_details) {
        this.vehicle_details = vehicle_details;
    }

    public DepartementDetails getDepartement_details() {
        return departement_details;
    }

    public void setDepartement_details(DepartementDetails departement_details) {
        this.departement_details = departement_details;
    }

    // Nested class for RoomDetails
    public static class RoomDetails {
        private int id;
        private String name;
        private int capacity;
        private String status;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }
    }

    // Nested class for VehicleDetails
    public static class VehicleDetails {
        private int id;
        private String name;
        private String type;
        private int capacity;
        private String status;
        private int driver;
        private String driver_name; // Added field for driver's name

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }

        public int getDriver() {
            return driver;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }
    }

    // Nested class for DepartementDetails
    public static class DepartementDetails {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

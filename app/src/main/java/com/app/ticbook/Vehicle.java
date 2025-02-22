package com.app.ticbook;

public class Vehicle {
    private int id;
    private String name;
    private String type;
    private String driver_name;
    private int capacity;
    private String status;

    public Vehicle(int id, String name, String type, String driver_name, int capacity, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.driver_name = driver_name;
        this.capacity = capacity;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


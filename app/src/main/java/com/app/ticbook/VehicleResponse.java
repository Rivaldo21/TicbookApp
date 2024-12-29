package com.app.ticbook;

import java.util.List;

public class VehicleResponse {
    private String next;
    private String previous;
    private List<Vehicle> results;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Vehicle> getResults() {
        return results;
    }
}

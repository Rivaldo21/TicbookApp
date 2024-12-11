package com.app.ticbook;

import java.util.List;

public class BookingResponse {
    private String next;
    private String previous;
    private List<Booking> results;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Booking> getResults() {
        return results;
    }
}

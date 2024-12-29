package com.app.ticbook;

import java.util.List;

public class RoomResponse {
    private String next;
    private String previous;
    private List<Room> results;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Room> getResults() {
        return results;
    }
}

package com.app.ticbook;

import java.util.List;

public class DepartementResponse {
    private String next;
    private String previous;
    private List<Departement> results;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Departement> getResults() {
        return results;
    }
}

package com.app.ticbook;

import java.util.List;

public class PurposeResponse {
    private long count;
    private Object next;
    private Object previous;
    private List<Result> results;

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }


    public Object getNext() { return next; }

    public void setNext(Object value) { this.next = value; }


    public Object getPrevious() { return previous; }

    public void setPrevious(Object value) { this.previous = value; }


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}



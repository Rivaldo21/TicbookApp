package com.app.ticbook;

import java.util.List;

public class SubstituteExecutivesResponse {
    private long count;
    private Object next;
    private Object previous;
    private List<SubstituteResult> results;

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }

    public Object getNext() { return next; }
    public void setNext(Object value) { this.next = value; }

    public Object getPrevious() { return previous; }
    public void setPrevious(Object value) { this.previous = value; }

    public List<SubstituteResult> getResults() { return results; }
    public void setResults(List<SubstituteResult> value) { this.results = value; }
}


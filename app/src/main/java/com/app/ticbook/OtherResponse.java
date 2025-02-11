package com.app.ticbook;

import java.util.List;

public class OtherResponse {
    private long count;
    private Object next;
    private Object previous;
    private List<OtherResult> results;

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }

    public Object getNext() { return next; }
    public void setNext(Object value) { this.next = value; }

    public Object getPrevious() { return previous; }
    public void setPrevious(Object value) { this.previous = value; }

    public List<OtherResult> getResults() { return results; }
    public void setResults(List<OtherResult> value) { this.results = value; }
}



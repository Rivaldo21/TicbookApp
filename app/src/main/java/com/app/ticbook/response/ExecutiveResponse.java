package com.app.ticbook.response;
import com.app.ticbook.ResultExecutive;

import java.util.List;

public class ExecutiveResponse {
    private long count;
    private Object next;
    private Object previous;
    private List<ResultExecutive> results;

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }

    public Object getNext() { return next; }
    public void setNext(Object value) { this.next = value; }

    public Object getPrevious() { return previous; }
    public void setPrevious(Object value) { this.previous = value; }

    public List<ResultExecutive> getResults() { return results; }
    public void setResults(List<ResultExecutive> value) { this.results = value; }
}


package org.url.edu;

import java.util.List;
import java.util.Map;

public abstract class Arc <V, D> {
    protected V tail;
    protected V head;

    public Arc(V tail, V head){
        this.tail = tail;
        this.head = head;
    }

    public abstract boolean satisfied(Map<V, D> assignment);
}

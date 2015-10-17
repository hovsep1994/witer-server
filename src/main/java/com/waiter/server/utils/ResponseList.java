package com.waiter.server.utils;

import java.util.LinkedList;

/**
 * Created by shahen on 11/24/14.
 */
public class ResponseList<T> extends LinkedList<T> {
    private int total;
    private boolean isTotalValid;

    public int total() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        setTotalValid(true);
    }

    public boolean isTotalValid() {
        return isTotalValid;
    }
    public void setTotalValid(boolean isTotalValid) {
        this.isTotalValid = isTotalValid;
    }
}

package com.waiter.server.api.utility.rating;

/**
 * Created by hovsep on 8/21/16.
 */
public class RoundRating {

    private static final int DEFAULT_PLACES = 1;

    public static double round(double value) {
        return round(value, DEFAULT_PLACES);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

package com.ccreanga.various.airport;

public class AirStrip {

    private boolean regular;

    public AirStrip(boolean regular) {
        this.regular = regular;
    }

    public boolean isRegular() {
        return regular;
    }

    public boolean isLarge() {
        return !regular;
    }

    @Override
    public String toString() {
        return regular ? "regular" : "large";
    }
}

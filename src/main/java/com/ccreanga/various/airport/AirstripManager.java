package com.ccreanga.various.airport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class AirstripManager {

    private AirStrip largeAirstrip = new AirStrip(false);
    private AirStrip regularAirstrip = new AirStrip(true);
    private Semaphore large = new Semaphore(1);
    private Semaphore regular = new Semaphore(1);


    private Map<AirStrip, Semaphore> locks = new HashMap<>();

    {
        locks.put(regularAirstrip, regular);
        locks.put(largeAirstrip, large);
    }

    public AirStrip acquire(Airplane airplane) {
        if (airplane.isRegular()) {
            if (regular.tryAcquire()) {
                return regularAirstrip;
            }
        }
        if (large.tryAcquire()) {
            return largeAirstrip;
        }
        return null;
    }

    public void release(AirStrip airStrip) {
        locks.get(airStrip).release();
    }

}

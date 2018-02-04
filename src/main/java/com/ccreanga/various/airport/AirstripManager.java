package com.ccreanga.various.airport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class AirstripManager {

    AirStrip largeAirstrip = new AirStrip(false);
    AirStrip regularAirstrip = new AirStrip(true);
    Semaphore large = new Semaphore(1);
    Semaphore regular = new Semaphore(1);


    Map<AirStrip,Semaphore> locks = new HashMap();

    {
        locks.put(regularAirstrip,regular);
        locks.put(largeAirstrip,large);
    }

    public AirStrip acquire(Airplane airplane){
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

    public void release(AirStrip airStrip){
        locks.get(airStrip).release();
    }

}

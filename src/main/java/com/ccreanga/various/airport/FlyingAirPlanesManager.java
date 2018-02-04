package com.ccreanga.various.airport;

import java.util.concurrent.PriorityBlockingQueue;

public class FlyingAirPlanesManager {

    private PriorityBlockingQueue<Airplane> regular = new PriorityBlockingQueue<>();
    private PriorityBlockingQueue<Airplane> large = new PriorityBlockingQueue<>();

    public FlyingAirPlanesManager() {
    }

    public void addAirplane(Airplane airplane){
        if (airplane.isRegular())
            regular.add(airplane);
        else
            large.add(airplane);
    }
    public void removeAirplane(Airplane airplane){
        if (airplane.isRegular())
            regular.remove(airplane);
        else
            large.remove(airplane);
    }

    public Airplane pollRegularAirplane(){
        return regular.poll();
    }
    public Airplane pollLargeAirplane(){
        return large.poll();
    }
    public boolean hasRegularAirplanes(){
        return regular.size()>0;
    }
    public boolean hasLargeAirplanes(){
        return large.size()>0;
    }

}

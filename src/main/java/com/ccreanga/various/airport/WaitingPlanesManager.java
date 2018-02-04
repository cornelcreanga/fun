package com.ccreanga.various.airport;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WaitingPlanesManager {

    private ConcurrentLinkedQueue<Airplane> regular = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Airplane> large = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Airplane> regularEmergency = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Airplane> largeEmergency = new ConcurrentLinkedQueue<>();


    public WaitingPlanesManager() {
    }

    public void addAirplane(Airplane airplane) {
        if (airplane.isRegular()) {
            if (airplane.isEmergency())
                regularEmergency.add(airplane);
            else
                regular.add(airplane);
        }else {
            if (airplane.isEmergency())
                largeEmergency.add(airplane);
            else
                large.add(airplane);
        }

    }

    public void removeAirplane(Airplane airplane) {
        if (airplane.isRegular()) {
            if (airplane.isEmergency())
                regularEmergency.remove(airplane);
            else
                regular.remove(airplane);
        }else {
            if (airplane.isEmergency())
                largeEmergency.remove(airplane);
            else
                large.remove(airplane);
        }
    }

    public Airplane pollRegularAirplane() {
        return regular.poll();
    }

    public Airplane pollLargeAirplane() {
        return large.poll();
    }

    public Airplane pollEmergencyRegularAirplane() {
        return regularEmergency.poll();
    }

    public Airplane pollEmergencyLargeAirplane() {
        return largeEmergency.poll();
    }

//    public boolean hasRegularAirplanes(){
//        return regular.size()>0;
//    }
//    public boolean hasLargeAirplanes(){
//        return large.size()>0;
//    }

}

package com.ccreanga.various.airport;

import com.ccreanga.various.airport.messages.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Airplane implements Runnable,Comparable {

    public static final int REGULAR_LANDING_TIME = 5000;
    public static final int LARGE_LANDING_TIME = 7000;
    private String name;
    private boolean regular;
    private boolean emergency;
    private int delay;
    private FlyingAirPlanesManager flyingAirPlanesManager;
    private BlockingQueue<AirplaneMessage> airplaneToControllers;
    private BlockingQueue<ControllerMessage> fromController;
    private volatile boolean done = false;
    private long initiateLandingTimestamp;

    public Airplane(String name, boolean regular,boolean emergency,int delay,FlyingAirPlanesManager flyingAirPlanesManager, BlockingQueue<AirplaneMessage> airplaneToControllers) {
        this.name = name;
        this.regular = regular;
        this.emergency = emergency;
        this.delay = delay;
        this.flyingAirPlanesManager = flyingAirPlanesManager;
        this.airplaneToControllers = airplaneToControllers;
        fromController = new LinkedBlockingQueue<>(100);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay*1000);
            initiateLandingTimestamp = System.currentTimeMillis();
        flyingAirPlanesManager.addAirplane(this);
        airplaneToControllers.put(new ReadyToLandMessage(this));
        } catch (InterruptedException ignore) { }

        while(!done){
            ControllerMessage message = fromController.poll();
            if (message instanceof CircleMessage){
                Controller  controller = ((CircleMessage) message).getController();
                System.out.println(Util.time()+", "+controller.getName()+" -> "+name+", CircleMessage");
            }else if (message instanceof StartLandingMessage){
                Controller  controller = ((StartLandingMessage) message).getController();
                AirStrip  airStrip = ((StartLandingMessage) message).getAirStrip();
                try {
                    System.out.println(Util.time()+", "+controller.getName()+" -> "+name+", StartLandingMessage on "+airStrip);
                    Thread.sleep(regular? REGULAR_LANDING_TIME : LARGE_LANDING_TIME);
                    done = true;
                    airplaneToControllers.put(new LandedMessage(this,airStrip));
                } catch (InterruptedException ignore) { }
            }
        }
    }

    public boolean isRegular() {
        return regular;
    }

    public void putMessage(ControllerMessage airplaneMessage){
        try {
            fromController.put(airplaneMessage);
        } catch (InterruptedException ignore) { }
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        Airplane toCompare = (Airplane)o;
        return (int)(toCompare.initiateLandingTimestamp-initiateLandingTimestamp);
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "name='" + name + '\'' +
                ", regular=" + regular +
                ", emergency=" + emergency +
                ", delay=" + delay +
                '}';
    }
}

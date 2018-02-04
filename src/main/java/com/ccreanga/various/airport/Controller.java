package com.ccreanga.various.airport;

import com.ccreanga.various.airport.messages.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Runnable {

    private String name;
    private FlyingAirPlanesManager flyingAirPlanesManager;
    private BlockingQueue<AirplaneMessage> airplaneToController;

    private AirstripManager airstripManager;
    private ReentrantLock landedLock = new ReentrantLock();
    private boolean stop;

    public Controller(String name, FlyingAirPlanesManager flyingAirPlanesManager, BlockingQueue<AirplaneMessage> airplaneToController, AirstripManager airstripManager) {
        this.name = name;
        this.flyingAirPlanesManager = flyingAirPlanesManager;
        this.airplaneToController = airplaneToController;
        this.airstripManager = airstripManager;
    }

    @Override
    public void run() {
        while(!stop){
            AirplaneMessage airplaneMessage = airplaneToController.poll();
            if (airplaneMessage instanceof ReadyToLandMessage){
                Airplane airplane = ((ReadyToLandMessage) airplaneMessage).getAirplane();
                System.out.println(Util.time()+", "+airplane.getName()+" -> "+name+", ReadyToLandMessage");
                AirStrip airStrip= airstripManager.acquire(airplane);

                if (airStrip==null){
                    airplane.putMessage(new CircleMessage(this));
                }else{
                    airplane.putMessage(new StartLandingMessage(this,airStrip));
                }
            }else if (airplaneMessage instanceof MaydayMessage){

            }else if (airplaneMessage instanceof LandedMessage){

                AirStrip airStrip = ((LandedMessage) airplaneMessage).getAirstrip();
                Airplane landedAirplane = ((LandedMessage) airplaneMessage).getAirplane();
                System.out.println(Util.time()+", "+landedAirplane.getName()+" -> "+name+", LandedMessage");
                landedLock.lock();
                flyingAirPlanesManager.removeAirplane(landedAirplane);

                if (airStrip.isRegular() && (flyingAirPlanesManager.hasRegularAirplanes())){
                    Airplane airplane = flyingAirPlanesManager.pollRegularAirplane();
                    landedLock.unlock();
                    airplane.putMessage(new StartLandingMessage(this,airStrip));
                }else if (airStrip.isLarge()){
                    if (flyingAirPlanesManager.hasLargeAirplanes()){
                        Airplane airplane = flyingAirPlanesManager.pollLargeAirplane();
                        landedLock.unlock();
                        airplane.putMessage(new StartLandingMessage(this,airStrip));
                    }else if (flyingAirPlanesManager.hasRegularAirplanes()){
                        Airplane airplane = flyingAirPlanesManager.pollRegularAirplane();
                        landedLock.unlock();
                        airplane.putMessage(new StartLandingMessage(this,airStrip));
                    }
                }else{
                    landedLock.unlock();
                    airstripManager.release(airStrip);
                }


            }
        }
    }

    public synchronized void stop(){
        stop = true;
    }

    public String getName() {
        return name;
    }
}

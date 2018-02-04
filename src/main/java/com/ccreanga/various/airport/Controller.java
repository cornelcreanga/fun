package com.ccreanga.various.airport;

import com.ccreanga.various.airport.messages.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Runnable {

    private String name;
    private WaitingPlanesManager waitingPlanesManager;
    private BlockingQueue<AirplaneMessage> airplaneToController;

    private AirstripManager airstripManager;
    private boolean stop;

    public Controller(String name,
                      WaitingPlanesManager waitingPlanesManager,
                      BlockingQueue<AirplaneMessage> airplaneToController,
                      AirstripManager airstripManager) {
        this.name = name;
        this.waitingPlanesManager = waitingPlanesManager;
        this.airplaneToController = airplaneToController;
        this.airstripManager = airstripManager;
    }

    @Override
    public void run() {
        while (!stop) {
            AirplaneMessage airplaneMessage = airplaneToController.poll();
            if ((airplaneMessage instanceof ReadyToLandMessage) || (airplaneMessage instanceof MaydayMessage)) {
                boolean mayday = airplaneMessage instanceof MaydayMessage;

                Airplane airplane = airplaneMessage.getAirplane();

                System.out.println(Util.time() + ", " + airplane.getName() + " -> " + name +
                        (mayday?", MaydayMessage":", ReadyToLandMessage"));
                AirStrip airStrip = airstripManager.acquire(airplane);

                if (airStrip == null) {
                    airplane.putMessage(new CircleMessage(this));
                } else {
                    waitingPlanesManager.removeAirplane(airplane);
                    airplane.putMessage(new StartLandingMessage(this, airStrip));
                }


            } else if (airplaneMessage instanceof LandedMessage) {

                AirStrip airStrip = ((LandedMessage) airplaneMessage).getAirstrip();
                Airplane landedAirplane = airplaneMessage.getAirplane();
                System.out.println(Util.time() + ", " + landedAirplane.getName() + " -> " + name + ", LandedMessage");


                Airplane airplane = null;
                if (airStrip.isRegular()) {
                    airplane = waitingPlanesManager.pollEmergencyRegularAirplane();
                    if (airplane == null)
                        airplane = waitingPlanesManager.pollRegularAirplane();
                    if (airplane != null)
                        airplane.putMessage(new StartLandingMessage(this, airStrip));
                } else if (airStrip.isLarge()) {
                    airplane = waitingPlanesManager.pollEmergencyLargeAirplane();
                    if (airplane == null)
                        airplane = waitingPlanesManager.pollEmergencyRegularAirplane();
                    if (airplane == null)
                        airplane = waitingPlanesManager.pollLargeAirplane();
                    if (airplane == null)
                        airplane = waitingPlanesManager.pollRegularAirplane();
                    if (airplane != null)
                        airplane.putMessage(new StartLandingMessage(this, airStrip));
                }

                if (airplane == null) {
//                    System.out.println("release");
                    airstripManager.release(airStrip);
                }

            }
        }
    }

    public synchronized void stopController() {
        stop = true;
    }

    public String getName() {
        return name;
    }
}

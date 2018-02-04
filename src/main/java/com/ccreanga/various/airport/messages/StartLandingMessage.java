package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.AirStrip;
import com.ccreanga.various.airport.Controller;

public class StartLandingMessage implements ControllerMessage {

    Controller controller;
    AirStrip airStrip;

    public StartLandingMessage(Controller controller, AirStrip airStrip) {
        this.controller = controller;
        this.airStrip = airStrip;
    }

    public Controller getController() {
        return controller;
    }

    public AirStrip getAirStrip() {
        return airStrip;
    }

}

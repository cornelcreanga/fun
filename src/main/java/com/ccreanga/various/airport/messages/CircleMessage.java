package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.Controller;

public class CircleMessage implements ControllerMessage {

    Controller controller;

    public CircleMessage(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

}

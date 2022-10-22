package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.Controller;

public class CircleMessage implements ControllerMessage {

    private Controller controller;

    public CircleMessage(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

}

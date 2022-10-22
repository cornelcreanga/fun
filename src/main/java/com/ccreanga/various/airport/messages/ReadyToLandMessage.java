package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.Airplane;

public class ReadyToLandMessage implements AirplaneMessage {

    private Airplane airplane;

    public ReadyToLandMessage(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }

}

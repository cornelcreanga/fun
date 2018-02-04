package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.Airplane;

public class MaydayMessage implements AirplaneMessage {
    private Airplane airplane;

    public MaydayMessage(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }
}

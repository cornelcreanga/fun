package com.ccreanga.various.airport.messages;

import com.ccreanga.various.airport.AirStrip;
import com.ccreanga.various.airport.Airplane;

public class LandedMessage implements AirplaneMessage {
    private Airplane airplane;
    private AirStrip airstrip;

    public LandedMessage(Airplane airplane, AirStrip airstrip) {
        this.airplane = airplane;
        this.airstrip = airstrip;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public AirStrip getAirstrip() {
        return airstrip;
    }

}

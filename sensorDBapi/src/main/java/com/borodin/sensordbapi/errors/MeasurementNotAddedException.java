package com.borodin.sensordbapi.errors;

public class MeasurementNotAddedException extends RuntimeException{

    public MeasurementNotAddedException(String message) {
        super( message );
    }
}

package com.borodin.sensordbapi.errors;

public class SensorNotRegisteredException extends RuntimeException{

    public SensorNotRegisteredException(String message){
        super(message);
    }
}

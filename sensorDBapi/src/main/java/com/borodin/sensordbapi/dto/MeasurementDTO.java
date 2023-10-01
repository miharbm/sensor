package com.borodin.sensordbapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull
    @JsonProperty("Sensor")
    private SensorDTO sensorDTO;

    @DecimalMax( "100.0" )
    @DecimalMin( "-100.0" )
    @Digits( integer = 2, fraction = 1)
    private double temperature;

    private Boolean isRain;

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Boolean getRain() {
        return isRain;
    }

    public void setRain(Boolean rain) {
        isRain = rain;
    }
}

package dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Measurement {

    @JsonProperty("Sensor")
    private Sensor sensor;

    private double temperature;

    private Boolean isRain;

    public Measurement(Sensor sensor, double temperature, Boolean isRain) {
        this.sensor = sensor;
        this.temperature = temperature;
        this.isRain = isRain;
    }

    public Sensor getSensorDTO() {
        return sensor;
    }

    public void setSensorDTO(Sensor sensor) {
        this.sensor = sensor;
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

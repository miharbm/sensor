package com.borodin.sensordbapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_sensor", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "temperature")
    @DecimalMax( "100.0" )
    @DecimalMin( "-100.0" )
    @Digits( integer = 2, fraction = 1)
    private double temperature;

    @Column(name = "is_rain")
    private Boolean isRain;

    @Column(name = "time")
    @Temporal( TemporalType.TIMESTAMP )
    private Date time;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", sensor=" + sensor +
                ", temperature=" + temperature +
                ", isRain=" + isRain +
                ", time=" + time +
                '}';
    }
}

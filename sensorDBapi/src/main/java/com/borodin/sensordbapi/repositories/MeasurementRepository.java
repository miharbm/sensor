package com.borodin.sensordbapi.repositories;

import com.borodin.sensordbapi.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,BigInteger> {

    long countMeasurementByIsRainIsTrue();
}

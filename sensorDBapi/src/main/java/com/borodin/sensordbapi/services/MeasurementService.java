package com.borodin.sensordbapi.services;

import com.borodin.sensordbapi.models.Measurement;
import com.borodin.sensordbapi.models.Sensor;
import com.borodin.sensordbapi.repositories.MeasurementRepository;
import com.borodin.sensordbapi.repositories.SensorRepository;
import com.borodin.sensordbapi.errors.SensorNotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Measurement> findOne(BigInteger id){
        return measurementRepository.findById( id );
    }

    public long countMeasurementByIsRainIsTrue(){
        long a = measurementRepository.countMeasurementByIsRainIsTrue();
        System.out.println(a);
        return a;
    }
    public void save(Measurement measurement){
        measurement.setTime( new Date() );

        Sensor sensor = sensorRepository.findByName( measurement.getSensor().getName() )
                .orElseThrow(()-> new SensorNotRegisteredException( "This sensor is not registered" ));
        measurement.setSensor( sensor );

        measurementRepository.save( measurement );
    }

    public void update(BigInteger id, Measurement measurement){
        measurement.setId( id );
        measurementRepository.save( measurement );
    }

    public void delete(BigInteger id){
        measurementRepository.deleteById( id );
    }
}

package com.borodin.sensordbapi.services;

import com.borodin.sensordbapi.models.Measurement;
import com.borodin.sensordbapi.models.Sensor;
import com.borodin.sensordbapi.repositories.SensorRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sensor> findOne(String name){
        return sensorRepository.findByName( name );
    }

    public void save(Sensor sensor){
        sensorRepository.save( sensor );
    }

    public void update(int id, Sensor updatedSensor){
        updatedSensor.setId( id );
        sensorRepository.save( updatedSensor );
    }
    public void delete(int id){
        sensorRepository.deleteById( id );
    }

    @Transactional(readOnly = true)
    public List<Measurement> getMeasurements(int id){
        Optional<Sensor> sensor = sensorRepository.findById( id );

        if(sensor.isPresent()){
            Hibernate.initialize( sensor.get().getMeasurements() );
            return sensor.get().getMeasurements();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Measurement> getMeasurement(Sensor sensor){
        Hibernate.initialize( sensor.getMeasurements() );
        return sensor.getMeasurements();
    }

}

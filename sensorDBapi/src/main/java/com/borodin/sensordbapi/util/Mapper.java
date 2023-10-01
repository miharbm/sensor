package com.borodin.sensordbapi.util;

import com.borodin.sensordbapi.dto.MeasurementDTO;
import com.borodin.sensordbapi.dto.SensorDTO;
import com.borodin.sensordbapi.models.Measurement;
import com.borodin.sensordbapi.models.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map( sensorDTO, Sensor.class );
    }
    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map( sensor, SensorDTO.class );
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        Measurement measurement = modelMapper.map( measurementDTO, Measurement.class );
        measurement.setSensor( convertToSensor( measurementDTO.getSensorDTO() ) );
        return modelMapper.map( measurementDTO, Measurement.class );
    }
    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        MeasurementDTO measurementDTO = modelMapper.map( measurement, MeasurementDTO.class );

        measurementDTO.setSensorDTO( convertToSensorDTO( measurement.getSensor() ) );
        return measurementDTO;
    }
}

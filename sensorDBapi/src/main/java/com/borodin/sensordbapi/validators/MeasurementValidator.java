package com.borodin.sensordbapi.validators;

import com.borodin.sensordbapi.dto.MeasurementDTO;
import com.borodin.sensordbapi.models.Measurement;
import com.borodin.sensordbapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals( clazz );
    }

    @Override
    public void validate(Object target, Errors errors) {

        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if(sensorService.findOne( measurementDTO.getSensorDTO().getName() ).isEmpty()){
            errors.rejectValue( "name", "", "This sensor is not registered" );
        }

    }
}

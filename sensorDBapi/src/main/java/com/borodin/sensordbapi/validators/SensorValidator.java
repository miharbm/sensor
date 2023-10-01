package com.borodin.sensordbapi.validators;

import com.borodin.sensordbapi.dto.SensorDTO;
import com.borodin.sensordbapi.models.Sensor;
import com.borodin.sensordbapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals( clazz );
    }

    @Override
    public void validate(Object target, Errors errors) {

        SensorDTO sensor = (SensorDTO) target;

        if(sensorService.findOne( sensor.getName() ).isPresent()){
            errors.rejectValue( "name", "", "This sensor name is already in use" );
        }
    }
}

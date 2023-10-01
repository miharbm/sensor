package com.borodin.sensordbapi.controllers;

import com.borodin.sensordbapi.dto.SensorDTO;
import com.borodin.sensordbapi.models.Sensor;
import com.borodin.sensordbapi.services.SensorService;
import com.borodin.sensordbapi.errors.ErrorResponse;
import com.borodin.sensordbapi.util.Mapper;
import com.borodin.sensordbapi.errors.SensorNotRegisteredException;
import com.borodin.sensordbapi.validators.SensorValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final Mapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, Mapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){

        sensorValidator.validate( sensorDTO, bindingResult );
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append( error.getField() )
                        .append( " - " ).append( error.getDefaultMessage() )
                        .append( ";" );
            }

            throw new SensorNotRegisteredException( errorMsg.toString() );
        }

        Sensor sensor = modelMapper.convertToSensor( sensorDTO );
        sensorService.save( sensor );
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotRegisteredException e){
        ErrorResponse response = new ErrorResponse( e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>( response, HttpStatus.NOT_ACCEPTABLE );
    }


}

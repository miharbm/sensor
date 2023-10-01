package com.borodin.sensordbapi.controllers;

import com.borodin.sensordbapi.dto.MeasurementDTO;
import com.borodin.sensordbapi.models.Measurement;
import com.borodin.sensordbapi.services.MeasurementService;
import com.borodin.sensordbapi.errors.ErrorResponse;
import com.borodin.sensordbapi.util.Mapper;
import com.borodin.sensordbapi.errors.MeasurementNotAddedException;
import com.borodin.sensordbapi.validators.MeasurementValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final Mapper modelMapper;


    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator, Mapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
        return measurementService.findAll().stream().map( modelMapper::convertToMeasurementDTO )
                .collect( Collectors.toList() );
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){

        measurementValidator.validate( measurementDTO, bindingResult );
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append( error.getField() )
                        .append( " - " ).append( error.getDefaultMessage() )
                        .append( ";" );
            }

            throw new MeasurementNotAddedException( errorMsg.toString() );
        }

        Measurement measurement = modelMapper.convertToMeasurement( measurementDTO );
        measurementService.save( measurement );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/rainyDaysCount")
    public Map<String,String> rainyDaysCount(){
//        Map<String, String> map = new HashMap<>();
//                map.put( "Number of rainy days", String.valueOf( measurementService.countMeasurementByIsRainIsTrue() ) );
//        return map;
        return Map.of("Number of rainy days", String.valueOf( measurementService.countMeasurementByIsRainIsTrue()));

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotAddedException e){

        ErrorResponse response = new ErrorResponse( e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
    }

}

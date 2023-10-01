package com.borodin.sensordbapi;

import com.borodin.sensordbapi.dto.SensorDTO;
import com.borodin.sensordbapi.models.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SensorDBapiApplication {

    public static void main(String[] args) {
        SpringApplication.run( SensorDBapiApplication.class, args );
    }


    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap( Sensor.class, SensorDTO.class );
        return modelMapper;
    }
}


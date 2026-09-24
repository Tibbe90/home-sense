package iot.temperature.stats.controllers;

import org.springframework.web.bind.annotation.RestController;

import iot.temperature.stats.models.TempHumidity;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("/arduino")
public class ArduinoController {
    
    @PostMapping("/sensor-data")
    public void postTempHumidity(@Valid @RequestBody TempHumidity measurements) {
        arduinoService.saveMeasurement(measurements);
        System.out.println(measurements);
        
    }
    

}

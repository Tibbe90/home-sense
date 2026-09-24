package iot.temperature.stats.controllers;

import org.springframework.web.bind.annotation.RestController;

import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.service.ArduinoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("/arduino")
public class ArduinoController {
    
    private final ArduinoService arduinoService;

    ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }
    
    @PostMapping("/sensor-data")
    public void postTempHumidity(@Valid @RequestBody TempHumidity measurements) {
        arduinoService.saveMeasurement(measurements);
        System.out.println(measurements);
        
    }
    

}

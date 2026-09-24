package iot.temperature.stats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.temperature.stats.models.StatsDTO;
import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.service.FrontendService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController 
@RequestMapping("/frontend")
public class FrontendController {
    
private final FrontendService frontendService;

public FrontendController(FrontendService frontendService) {
    this.frontendService = frontendService;
}

    @GetMapping("/live-data")
    public TempHumidityDTO getLatestData() {
        return frontendService.getLatestData();
    }
    
    @GetMapping("/24stats")
    public StatsDTO get24hStats() {
        return frontendService.get24hStats();
    }
    
    @GetMapping("/all-stats")
    public StatsDTO getAllStats() {
        return frontendService.getAllStats();
    }
    
    //Returns abnormal data, otherwise ok
    @GetMapping("/status")
    public ResponseEntity<String> getStatusDto() {
        return ResponseEntity.ok(frontendService.getStatus());
    }

    @DeleteMapping("/delete-all")
        public ResponseEntity<Void> clearSensorData() {
        frontendService.delete();
        return ResponseEntity.noContent().build();
        }

    @DeleteMapping("/delete/{readings}")
        public ResponseEntity<Void> clearSpecifiedSensorData(@PathVariable int readings) {
        frontendService.delete(readings);
        return ResponseEntity.noContent().build();
        }
    
}

package iot.temperature.stats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.temperature.stats.models.StatsDTO;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.service.FrontendService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/frontend")
public class FrontendController {

    private final FrontendService frontendService;

    public FrontendController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @GetMapping("/live-data")
    public List<TempHumidityDTO> getLatestData() {
        return frontendService.getLatestData();
    }

    @GetMapping("/24stats")
    public List<StatsDTO> get24hStats() {
        return frontendService.get24hReadings();
    }

    @GetMapping("/all-stats")
    public List<StatsDTO> getAllStats() {
        return frontendService.getAllReadings();
    }

    // Returns abnormal data, otherwise ok
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
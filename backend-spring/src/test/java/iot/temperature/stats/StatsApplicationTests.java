package iot.temperature.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import iot.temperature.stats.models.StatsDTO;
import iot.temperature.stats.models.StatsTempHumidityDTO;
import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.service.ArduinoService;
import iot.temperature.stats.service.FrontendService;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class StatsApplicationTests {
	static {
        System.setProperty("arduino.api.key", "arduino-test-key");
		System.setProperty("arduino.frontend.api.key", "frontend-test-key");
	}

	@Autowired
	ArduinoService arduinoService;
	@Autowired
	FrontendService frontendService;

	@BeforeEach
	void cleanEntries() {
		frontendService.delete();
	}

	@Test
	void checkIfMeasurementsAreSaved() {
		String device = "junit";
		TempHumidity measTempHumidity = new TempHumidity(device, 88, 99);
		arduinoService.saveMeasurement(measTempHumidity);
		frontendService.updateDeviceList();

		List<TempHumidityDTO> latestData = frontendService.getLatestData();
		assertFalse(latestData.isEmpty());
	}

	@Test
	void checkIfCurrentTempGetsLatestEntry() {
		String device = "latest";
		TempHumidity measTempHumidity = new TempHumidity(device, 25.6, 52);
		arduinoService.saveMeasurement(measTempHumidity);
		TempHumidity measTempHumidityNew = new TempHumidity(device, 36, 52);
		arduinoService.saveMeasurement(measTempHumidityNew);
		frontendService.updateDeviceList();

		List<TempHumidityDTO> latestData = frontendService.getLatestData();
		TempHumidityDTO latest = latestData.stream()
				.filter(d -> d.getDevice().contains(device))
				.findFirst()
				.orElseThrow();
		assertEquals(36, latest.getTemp());
		assertEquals(52, latest.getHumidity());
	}

	@Test
	void check24hStatsCalculations() {
		String device = "24StatsTest";
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 75, 52));
		frontendService.updateDeviceList();

		List<StatsDTO> stats = frontendService.get24hReadings();
		StatsDTO results = stats.getFirst();
		List<StatsTempHumidityDTO> measurements = results.getMeasurements();
		DoubleSummaryStatistics tempStats = measurements.stream()
                    .mapToDouble(StatsTempHumidityDTO::getTemp)
                    .summaryStatistics();
		assertEquals(25, tempStats.getMin());
		assertEquals(75, tempStats.getMax());
		assertEquals(50, tempStats.getAverage()); 
	}

	@Test
	// Status updates are unimplemented
	void checkIfStatusFlagsOnAbnormalValue() {
		String device = "AbnormalValue";
		arduinoService.saveMeasurement(new TempHumidity(device, 188, 300));
		frontendService.updateDeviceList();
		String message = frontendService.getStatus();
		String expectedMessage = "Measurements from AbnormalValue are unreasonable, please investigate.";
		assertTrue(message.contains(expectedMessage));
	}

	@Test
	void checkIfDeleteDeletes() {
		String device = "delete-me";
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(device, 25, 52));
		frontendService.updateDeviceList();

		frontendService.delete();
		List<TempHumidityDTO> latestData = frontendService.getLatestData();
		assertTrue(latestData.isEmpty());
	}
}

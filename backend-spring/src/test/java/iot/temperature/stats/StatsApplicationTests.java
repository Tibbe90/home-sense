package iot.temperature.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import iot.temperature.stats.models.Stats;
import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.service.ArduinoService;
import iot.temperature.stats.service.FrontendService;

@SpringBootTest
class StatsApplicationTests extends AbstractBaseIntegrationTest {

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
		int id = 1;
		String device = "junit";
		TempHumidity measTempHumidity = new TempHumidity(id, device, 88, 99);
		arduinoService.saveMeasurement(measTempHumidity);

		List<TempHumidityDTO> latestData = frontendService.getLatestData();
		assertFalse(latestData.isEmpty());
	}

	@Test
	void checkIfCurrentTempGetsLatestEntry() {
		String device = "latest";
		int id = 25;
		TempHumidity measTempHumidity = new TempHumidity(id, device, 25.6, 52);
		TempHumidity measTempHumidityNew = new TempHumidity(id, device, 36, 52);
		arduinoService.saveMeasurement(measTempHumidity);
		arduinoService.saveMeasurement(measTempHumidityNew);

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
		int id = 10000;
		arduinoService.saveMeasurement(new TempHumidity(id + 1, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 2, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 3, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 4, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 5, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 6, device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 7, device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 8, device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 9, device, 75, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 10, device, 75, 52));

		Stats stats = frontendService.get24hStats(device);
		assertEquals(25, stats.getMin());
		assertEquals(75, stats.getMax());
		assertEquals(50, stats.getAverage());
	}

	@Test
	void checkIfStatusFlagsOnAbnormalValue() {
		int id = 1203134;
		String device = "AbnormalValue";
		arduinoService.saveMeasurement(new TempHumidity(id, device, 188, 300));
		String message = frontendService.getStatus();
		String expectedMessage = "Measurements from AbnormalValue are unreasonable, please investigate.";
		assertTrue(message.contains(expectedMessage));
	}

	@Test
	void checkIfDeleteDeletes() {
		String device = "delete-me";
		int id = 10000;
		arduinoService.saveMeasurement(new TempHumidity(id + 1, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 2, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 3, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 4, device, 25, 52));
		arduinoService.saveMeasurement(new TempHumidity(id + 5, device, 25, 52));

		frontendService.delete();
		List<TempHumidity> latestData = frontendService.getLatestData();
		assertTrue(latestData.isEmpty());
	}
}

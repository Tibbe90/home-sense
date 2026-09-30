package iot.temperature.stats;
import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(StatsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

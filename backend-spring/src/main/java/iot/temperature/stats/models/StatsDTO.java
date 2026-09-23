package iot.temperature.stats.models;

import java.time.Instant;

public record StatsDTO(
    String deviceId,
    float average,
    float min,
    float max,
    Instant periodStart,
    Instant periodEnd
) {}

export interface TempHumidity {
    "device": string,
    "temp": number,
    "humidity": number
}

export interface TempHumidityStats {
    "device": string,
    "temp": number,
    "humidity": number
    "measuredAt": string
}

export interface TempStatistics {
    "device": string,
    "averageTemp": number,
    "minTemp": number,
    "maxTemp": number,
    "averageHumidity": number,
    "minHumidity": number,
    "maxHumidity": number,
    "periodStart" : Date,
    "periodEnd" : string
}

export interface StatsDTO {
    "stats" : TempStatistics,
    "measurements" : TempHumidityStats[]
}
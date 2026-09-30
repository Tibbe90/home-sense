import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  TimeScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
} from "chart.js";
import "chartjs-adapter-date-fns";
import type { StatsDTO} from "../types/types";

ChartJS.register(
  TimeScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
);

interface LineChartProps {
  stats?: StatsDTO[];
}

function LineChart({ stats }: LineChartProps) {
  const colors = ["blue", "green"];

  const tempData =
    stats?.map((s, i) => ({
      label: s.stats.device,
      borderColor: colors[i],
      backgroundColor: colors[i],
      data: s.measurements.map((m) => ({
        x: new Date(m.measuredAt),
        y: m.temp,
      })),
    })) ?? [];

  const humidityData =
    stats?.map((s, i) => ({
      label: s.stats.device,
      borderColor: colors[i],
      backgroundColor: colors[i],
      data: s.measurements.map((m) => ({
        x: new Date(m.measuredAt),
        y: m.humidity,
      })),
    })) ?? [];

  const options = {
    x: { type: "time" as const, time: { unit: "minute" as const } },
    y: { beginAtZero: false },
  };
  console.log("humidity DATA:::: ", humidityData);

    return (
  <div id="charts">
    <h3>Temperature & Humidity Summary</h3>
    <table>
      <thead>
        <tr>
          <th>Device</th>
          <th>Avg Temp</th>
          <th>Min Temp</th>
          <th>Max Temp</th>
          <th>Avg Humidity</th>
          <th>Min Humidity</th>
          <th>Max Humidity</th>
        </tr>
      </thead>
      <tbody>
        {stats?.map(s => (
          <tr key={s.stats.device}>
            <td>{s.stats.device}</td>
            <td>{s.stats.averageTemp.toFixed(1)}°C</td>
            <td>{s.stats.minTemp.toFixed(1)}°C</td>
            <td>{s.stats.maxTemp.toFixed(1)}°C</td>
            <td>{s.stats.averageHumidity.toFixed(1)}%</td>
            <td>{s.stats.minHumidity.toFixed(1)}%</td>
            <td>{s.stats.maxHumidity.toFixed(1)}%</td>
          </tr>
        ))}
      </tbody>
    </table>

    <h3>Temperature</h3>
    <Line datasetIdKey="label" data={{ datasets: tempData }} options={{ scales: options }} />

    <h3>Humidity</h3>
    <Line datasetIdKey="label" data={{ datasets: humidityData }} options={{ scales: options }} />
  </div>
  );
}

export default LineChart;

import LineChart from "../components/LineChart";
import useGet24hStatistics from "../hooks/useGet24hStatistics";
import type { StatsDTO } from "../types/types";
import CurrentData from "../components/CurrentData";
import useGetStatistics from "../hooks/useGetStatistics";

function Dashboard() {
    const stats24h : StatsDTO[] | void = useGet24hStatistics();
    const allStats : StatsDTO[] | void = useGetStatistics();

    return (
    <div>
      <div className="current-data-box">
        <h2>Latest readings</h2>
        <CurrentData />
      </div>
      <div className="chart-row">
        <div className="chart-panel">
          <h2>24h Statistics</h2>
          <LineChart stats={stats24h} timeUnit="hour" />
        </div>
        <div className="chart-panel">
          <h2>Full Statistics</h2>
          <LineChart stats={allStats}  timeUnit="day"/>
        </div>
      </div>

    </div>
  );
}
export default Dashboard;
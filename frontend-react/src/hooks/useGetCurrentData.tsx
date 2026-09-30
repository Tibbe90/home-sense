import { useEffect, useState } from "react";
import api from "../api";
import type { TempHumidity } from "../types/types";

const updateIntervalMS = 60000;

function useGetCurrentData() {
  const url = "frontend/live-data";
  const [latestReadings, setLatestReadings] = useState<TempHumidity[]>([]);

  useEffect(() => {
    const getLatestReadings = async () => {
      const { data } = await api.get(url);
      setLatestReadings(data);
    };

    getLatestReadings();
    const interval = setInterval(getLatestReadings, updateIntervalMS);
    return () => clearInterval(interval);
  }, []);
  
  return latestReadings;
}

export default useGetCurrentData;

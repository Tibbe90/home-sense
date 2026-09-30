import { useEffect, useState } from "react";
import api from "../api";
import type { StatsDTO } from "../types/types";

function useGetStatistics() {
const url = "frontend/24stats"
const [allStats, setAllStats] = useState<StatsDTO[]>([]);

useEffect(() => {
    const getAllStats = async () => {
      const { data } = await api.get(url);
      setAllStats(data);
    };
    getAllStats();

}, [])

    return allStats; 
}

export default useGetStatistics
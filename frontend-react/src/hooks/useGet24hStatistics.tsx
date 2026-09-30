import { useEffect, useState } from "react"
import { type StatsDTO } from "../types/types"
import api from "../api";

function useGet24hStatistics() {
const url = "frontend/24stats"
const [stats24h, setStats24h] = useState<StatsDTO[]>([]);

useEffect(() => {
    const get24hStats = async () => {
      const { data } = await api.get(url);
      setStats24h(data);
    };
    get24hStats();

}, [])

    return stats24h; 
}

export default useGet24hStatistics
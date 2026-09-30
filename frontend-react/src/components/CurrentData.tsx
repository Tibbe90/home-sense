import useGetCurrentData from "../hooks/useGetCurrentData";
import type { TempHumidity } from "../types/types";

function CurrentData() {
const currentData = useGetCurrentData();
console.log(currentData);


return (
    <div>{(currentData).map((tempHumidity : TempHumidity) => (
        <span key={tempHumidity.device}>{tempHumidity.device} : {tempHumidity.temp.toFixed(1)}C {tempHumidity.humidity.toFixed(1)}% humidity<br /> </span>
    ))}</div>
)
}

export default CurrentData;
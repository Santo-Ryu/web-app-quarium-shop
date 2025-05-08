import { useState } from "react";
import { CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";

export const LineChartComponent = ({
    label,
    data,
    dataKey
})  => {

    return (
        <>
            <div className="line-chart">
                <div className="line-chart__label">
                    <h3>{label}</h3>
                </div>

                <ResponsiveContainer width="100%" height='80%'>
                    <LineChart
                        data={data} 
                        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                    >
                        <CartesianGrid strokeDasharray="0 2" />
                        <XAxis dataKey="month" fontSize={'0.7rem'}/>
                        <YAxis fontSize={'0.7rem'}/>
                        <Tooltip />
                        <Legend 
                            iconType="square" 
                            wrapperStyle={{
                                fontSize: '0.8rem', 
                                fontWeight: 500,
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}
                        />

                        <Line
                            type="monotone" 
                            dataKey={dataKey}
                            stroke="#000103c7"
                            name={label}
                        />
                    </LineChart>
                </ResponsiveContainer>
            </div>
        </>
    )
}
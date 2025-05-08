import { faBoxes, faDollar, faShoppingCart, faUsers } from "@fortawesome/free-solid-svg-icons"
import { DashboardCard } from "./DashboardCard"
import { LineChartComponent } from "./LineChartComponent"
import PieChartStructure from "./PieChartStructure"
import PieChartTopSale from './PieChartTopSale'

export const DoashboardContent = () => {
    const dashboardCards = [
        {fontAweSome: faBoxes, title: "Sản phẩm đã bán", content: 5623, details: {number: 57, text: "% tháng trước"}},
        {fontAweSome: faShoppingCart, title: "Số đơn hàng", content: 4656, details: {number: 4650, text: "đơn đã hoàn thành"}},
        {fontAweSome: faDollar, title: "Doanh thu tháng", content: "12,993,833 đ", details: {number: 27, text: " % tháng trước"}},
        {fontAweSome: faUsers, title: "Số khách hàng", content: 596, details: {number: 265, text: " online"}},
    ]

    const revenueData = [
        { month: 'Tháng 1', revenue: 4000 },
        { month: 'Tháng 2', revenue: 3000 },
        { month: 'Tháng 3', revenue: 5000 },
        { month: 'Tháng 4', revenue: 2780 },
        { month: 'Tháng 5', revenue: 1890 },
        { month: 'Tháng 6', revenue: 2390 },
        { month: 'Tháng 7', revenue: 3490 },
        { month: 'Tháng 8', revenue: 4000 },
        { month: 'Tháng 9', revenue: 3200 },
        { month: 'Tháng 10', revenue: 2800 },
        { month: 'Tháng 11', revenue: 4300 },
        { month: 'Tháng 12', revenue: 5000 },
    ];

    const newCustomerData = [
        { month: 'Tháng 1', number: 4000 },
        { month: 'Tháng 2', number: 3000 },
        { month: 'Tháng 3', number: 5000 },
        { month: 'Tháng 4', number: 2780 },
        { month: 'Tháng 5', number: 1890 },
        { month: 'Tháng 6', number: 2390 },
        { month: 'Tháng 7', number: 3490 },
        { month: 'Tháng 8', number: 4000 },
        { month: 'Tháng 9', number: 3200 },
        { month: 'Tháng 10', number: 2800 },
        { month: 'Tháng 11', number: 4300 },
        { month: 'Tháng 12', number: 5000 },
    ];

    return (
        <>
            <div className="dashboard-content">
                {/* OVERVIEW */}
                <div className="dashboard-content__overview">
                    {dashboardCards.map((e, key) => (
                        <DashboardCard 
                            fontAweSome={e.fontAweSome}
                            title={e.title}
                            content={e.content}
                            details={e.details}
                            key={key}
                        />
                    ))}
                </div>

                {/* CHART */}
                <div className="dashboard-content__line-chart">
                    <div className="chart-row">
                        <LineChartComponent 
                            label={"Doanh thu (tháng)"}
                            data={revenueData}
                            dataKey={"revenue"}
                        />
                        
                        <LineChartComponent 
                            label={"Khách hàng mới (tháng)"}
                            data={newCustomerData}
                            dataKey={"number"}
                        />
                    </div>

                    <div className="chart-row">
                        <PieChartTopSale />
                        <PieChartStructure />
                    </div>
                </div>

            </div>
        </>
    )
}

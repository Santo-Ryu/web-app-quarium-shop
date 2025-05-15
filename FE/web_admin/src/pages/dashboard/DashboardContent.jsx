import { faBoxes, faDollar, faShoppingCart, faUsers } from "@fortawesome/free-solid-svg-icons"
import { DashboardCard } from "./DashboardCard"
import { LineChartComponent } from "./LineChartComponent"
import PieChartStructure from "./PieChartStructure"
import PieChartTopSale from './PieChartTopSale'
import { useGetAccount } from "../../hooks/useAdmin"
import { formatCurrencyVN } from '../../app/service/Validator'

export const DoashboardContent = () => {
    const { data, isLoading, fetchData } = useGetAccount()
    if (isLoading) return "Đang tải..."

    const products = data.products
    const productImages = data.productImages
    const categories = data.categories
    const orderItems = data.orderItems
    const orders = data.orders
    const customers = data.customers

    const productsSold = products?.reduce((total, product) => {
        return total + (product.salesCount || 0);
    }, 0);
    const productsCount = products?.reduce((total, product) => {
        return total + (product.quantity || 0)
    }, 0)

    const ordersCompleted = orders.filter(order => order.status.statusName === "Đã hoàn thành").length

    const selectedMonth = (month) => {
        switch (month) {
            case 1: return 'Tháng 1';
            case 2: return 'Tháng 2';
            case 3: return 'Tháng 3';
            case 4: return 'Tháng 4';
            case 5: return 'Tháng 5';
            case 6: return 'Tháng 6';
            case 7: return 'Tháng 7';
            case 8: return 'Tháng 8';
            case 9: return 'Tháng 9';
            case 10: return 'Tháng 10';
            case 11: return 'Tháng 11';
            case 12: return 'Tháng 12';
            default: return 'Invalid month';
        }
    }

    const currentDate = new Date();
    const currentMonth = currentDate.getMonth() + 1; // 1-12
    const currentYear = currentDate.getFullYear();

    // Tính tháng trước, xử lý nếu là tháng 1
    const prevMonthDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1);
    const prevMonth = prevMonthDate.getMonth() + 1;
    const prevYear = prevMonthDate.getFullYear();

    // Tính doanh thu từng tháng
    let revenueCurrentMonth = 0;
    let revenuePreviousMonth = 0;

    orders.forEach(order => {
        const createdAt = new Date(order.createdAt);
        const orderMonth = createdAt.getMonth() + 1;
        const orderYear = createdAt.getFullYear();
        const orderTotal = order.price || 0;

        if (order.status.statusName === "Đã hoàn thành") {
            if (orderMonth === currentMonth && orderYear === currentYear) {
                revenueCurrentMonth += orderTotal;
            } else if (orderMonth === prevMonth && orderYear === prevYear) {
                revenuePreviousMonth += orderTotal;
            }
        }
    });
    const revenueDiff = revenuePreviousMonth === 0 ? 100: ((revenueCurrentMonth / revenuePreviousMonth) * 100).toFixed(2);

    const customerTotal = customers.length
    const customerVerified = customers.reduce((total, customer) => {
        return total += customer.verifyEmail === "VERIFIED" ? 1 : 0
    }, 0)

    const dashboardCards = [
        {fontAweSome: faBoxes, title: "Sản phẩm đã bán", content: productsSold, details: {number: productsCount, text: " tồn kho"}},
        {fontAweSome: faShoppingCart, title: "Số đơn hàng", content: orders.length, details: {number: ordersCompleted, text: "đơn đã hoàn thành"}},
        {fontAweSome: faDollar, title: "Doanh thu tháng", content: formatCurrencyVN(revenueCurrentMonth), details: {number: revenueDiff, text: " % tháng trước"}},
        {fontAweSome: faUsers, title: "Số khách hàng", content: customerTotal, details: {number: customerVerified, text: " user đã xác thực"}},
    ]

    const revenueData = [
        { month: 'Tháng 1', revenue: 0 },
        { month: 'Tháng 2', revenue: 0 },
        { month: 'Tháng 3', revenue: 0 },
        { month: 'Tháng 4', revenue: 0 },
        { month: 'Tháng 5', revenue: 0 },
        { month: 'Tháng 6', revenue: 0 },
        { month: 'Tháng 7', revenue: 0 },
        { month: 'Tháng 8', revenue: 0 },
        { month: 'Tháng 9', revenue: 0 },
        { month: 'Tháng 10', revenue: 0 },
        { month: 'Tháng 11', revenue: 0 },
        { month: 'Tháng 12', revenue: 0 },
    ];
    orders.forEach(order => {
        if (order.status.statusName === "Đã hoàn thành") {
            const createdAt = new Date(order.createdAt); 
            const month = createdAt.getMonth();  // tháng tạo đơn
            revenueData[month].revenue += order.price || 0;
        }
    });

    const newCustomerData = [
        { month: 'Tháng 1', number: 0 },
        { month: 'Tháng 2', number: 0 },
        { month: 'Tháng 3', number: 0 },
        { month: 'Tháng 4', number: 0 },
        { month: 'Tháng 5', number: 0 },
        { month: 'Tháng 6', number: 0 },
        { month: 'Tháng 7', number: 0 },
        { month: 'Tháng 8', number: 0 },
        { month: 'Tháng 9', number: 0 },
        { month: 'Tháng 10', number: 0 },
        { month: 'Tháng 11', number: 0 },
        { month: 'Tháng 12', number: 0 },
    ];
    customers.forEach(customer => {
        const createdAt = new Date(customer.createdAt);
        const month = createdAt.getMonth(); 
        newCustomerData[month].number += 1;
    });

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
                        <PieChartTopSale categories={categories} products={products} />
                        <PieChartStructure categories={categories} products={products} />
                    </div>
                </div>

            </div>
        </>
    )
}

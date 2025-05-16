import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faBoxArchive } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate } from "react-router-dom";
import { useGetAccount } from "../../hooks/useAdmin";
import { formatCurrencyVN } from "../../app/service/Validator";

export const OrderPage = () => {
    const navigate = useNavigate();
    const {data, isLoading} = useGetAccount()

    if (isLoading) return "Đang tải..."

    const orders = data?.orders

    const label = {text: 'Đơn hàng', icon: faBoxArchive}

    const switchColorStatus = (status) => {
        switch (status) {
            case "Đã hoàn thành": return "#4CAF50";      // Xanh lá
            case "Đang xử lý": return "#FFC107";    // Vàng
            case "Đang vận chuyển": return "#2196F3";       // Xanh dương
            default: return "#9E9E9E";                // Xám cho các trạng thái chưa rõ
        }
    };

    const columns = [
        {
            name: <span style={{ fontSize: '0.9rem', }}>ID</span>,
            selector: row => row.id,
            sortable: true,
            width: '50px'
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Mã đơn</span>,
            selector: row => row.code,
            sortable: true,
            width: '100px'
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Khách hàng</span>,
            selector: row => row.name,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Email</span>,
            selector: row => row.email,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem' }}>Tình trạng</span>,
            selector: row => row.status,
            sortable: true,
            cell: row => (
                <span style={{ color: switchColorStatus(row.status), fontWeight: 'bold' }}>
                    {row.status}
                </span>
            )
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Ngày đặt</span>,
            selector: row => row.createdAt,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Giá tiền</span>,
            selector: row => row.price,
            sortable: true,
            width: '150px'
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Chức năng</span>,
            cell: row => (
                <div className="product-table__button">
                    <button className="table-button product-table__button--view" onClick={() => navigate(`/order-details/${row.id}`)}>Xem thêm</button>
                </div>
            ),
            sortable: true,
            width: '160px'
        },
    ]

    const rows = orders?.map((order, index) => ({
        id: order.id,
        code: `#${order.id}` || `#${50000 + index}`,
        name: order.customer?.name || "Lỗi",
        email: order.customer?.email || "Lỗi",
        status: order.status.statusName || "Lỗi",
        createdAt: new Date(order.createdAt).toLocaleDateString("vi-VN"),
        price: formatCurrencyVN(order.price),
    })) || [];


    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        hiddenSearchBar={true}
                        columns={columns}
                        data={rows}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
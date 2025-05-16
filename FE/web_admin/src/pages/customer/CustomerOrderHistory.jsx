import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faBackwardStep, faHistory } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useGetAccount } from "../../hooks/useAdmin";
import { useEffect, useState } from "react";
import { formatCurrencyVN } from "../../app/service/Validator";

const switchColorStatus = (status) => {
    switch (status) {
        case "Đã hoàn thành": return "#4CAF50";      // Xanh lá
        case "Đang xử lý": return "#FFC107";    // Vàng
        case "Đang vận chuyển": return "#2196F3";       // Xanh dương
        case "Đã hủy": return "#f44336";          // Đỏ
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
        name: <span style={{ fontSize: '0.9rem', }}>Ngày giao</span>,
        selector: row => row.orderDate,
        sortable: true,
    },
    {
        name: <span style={{ fontSize: '0.9rem', }}>Giá tiền</span>,
        selector: row => row.price,
        sortable: true,
        width: '180px'
    },
    {
        name: <span style={{ fontSize: '0.9rem', }}>Chức năng</span>,
        cell: row => (<Link to={`/order-details/${row.id}`}>Xem thêm</Link>),
        sortable: true,
        width: '160px'
    },
]

export const CustomerOrderHistory = () => {
    const {id} = useParams()
    const navigate = useNavigate();
    const {data, isLoading, fetchData} = useGetAccount()
    const [customer, setCustomer] = useState();

    useEffect(() => {
        if (data?.customers) {
            data?.customers?.map((e, index) => {
                if (e.id === Number(id)) {
                    console.log("Customer found: ", e)
                    console.log("Customer ID FOUND: ", id)
                    setCustomer(e)
                    console.log(e)
                }
            })
        }
    }, [data, id])

    if (isLoading) return "Đang tải"

    const label = {text: `Lịch sử mua hàng của ${customer?.name}`, icon: faHistory}
    const buttons = [
        {func: () => navigate(`/customer-details/${customer?.id}`), name: "Trở về", icon: faBackwardStep}
    ]


    const myOrders = data?.orders
    ?.filter(order => order?.customer?.id === Number(id))
    ?.map((order, key) => (order));

    const dataT = myOrders?.map((o, index) => 
        {
            console.log("ORDER: ", o)
            return { 
                id: o.id, 
                code: `#${o.id}`, 
                name: o?.customer?.name || "NULL", 
                status: o.status.statusName, 
                createdAt: o.createdAt, 
                orderDate: new Date(o.orderDate).toISOString().split("T")[0] || "NULL", 
                price: formatCurrencyVN(o.price)
            }
        }
    );

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        buttons={buttons}
                        hiddenSearchBar={true}
                        columns={columns}
                        data={dataT}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
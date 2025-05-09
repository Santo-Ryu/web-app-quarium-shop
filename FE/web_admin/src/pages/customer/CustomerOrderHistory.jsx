import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faBackwardStep, faHistory } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate } from "react-router-dom";

export const CustomerOrderHistory = ({
    id
}) => {
    const navigate = useNavigate();
    const label = {text: 'Lịch sử mua hàng của Huynh Nhan', icon: faHistory}
    const buttonOption = {hidden: true, func: () => navigate('/customer-details'), name: "Trở về", icon: faBackwardStep}

    const switchColorStatus = (status) => {
        switch (status) {
            case "Hoàn thành": return "#4CAF50";      // Xanh lá
            case "Chờ xác nhận": return "#FFC107";    // Vàng
            case "Đang giao": return "#2196F3";       // Xanh dương
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
            selector: row => row.updatedAt,
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
            cell: row => (<Link to={'/order-details/{id}'}>Xem thêm</Link>),
            sortable: true,
            width: '160px'
        },
    ]

    const data = [
        { id: "1", code: "#51233", name: "Huynh Nhan", status: "Đang giao", createdAt: "08/05/2025", updatedAt: "NULL", price: "23,000 đ" },
        { id: "2", code: "#51234", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "07/05/2025", updatedAt: "07/05/2025", price: "150,000 đ" },
        { id: "3", code: "#51235", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "06/05/2025", updatedAt: "NULL", price: "80,000 đ" },
        { id: "4", code: "#51236", name: "Huynh Nhan", status: "Đang giao", createdAt: "06/05/2025", updatedAt: "NULL", price: "210,000 đ" },
        { id: "5", code: "#51237", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "05/05/2025", updatedAt: "NULL", price: "45,000 đ" },
        { id: "6", code: "#51238", name: "Huynh Nhan", status: "Đang giao", createdAt: "04/05/2025", updatedAt: "NULL", price: "99,000 đ" },
        { id: "7", code: "#51239", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "04/05/2025", updatedAt: "04/05/2025", price: "320,000 đ" },
        { id: "8", code: "#51240", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "03/05/2025", updatedAt: "NULL", price: "30,000 đ" },
        { id: "9", code: "#51241", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "02/05/2025", updatedAt: "NULL", price: "120,000 đ" },
        { id: "10", code: "#51242", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "01/05/2025", updatedAt: "01/05/2025", price: "170,000 đ" },
        { id: "11", code: "#51243", name: "Huynh Nhan", status: "Đang giao", createdAt: "30/04/2025", updatedAt: "NULL", price: "55,000 đ" },
        { id: "12", code: "#51244", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "29/04/2025", updatedAt: "29/04/2025", price: "88,000 đ" },
        { id: "13", code: "#51245", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "28/04/2025", updatedAt: "NULL", price: "190,000 đ" },
        { id: "14", code: "#51246", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "28/04/2025", updatedAt: "NULL", price: "60,000 đ" },
        { id: "15", code: "#51247", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "27/04/2025", updatedAt: "27/04/2025", price: "230,000 đ" },
        { id: "16", code: "#51248", name: "Huynh Nhan", status: "Đang giao", createdAt: "26/04/2025", updatedAt: "NULL", price: "75,000 đ" },
        { id: "17", code: "#51249", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "25/04/2025", updatedAt: "NULL", price: "100,000 đ" },
        { id: "18", code: "#51250", name: "Huynh Nhan", status: "Chờ xác nhận", createdAt: "24/04/2025", updatedAt: "NULL", price: "40,000 đ" },
        { id: "19", code: "#51251", name: "Huynh Nhan", status: "Hoàn thành", createdAt: "23/04/2025", updatedAt: "23/04/2025", price: "300,000 đ" },
        { id: "20", code: "#51252", name: "Huynh Nhan", status: "Đang giao", createdAt: "22/04/2025", updatedAt: "NULL", price: "110,000 đ" }
    ];

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        buttonOption={buttonOption}
                        hiddenSearchBar={true}
                        columns={columns}
                        data={data}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
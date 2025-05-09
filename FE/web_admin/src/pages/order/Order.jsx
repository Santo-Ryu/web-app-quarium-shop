import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faBoxArchive } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link } from "react-router-dom";

export const Order = () => {
    const label = {text: 'Đơn hàng', icon: faBoxArchive}

    const switchColorStatus = (status) => {
        switch (status) {
            case "Hoàn thành": return "#4CAF50";      // Xanh lá
            case "Chờ xác nhận": return "#FFC107";    // Vàng
            case "Đang giao": return "#2196F3";       // Xanh dương
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
        { id: "1", code: "#51233", name: "Huynh Nhan", email: "huynhhan@gmail.com", status: "Đang giao", createdAt: "08/05/2025", price: "23,000 đ" },
        { id: "2", code: "#51234", name: "Tran Hoa", email: "tranhoa@gmail.com", status: "Hoàn thành", createdAt: "07/05/2025", price: "150,000 đ" },
        { id: "3", code: "#51235", name: "Nguyen Minh", email: "nguyenminh@gmail.com", status: "Chờ xác nhận", createdAt: "06/05/2025", price: "80,000 đ" },
        { id: "4", code: "#51236", name: "Pham Tuan", email: "phamtuan@gmail.com", status: "Đang giao", createdAt: "06/05/2025", price: "210,000 đ" },
        { id: "5", code: "#51237", name: "Le Khanh", email: "lekhanh@gmail.com", status: "Chờ xác nhận", createdAt: "05/05/2025", price: "45,000 đ" },
        { id: "6", code: "#51238", name: "Bui Ha", email: "buiha@gmail.com", status: "Đang giao", createdAt: "04/05/2025", price: "99,000 đ" },
        { id: "7", code: "#51239", name: "Doan Trang", email: "doantrang@gmail.com", status: "Hoàn thành", createdAt: "04/05/2025", price: "320,000 đ" },
        { id: "8", code: "#51240", name: "Nguyen An", email: "nguyenan@gmail.com", status: "Chờ xác nhận", createdAt: "03/05/2025", price: "30,000 đ" },
        { id: "9", code: "#51241", name: "Tran Linh", email: "tranlinh@gmail.com", status: "Chờ xác nhận", createdAt: "02/05/2025", price: "120,000 đ" },
        { id: "10", code: "#51242", name: "Vo Hoang", email: "vohoang@gmail.com", status: "Hoàn thành", createdAt: "01/05/2025", price: "170,000 đ" },
        { id: "11", code: "#51243", name: "Mai Lan", email: "mailan@gmail.com", status: "Đang giao", createdAt: "30/04/2025", price: "55,000 đ" },
        { id: "12", code: "#51244", name: "Hoang Nam", email: "hoangnam@gmail.com", status: "Hoàn thành", createdAt: "29/04/2025", price: "88,000 đ" },
        { id: "13", code: "#51245", name: "Trinh My", email: "trinhmy@gmail.com", status: "Chờ xác nhận", createdAt: "28/04/2025", price: "190,000 đ" },
        { id: "14", code: "#51246", name: "Dang Phuc", email: "dangphuc@gmail.com", status: "Chờ xác nhận", createdAt: "28/04/2025", price: "60,000 đ" },
        { id: "15", code: "#51247", name: "Phan Kieu", email: "phankieu@gmail.com", status: "Hoàn thành", createdAt: "27/04/2025", price: "230,000 đ" },
        { id: "16", code: "#51248", name: "Le Duyen", email: "leduyen@gmail.com", status: "Đang giao", createdAt: "26/04/2025", price: "75,000 đ" },
        { id: "17", code: "#51249", name: "Vo Tuan", email: "votuan@gmail.com", status: "Chờ xác nhận", createdAt: "25/04/2025", price: "100,000 đ" },
        { id: "18", code: "#51250", name: "Pham Vy", email: "phamvy@gmail.com", status: "Chờ xác nhận", createdAt: "24/04/2025", price: "40,000 đ" },
        { id: "19", code: "#51251", name: "Tran Quang", email: "tranquang@gmail.com", status: "Hoàn thành", createdAt: "23/04/2025", price: "300,000 đ" },
        { id: "20", code: "#51252", name: "Nguyen Thu", email: "nguyenthu@gmail.com", status: "Đang giao", createdAt: "22/04/2025", price: "110,000 đ" }
    ];

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
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
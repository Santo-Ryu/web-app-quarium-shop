import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate } from "react-router-dom";

export const Customer = () => {
    const navigate = useNavigate();
    const label = {text: 'Khách hàng', icon: faUser}

    const switchColorStatus = (status) => {
        switch (status) {
            case "Online": return "#4CAF50";      // Xanh lá
            case "Offline": return "#f44336";    // Đỏ
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
            name: <span className="order-table__header">Hình ảnh</span>,
            sortable: true,
            width: '100px',
            cell: row => (<img src={row.image} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px', objectFit: 'cover'}} alt='Product Image' />)
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
            name: <span style={{ fontSize: '0.9rem', }}>Địa chỉ</span>,
            selector: row => row.address,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem' }}>Trạng thái</span>,
            selector: row => row.status,
            sortable: true,
            cell: row => (
                <span style={{ color: switchColorStatus(row.status), fontWeight: 'bold' }}>
                    {row.status}
                </span>
            )
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Chức năng</span>,
            cell: row => (
                <div className="product-table__button">
                    <button className="table-button product-table__button--view" onClick={() => navigate('/customer-details')}>Xem thêm</button>
                </div>
            ),
            sortable: true,
            width: '160px'
        },
    ]

    const data = [
        {id: "1", image: '/src/assets/avt1.jpg', name: "Huynh Nhan", email: "huynhnhan02@gmail.com", address: "Đà Nẵng", status: "Offline"},
        {id: "2", image: '/src/assets/avt2.jpg', name: "Tran Van Binh", email: "binhtv@gmail.com", address: "Hà Nội", status: "Online"},
        {id: "3", image: '/src/assets/avt2.jpg', name: "Le Thi Cam", email: "camle123@yahoo.com", address: "TP. HCM", status: "Offline"},
        {id: "4", image: '/src/assets/avt2.jpg', name: "Nguyen Van Hieu", email: "hieunv@gmail.com", address: "Huế", status: "Online"},
        {id: "5", image: '/src/assets/avt2.jpg', name: "Pham Minh Chau", email: "chaupm@hotmail.com", address: "Đà Nẵng", status: "Offline"},
        {id: "6", image: '/src/assets/avt2.jpg', name: "Hoang Bao Anh", email: "anhhb@gmail.com", address: "Nha Trang", status: "Online"},
        {id: "7", image: '/src/assets/avt2.jpg', name: "Vo Van Khoa", email: "khoavv@gmail.com", address: "Cần Thơ", status: "Offline"},
        {id: "8", image: '/src/assets/avt2.jpg', name: "Nguyen Hoai Thu", email: "thunh@gmail.com", address: "Bình Định", status: "Online"},
        {id: "9", image: '/src/assets/avt2.jpg', name: "Tran Quoc Toan", email: "toantq@live.com", address: "Hải Phòng", status: "Offline"},
        {id: "10", image: '/src/assets/avt2.jpg', name: "Mai Thanh Ha", email: "hamt@gmail.com", address: "Quảng Nam", status: "Online"},
        {id: "11", image: '/src/assets/avt2.jpg', name: "Le Quang Minh", email: "minhlq@gmail.com", address: "Bắc Ninh", status: "Online"},
        {id: "12", image: '/src/assets/avt2.jpg', name: "Dang Thi Ngoc", email: "ngocdt@gmail.com", address: "Bến Tre", status: "Offline"},
        {id: "13", image: '/src/assets/avt2.jpg', name: "Vo Tan Tai", email: "taivt@gmail.com", address: "Đồng Nai", status: "Online"},
        {id: "14", image: '/src/assets/avt2.jpg', name: "Nguyen Bao Han", email: "hanbn@gmail.com", address: "Hà Nam", status: "Offline"},
        {id: "15", image: '/src/assets/avt2.jpg', name: "Bui Khanh Linh", email: "linhbk@gmail.com", address: "Vũng Tàu", status: "Online"}
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
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faBoxArchive, faBoxes } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate } from "react-router-dom";

export const Product = () => {
    const navigate = useNavigate();
    const label = {text: 'Sản phẩm', icon: faBoxes}

    const switchColorStatus = (status) => {
        switch (status) {
            case "Hoàn thành": return "#4CAF50";      // Xanh lá
            case "Chờ xác nhận": return "#FFC107";    // Vàng
            case "Đang giao": return "#2196F3";       // Xanh dương
            default: return "#9E9E9E";                // Xám cho các trạng thái chưa rõ
        }
    };

    const selectOption = [
        {id: 1, option: "Cá cảnh"},
        {id: 2, option: "Cây thủy sinh"},
        {id: 3, option: "Thức Ăn"},
        {id: 4, option: "Cá cảnh"},
    ]
    

    const columns = [
        {
            name: <span style={{ fontSize: '0.9rem', }}>ID</span>,
            selector: row => row.id,
            sortable: true,
            width: '50px'
        },
        {
            name: <span className="product-table__header">Hình ảnh</span>,
            sortable: true,
            width: '100px',
            cell: row => (<img src={row.image} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Sản phẩm</span>,
            selector: row => row.name,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Giá bán</span>,
            selector: row => row.price,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem' }}>Danh mục</span>,
            selector: row => row.category,
            sortable: true
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Tồn kho</span>,
            selector: row => row.quantity,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Chức năng</span>,
            cell: row => (
                <div className="product-table__button">
                    <button className="table-button product-table__button--view" onClick={() => navigate('/product-details')}>Xem thêm</button>
                    <button className="table-button product-table__button--delete">Xóa</button>
                </div>
            ),
            sortable: true,
        },
    ]

    const data = [
        {id: 1, image: '/src/assets/beta1.jpg', name: 'Cá beta siêu đẹp', price: "200.000", category: "Cá cảnh", quantity: "125"}
    ];

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        selectOption={selectOption}
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
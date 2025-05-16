import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faAdd, faBoxes } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { useNavigate } from "react-router-dom";
import { useGetAccount } from "../../hooks/useAdmin";
import { BASE_URL } from "../../app/config/configApi";
import { formatCurrencyVN } from "../../app/service/Validator";
import { useState } from "react";
import { deleteProduct } from "../../app/api/product.api";

export const Product = () => {
    const navigate = useNavigate();
    const [selectedCategoryId, setSelectedCategoryId] = useState(0);
    const {data, isLoading, fetchData} = useGetAccount()
    const label = {text: 'Sản phẩm', icon: faBoxes}

    if (isLoading) return "Đang tải..."

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
            name: <span className="product-table__header">Hình ảnh</span>,
            sortable: true,
            width: '100px',
            cell: row => (<img src={`${BASE_URL}api/public/image?name=${row.image}`} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
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
                    <button className="table-button product-table__button--view" onClick={() => navigate(`/product-details/${row.id}`)}>Xem thêm</button>
                    <button className="table-button product-table__button--delete" onClick={() => handleDeleteProduct(row.id)}>Xóa</button>
                </div>
            ),
            sortable: true,
        },
    ]

    const productImages = data?.productImages
    const dataT = data?.products
        ?.filter((e) => selectedCategoryId === 0 || e.category.id === selectedCategoryId)
        ?.map((e) => ({
            id: e.id,
            image: productImages?.find(p => p.product.id === e.id)?.name || 'isLoading.jpg',
            name: e.name,
            price: formatCurrencyVN(e.price),
            category: e.category.category,
            quantity: e.quantity
        }))

    
    const selectOption = [{id: 0, option: "Tất cả"}]
    data?.categories?.map((e, index) => {
        selectOption.push(
            {
                id: e.id,
                option: e.category
            }
        )
    })

    const buttons = [{
        func: () => {navigate("/product-add")},
        icon: faAdd,
        name: "Thêm"
    }]

    const handleDeleteProduct = async (id) => {
        const response = await deleteProduct(id)
        if (response) alert("Xóa thành công!")
        fetchData()
    }

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        buttons={buttons}
                        selectOption={selectOption}
                        hiddenSearchBar={true}
                        columns={columns}
                        data={dataT}
                        rowPerPage={10}
                        onSelectChange={(id) => setSelectedCategoryId(parseInt(id))}
                    />
                } 
            />
        </>
    )
}
import { useNavigate, useParams } from "react-router-dom"
import { faBoxes, faClose, faGear, faHistory, faRecycle, faSave, faStore } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from "../../components/DetailsContent";
import { useState } from "react";
import { FieldsRenderer } from "../../components/FieldsRenderer";
import { Value } from "sass";

export const ProductDetails = () => {
    const navigate = useNavigate();
    const id = useParams();
    const [disabledInput, setDisabledInput] = useState(true)

    const label = {text: 'Chi tiết sản phẩm', icon: faBoxes}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const fields = [
        { label: "ID sản phẩm", value: "1", type: "text", name: "id" , disabled: disabledInput},
        { label: "Danh mục", value: "Cá cảnh", type: "select", name: "category", options: ["Cá cảnh", "Cây thủy sinh"], disabled: disabledInput},
        { label: "Tên sản phẩm", value: "Cá beta siêu đẹp", type: "text", name: "name", disabled: disabledInput },
        { label: "Mô tả", value: "Giống cá sống bền, dễ nuôi, không cần chăm sóc nhiều, Giá thành từ rẻ đến đắt đa dạng", type: "quill", name: "description", disabled: disabledInput },
        { label: "Đánh giá", value: "4.5", type: "text", name: "rating", disabled: disabledInput },
        { label: "Giá tiền", value: "200.000", type: "number", name: "price", disabled: disabledInput },
        { label: "Tồn kho", value: "120", type: "number", name: "stock", disabled: disabledInput },
        { label: "Đã bán", value: "26", type: "number", name: "sold", disabled: disabledInput },
        { label: "Ngày thêm vào", value: "2025-04-24", type: "date", name: "createdAt", disabled: disabledInput },
        { label: "Cập nhật gần đây", value: "2025-04-25", type: "date", name: "updatedAt", disabled: disabledInput },
    ];

    const listButton = [
        {button: "Dừng bán", icon: faStore, onClick: null},
        {button: "Xóa", icon: faRecycle, onClick: null},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: null},
        {button: "Đóng", icon: faClose, onClick: () => navigate('/product')},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <DetailsContent
                        listButton={listButton}
                        layout={<FieldsRenderer fields={fields}  />}
                    />
                }
            />
        </>
    )
}
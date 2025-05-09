import { useNavigate, useParams } from "react-router-dom"
import { faBoxes, faClose, faGear, faHistory, faRecycle, faSave, faStore } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from "../../components/DetailsContent";
import { useState } from "react";
import { ProductDetailsContent } from "./ProductDetailsContent";

export const ProductDetails = () => {
    const navigate = useNavigate();
    const id = useParams();
    const [disabledInput, setDisabledInput] = useState(true)

    const label = {text: 'Chi tiết sản phẩm', icon: faBoxes}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

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
                        layout={<ProductDetailsContent disabledInput={disabledInput} />}
                    />
                }
            />
        </>
    )
}
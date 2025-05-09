import { faClose, faGear, faHistory, faSave, faUser } from "@fortawesome/free-solid-svg-icons";
import { useNavigate, useParams } from "react-router-dom";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from '../../components/DetailsContent';
import { CustomerDetailsContent } from "./CustomerDetailsContent";
import { useState } from "react";

export const CustomerDetails = () => {
    const navigate = useNavigate();
    const [disabledInput, setDisabledInput] = useState(true)
    const id = useParams();

    const label = {text: 'Khách hàng', icon: faUser}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const typeInfo = [
        {label: "ID khách hàng", value: "1", inputType: "text", disabled: disabledInput},
        {label: "Họ và tên", value: "Huynh Nhan", inputType: "text", disabled: disabledInput},
        {label: "Giới tính", value: "Nam", inputType: "text", disabled: disabledInput},
        {label: "Số điện thoại", value: "0354337112", inputType: "tel", disabled: disabledInput},
        {label: "Email", value: "huynhnhan2202@gmail.com", inputType: "email", disabled: disabledInput},
        {label: "Địa chỉ", value: "Đà Nẵng", inputType: "text", disabled: disabledInput},
        {label: "Ngày sinh", value: "1999-02-22", inputType: "date", disabled: disabledInput},
        {label: "Ngày đăng ký", value: "2025-04-25", inputType: "date", disabled: true},
        {label: "Cập nhật gần đây", value: "2025-04-27", inputType: "date", disabled: disabledInput}
    ]

    const listButton = [
        {button: "Lịch sử", icon: faHistory, onClick: () => navigate('/customer-order-history')},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: null},
        {button: "Đóng", icon: faClose, onClick: () => navigate('/customer')},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <DetailsContent 
                        listButton={listButton}
                        layout={<CustomerDetailsContent typeInfo={typeInfo} />}
                    />
                }
            />
        </>
    )
}
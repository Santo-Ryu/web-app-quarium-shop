import { Helmet } from "react-helmet-async"
import { MainLayout } from "../../layouts/MainLayout"
import { DetailsContent } from '../../components/DetailsContent';
import { FieldsRenderer } from '../../components/FieldsRenderer';
import { faGear, faKey, faSave, faSignOut, faUser } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";

export const Personal = () => {
    const [disabledInput, setDisabledInput] = useState(true)
    const [isChangePasswordLayout, setIsChangePasswordLayout] = useState(true) // false: change password, true: personal

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }
    const handleChangeLayout = (layout) => {
        switch(layout) {
            case "personal": return setIsChangePasswordLayout(true);
            case "changePassword": return setIsChangePasswordLayout(false);
            default: return console.log("Layout not valid!")
        }
    }

    const label = {text: 'Cá nhân', icon: faGear}
    const fieldsInfomation = [
        { label: "ID quan trị viên", value: "1", type: "text", name: "id" , disabled: disabledInput},
        { label: "Họ và tên", value: "Ly Na", type: "text", name: "name", disabled: disabledInput},
        { label: "Giới tính", value: "Nữ", type: "select", name: "gender", options: ['Nam', 'Nữ'] , disabled: disabledInput },
        { label: "Ngày sinh", value: "2000-02-22", type: "date", name: "date", disabled: disabledInput },
        { label: "Email", value: "lyna2202@gmail.com", type: "text", name: "email", disabled: disabledInput },
        { label: "Số điện thoại", value: "0354337115", type: "number", name: "phone", disabled: disabledInput },
        { label: "Ngày đăng ký", value: "2022-04-26", type: "date", name: "createdAt", disabled: disabledInput },
        { label: "Cập nhật gần đây", value: "2025-04-26", type: "date", name: "updatedAt", disabled: disabledInput },
    ];
    const fieldsChangePassword = [
        { label: "Mật khẩu hiện tại", type: "password", name: "currentPassword" , disabled: false},
        { label: "Mật khẩu mới", type: "password", name: "newPassword" , disabled: false},
        { label: "Mật khẩu mới", type: "password", name: "confirmPassword" , disabled: false},
        { type: "button", buttons: [
            {text: "Trở về", onClick: () => handleChangeLayout('personal'), className: "button-back"}, 
            {text: "Xác nhận", onClick: () => {}, className: "button-confirm"}
        ] , disabled: false},
    ]

    const listButton = [
        {button: "Đổi mật khẩu", icon: faKey, onClick: () => handleChangeLayout('changePassword')},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: null},
        {button: "Đăng xuất", icon: faSignOut, onClick: null},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <DetailsContent 
                        listButton={listButton}
                        layout={<FieldsRenderer fields={isChangePasswordLayout ? fieldsInfomation : fieldsChangePassword} />}
                    />
                } 
            />
        </>
    )
}
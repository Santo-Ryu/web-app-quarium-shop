import { Helmet } from "react-helmet-async"
import { MainLayout } from "../../layouts/MainLayout"
import { DetailsContent } from '../../components/DetailsContent';
import { FieldsRenderer } from '../../components/FieldsRenderer';
import { faGear, faKey, faSave, faSignOut, faUser } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useRef, useState } from "react";
import { useGetAccount } from "../../hooks/useAdmin";
import { formatStringToDate } from "../../app/service/Validator";
import { changePassword, updateAdmin, updateImage } from "../../app/api/admin.api";
import { encrypt, hashPassword } from "../../app/service/Encrypt";
import { useNavigate } from "react-router-dom";

export const Personal = () => {
    const navigate = useNavigate()
    const [adminInfo, setAdminInfo] = useState()
    const [password, setPassword] = useState({newPassword: '', confirmPassword: ''})
    const {data, isLoading, fetchData} = useGetAccount()
    const [disabledInput, setDisabledInput] = useState(true)
    const [isChangePasswordLayout, setIsChangePasswordLayout] = useState(true) // false: change password, true: personal
    const fileInputRef = useRef(null);

    useEffect(() => {
        if (data?.admin) setAdminInfo(data?.admin)
    }, [data, isLoading])

    if (isLoading) return "Đang tải..."

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

    const handleFieldChange = (key, value) => {
        if (key === "date") key = "birthDate"; // map lại key đúng với adminInfo
        setAdminInfo(prev => ({
            ...prev,
            [key]: value,
        }));
    }

    const handlePasswordFieldChange = (key, value) => {
        setPassword(prev => ({
            ...prev,
            [key]: value.trim(),
        }))
    }

    const handleImageClick = () => {
        fileInputRef.current.click(); 
    }

    const handleUpdateImage = async (event) => {
        const file = event.target.files[0];
        if (!file) return;

        try {
            const success = await updateImage(adminInfo.id, "admin", file);
            if (success) {
                alert("Cập nhật ảnh thành công");
                fetchData();  // refresh data nếu cần
            } else {
                alert("Cập nhật ảnh thất bại");
            }
        } catch (error) {
            alert("Lỗi khi cập nhật ảnh");
            console.error(error);
        }
    }

    const handleUpdateAdmin = async () => {
        const response = await updateAdmin(adminInfo)
        if (response) {
            fetchData()
            alert("Cập nhật thành công!")
        }else alert("Có lỗi xảy ra!")
    }

    const handleChangePassword = async () => {
        if (password.confirmPassword === '' || password.newPassword === '') 
            return alert('Vui lòng nhập đủ thông tin!')
        if (password.confirmPassword.length < 6 || password.newPassword.length < 6) 
            return alert('Độ dài mật khẩu phải > 6!')
        if (password.confirmPassword !== password.newPassword) 
            return alert('Mật khẩu không giống nhau!')
        
        const form = {
            email: encrypt(adminInfo.email),
            password: hashPassword(password.newPassword),
            role: encrypt("ADMIN")
        }
        const response = await changePassword(form)
        if (response) {
            handleLogout()
            alert("Đổi mật khẩu thành công!")
        }else alert("Có lỗi xảy ra!")
    }

    const handleLogout = () => {
        localStorage.clear()
        navigate("/auth/login")
    }

    const label = {text: 'Cá nhân', icon: faGear}
    const fieldsInfomation = [
        {key: "id", label: "ID quan trị viên", value: adminInfo?.id, type: "text", name: "id" , disabled: true},
        {key: "name", label: "Họ và tên", value: adminInfo?.name, type: "text", name: "name", disabled: disabledInput},
        {key: "gender", label: "Giới tính", value: adminInfo?.gender, type: "select", name: "gender", options: ['ORTHER', 'MALE', 'FEMALE'] , disabled: disabledInput },
        {key: "date", label: "Ngày sinh", value: formatStringToDate(adminInfo?.birthDate), type: "date", name: "birthDate", disabled: disabledInput },
        {key: "email", label: "Email", value: adminInfo?.email, type: "text", name: "email", disabled: true },
        {key: "phone", label: "Số điện thoại", value: adminInfo?.phone, type: "number", name: "phone", disabled: disabledInput },
        {key: "createdAt", label: "Ngày đăng ký", value: formatStringToDate(adminInfo?.createdAt), type: "date", name: "createdAt", disabled: true },
        {key: "updatedAt", label: "Cập nhật gần đây", value: formatStringToDate(adminInfo?.updatedAt), type: "date", name: "updatedAt", disabled: true },
    ];

    const fieldsChangePassword = [
        {key: "newPassword", label: "Mật khẩu mới", value: password.newPassword, type: "password", name: "newPassword" , disabled: false},
        {key: "confirmPassword", label: "Mật khẩu mới", value: password.confirmPassword, type: "password", name: "confirmPassword" , disabled: false},
        { type: "button", buttons: [
            {text: "Trở về", onClick: () => handleChangeLayout('personal'), className: "button-back"}, 
            {text: "Xác nhận", onClick: () => handleChangePassword(), className: "button-confirm"}
        ] , disabled: false},
    ]

    const listButton = [
        {button: "Đổi mật khẩu", icon: faKey, onClick: () => handleChangeLayout('changePassword')},
        {button: "Chỉnh sửa", icon: faGear, onClick: () => handleDisabledInput()},
        {button: "Lưu", icon: faSave, onClick: () => handleUpdateAdmin()},
        {button: "Đăng xuất", icon: faSignOut, onClick: () => handleLogout()},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <DetailsContent 
                        listButton={listButton}
                        layout={
                            isChangePasswordLayout ?
                                <FieldsRenderer
                                    onChange={handleFieldChange} 
                                    fields={fieldsInfomation} 
                                />
                                :
                                <FieldsRenderer
                                    onChange={handlePasswordFieldChange} 
                                    fields={fieldsChangePassword} 
                                />
                        }
                        image={adminInfo?.image?.name || "user.png"}
                        onImageClick={handleImageClick}
                        fileInputRef={fileInputRef}
                        onImageChange={handleUpdateImage}
                    />
                } 
            />
        </>
    )
}
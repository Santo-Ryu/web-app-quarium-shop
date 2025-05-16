import { faClose, faGear, faHistory, faSave, faUser } from "@fortawesome/free-solid-svg-icons";
import { useNavigate, useParams } from "react-router-dom";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from '../../components/DetailsContent';
import { FieldsRenderer } from "../../components/FieldsRenderer";
import { useGetAccount } from "../../hooks/useAdmin";
import { useState, useEffect, useRef } from "react"
import { updateCustomer, updateImage } from "../../app/api/admin.api";

export const CustomerDetails = () => {
    const [customer, setCustomer] = useState();
    const [image, setImage] = useState('');
    const {id} = useParams();
    const fileInputRef = useRef(null);
    const {data, isLoading, fetchData} = useGetAccount()

    useEffect(() => {
        if (data?.customers) {
            data?.customers?.map((e, index) => {
                if (e.id === Number(id)) {
                    console.log("Customer found: ", e)
                    console.log("Customer ID FOUND: ", id)
                    setCustomer(e)
                    setImage(e.image.name)
                    console.log(e)
                }
            })
        }
    }, [data, id])

    const navigate = useNavigate();
    const [disabledInput, setDisabledInput] = useState(true)

    if (isLoading) return "Đang tải"

    const label = {text: 'Khách hàng', icon: faUser}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const typeGender = (type) => {
        switch(type) {
            case "OTHER": return "Khác"
            case "MALE": return "Nam"
            case "FEMALE": return "Nữ"
        }
    }

    const handleFieldChange = (key, value) => {
        if (key === "date") key = "birthDate"; // map lại key đúng với adminInfo
        setCustomer(prev => ({
            ...prev,
            [key]: value,
        }));
    }

    const typeInfo = [
        { key: "id", label: "ID khách hàng", value: customer?.id || "", type: "text", disabled: true },
        { key: "name", label: "Họ và tên", value: customer?.name || "", type: "text", disabled: disabledInput },
        { key: "gender", label: "Giới tính", value: customer?.gender || "", type: "select", options: ['MALE', 'FEMALE', 'OTHER'], disabled: disabledInput },
        { key: "phone", label: "Số điện thoại", value: customer?.phone || "", type: "tel", disabled: disabledInput },
        { key: "email", label: "Email", value: customer?.email || "", type: "email", disabled: true },
        { key: "address", label: "Địa chỉ", value: customer?.address || "", type: "text", disabled: disabledInput },
        { key: "date", label: "Ngày sinh", value: customer?.birthDate
            ? new Date(customer.birthDate).toISOString().split("T")[0] : "", type: "date", name: "birthDate", disabled: disabledInput },
        { key: "createdAt", label: "Ngày đăng ký", value: customer?.createdAt
            ? new Date(customer.createdAt).toISOString().split("T")[0] : "", type: "date", disabled: true },
        { key: "updatedAt", label: "Cập nhật gần đây", value: customer?.updatedAt
            ? new Date(customer.updatedAt).toISOString().split("T")[0] : "", type: "date", disabled: true }
    ]

    const handleUpdateCustomer = async () => {
        const response = await updateCustomer(customer)
        if (response) {
            fetchData()
            alert("Cập nhật thành công!")
        }else alert("Có lỗi xảy ra!")
    }

    const handleImageClick = () => {
        fileInputRef.current.click(); 
    }

    const handleUpdateImage = async (event) => {
        const file = event.target.files[0];
        if (!file) return;

        try {
            const success = await updateImage(customer.id, "customer", file);
            if (success) {
                alert("Cập nhật ảnh thành công");
                // cập nhật lại ảnh để hiển thị
                setImage(file.name); 
                fetchData();  // refresh data nếu cần
            } else {
                alert("Cập nhật ảnh thất bại");
            }
        } catch (error) {
            alert("Lỗi khi cập nhật ảnh");
            console.error(error);
        }
    }

    const listButton = [
        {button: "Lịch sử", icon: faHistory, onClick: () => navigate(`/customer-order-history/${customer?.id}`)},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: () => handleUpdateCustomer()},
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
                        layout={<FieldsRenderer fields={typeInfo} onChange={handleFieldChange} />}
                        image={image ? image : 'user.png'}
                        onImageClick={handleImageClick}
                        fileInputRef={fileInputRef}
                        onImageChange={handleUpdateImage}
                    />
                }
            />
        </>
    )
}
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { Link, useNavigate } from "react-router-dom";
import { useGetAccount } from "../../hooks/useAdmin";
import { BASE_URL } from "../../app/config/configApi";
import { deleteCustomer } from "../../app/api/admin.api";

export const Customer = () => {
    const navigate = useNavigate();
    const {data, isLoading, fetchData} = useGetAccount()

    if (isLoading) return "Đang tải..."

    const customers = data.customers

    const label = {text: 'Khách hàng', icon: faUser}

    const switchColorStatus = (status) => {
        switch (status) {
            case "VERIFIED": return "#4CAF50";      
            case "UNVERIFIED": return "#f44336";   
            default: return "#9E9E9E";                
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
            cell: row => (<img src={`${BASE_URL}api/public/image?name=${row.image || "user.png"}`} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px', objectFit: 'cover'}} alt='Product Image' />)
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
                    <button className="table-button product-table__button--view" onClick={() => navigate(`/customer-details/${row.id}`)}>Xem thêm</button>
                    <button className="table-button product-table__button--delete" onClick={() => {handleDeleteCustomer(row.id)}} >Xóa</button>
                </div>
            ),
            sortable: true,
        },
    ]

    const handleDeleteCustomer = async (id) => {
        const response = await deleteCustomer(id)
        if (response) {
            fetchData()
            alert("Xóa thành công!")
        }else alert("Có lỗi xảy ra!")
    }

    const dataT = customers.map((e, index) => ({
        id: e.id,
        image: e.image.name,
        name: e.name,
        email: e.email,
        address: e.adress || "Chưa cập nhật",
        status: e.verifyEmail
    }))


    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        hiddenSearchBar={true}
                        columns={columns}
                        data={dataT}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
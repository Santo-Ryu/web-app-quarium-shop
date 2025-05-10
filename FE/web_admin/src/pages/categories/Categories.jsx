import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { CustomTable } from "../../components/CustomTable";
import { faAdd, faList } from "@fortawesome/free-solid-svg-icons";

const label = {text: 'Danh mục', icon: faList}

export const Categories = () => {

    const handleUpdate = () => {
        const category = window.prompt("Nhập tên danh mục mới: ")
    }
    const handleAdd = () => {
        const category = window.prompt("Nhập tên danh mục mới: ")
    }
    
    const columns = [
        {
            name: <span style={{ fontSize: '0.9rem', }}>ID</span>,
            selector: row => row.id,
            sortable: true,
            width: '80px',
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Danh mục</span>,
            selector: row => row.category,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Ngày tạo</span>,
            selector: row => row.createdAt,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Cập nhật gần đây</span>,
            selector: row => row.updatedAt,
            sortable: true,
        },
        {
            name: <span style={{ fontSize: '0.9rem', }}>Chức năng</span>,
            cell: row => (
                <div className="product-table__button">
                    <button className="table-button product-table__button--view" onClick={() => handleUpdate()}>Chỉnh sửa</button>
                    <button className="table-button product-table__button--delete">Xóa</button>
                </div>
            ),
            sortable: true,
        },
    ]

    const data = [
        {id: 1, category: "Cá cảnh", createdAt: "2025-04-22", updatedAt: "2025-04-22"},
        {id: 2, category: "Cây thủy sinh", createdAt: "2025-04-22", updatedAt: "2025-04-22"},
        {id: 3, category: "Thức ăn", createdAt: "2025-04-22", updatedAt: "2025-04-22"},
    ]

    const buttons = [{func: () => handleAdd(), name: "Thêm danh mục mới", icon: faAdd}]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable
                        buttons={buttons}
                        hiddenSearchBar={false}
                        columns={columns}
                        data={data}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
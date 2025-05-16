import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { CustomTable } from "../../components/CustomTable";
import { faAdd, faList } from "@fortawesome/free-solid-svg-icons";
import { useGetAccount } from "../../hooks/useAdmin";
import { formatStringToDate } from "../../app/service/Validator";
import { addCategory, deleteCategory, updateCategory } from "../../app/api/category.api";

const label = {text: 'Danh mục', icon: faList}

export const Categories = () => {
    const {data, isLoading, fetchData} = useGetAccount()

    if (isLoading) return "Đang tải..."

    const handleUpdate = (category, id) => {
        const categoryName = window.prompt(`Nhập tên danh mục mới cho "${category}": `)
        if (categoryName == null || categoryName == "") {
            alert("Danh mục trống!")
        }else {
            const response = updateCategory(id, categoryName)
            if (response) {
                fetchData()
            }
        }
    }

    const handleAdd = () => {
        const categoryName = window.prompt("Nhập tên danh mục mới: ")
        if (categoryName == null || categoryName == "") {
            alert("Danh mục trống!")
        }else {
            const response = addCategory(categoryName)
            if (response) {
                fetchData()
            }
        }
    }
    const handleDelete = async (id) => {
        const response = deleteCategory(id)
        if (response) {
            fetchData()
            alert("Xóa thành công!")
        }else alert("Có lỗi xảy ra!")
    }

    const dataT = data?.categories?.map((item, index) => (
        {
            id: item?.id, 
            category: item?.category, 
            createdAt: formatStringToDate(item?.createdAt), 
            updatedAt: formatStringToDate(item?.updatedAt)
        }
    ))

    const buttons = [{func: () => handleAdd(), name: "Thêm danh mục mới", icon: faAdd}]

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
                    <button className="table-button product-table__button--view" onClick={() => handleUpdate(row.category, row.id)}>Chỉnh sửa</button>
                    <button className="table-button product-table__button--delete" onClick={() => handleDelete(row.id)}>Xóa</button>
                </div>
            ),
            sortable: true,
        },
    ]

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
                        data={dataT}
                        rowPerPage={10}
                    />
                } 
            />
        </>
    )
}
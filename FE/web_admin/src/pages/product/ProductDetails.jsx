import { data, useNavigate, useParams } from "react-router-dom"
import { faBoxes, faClose, faGear, faHistory, faRecycle, faSave, faStore } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from "../../components/DetailsContent";
import { useEffect, useRef, useState } from "react";
import { FieldsRenderer } from "../../components/FieldsRenderer";
import { Value } from "sass";
import { useGetAccount } from "../../hooks/useAdmin";
import { formatStringToDate } from "../../app/service/Validator";
import { BASE_URL } from "../../app/config/configApi";
import { updateImage } from "../../app/api/admin.api";
import { deleteProduct, deleteProductImage, updateProduct } from "../../app/api/product.api";

export const ProductDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const [disabledInput, setDisabledInput] = useState(true)
    const [categories, setCategories] = useState([])
    const [productImages, setProductImages] = useState([])
    const [product, setProduct] = useState()
    const {data, isLoading, fetchData} = useGetAccount()
    const fileInputRef = useRef(null);

    useEffect(() => {
        if (data?.categories?.length > 0) {
            const cats = data.categories.map(e => e.category)
            setCategories(cats)
        }
        if (data?.products?.length > 0) {
            const product = data.products.find(p => p.id === Number(id))
            setProduct(product)
        }
        if (data?.productImages.length > 0) {
            const images = []
            data.productImages.map(e => {
                if (e.product.id === Number(id)) {
                    images.push({
                        id: e.id,
                        preview: `${BASE_URL}api/public/image?name=${e.name}`
                    })
                }
            })
            setProductImages(images)
        }
    }, [data, isLoading])

    if (isLoading) return "Đang tải..."

    const label = {text: 'Chi tiết sản phẩm', icon: faBoxes}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const handleAddImage = async (e) => {
        const file = e.target.files[0];

        if (!file) return;

        const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];
        if (!allowedTypes.includes(file.type)) {
            alert("Chỉ chấp nhận các định dạng ảnh: JPG, JPEG, PNG.");
            return;
        }

        console.log("Ảnh hợp lệ:", file);

        const response = await updateImage(product.id, "product", file);
        if (response) {
            fetchData();
            alert("Cập nhật thành công!");
        } else {
            alert("Có lỗi xảy ra!");
        }
    };

    const handleDeleteProductImage = async (e) => {
        const response = await deleteProductImage(e.id)
        if (response) {
            fetchData();
            alert("Đã xóa 1 hình ảnh!");
        } else {
            alert("Có lỗi xảy ra!");
        }
    }

    const handleUpdateProduct = async () => {
        console.log("PRODUCT: ", product)
        const response = await updateProduct(product)
        if (response) {
            fetchData();
            alert("Cập nhật thành công!");
        } else {
            alert("Có lỗi xảy ra!");
        }
    }

    const handleFieldChange = (key, value) => {
        if (!product) return;
        if (key === "category") {
            setProduct(prev => ({
                ...prev,
                category: {
                    ...prev.category,
                    category: value
                }
            }));
        } else {
            setProduct(prev => ({
                ...prev,
                [key]: value
            }));
        }
    }

    const handleDeleteProduct = async () => {
        const response = await deleteProduct(product.id)
        if (response) alert("Xóa thành công!")
        navigate("/product")
    }

    const fields = [
        {key: "productId", label: "ID sản phẩm", value: product?.id, type: "text", name: "id" , disabled: true},
        {key: "category", label: "Danh mục", value: product?.category?.category, type: "select", name: "category", options: categories, disabled: disabledInput},
        {key: "productName", label: "Tên sản phẩm", value: product?.name, type: "text", name: "name", disabled: disabledInput },
        {key: "description", label: "Mô tả", value: product?.description, type: "quill", name: "description", disabled: disabledInput },
        {key: "rating", label: "Đánh giá", value: product?.rating, type: "text", name: "rating", disabled: true },
        {key: "price", label: "Giá tiền", value: product?.price, type: "number", name: "price", disabled: disabledInput },
        {key: "quantity", label: "Tồn kho", value: product?.quantity, type: "number", name: "quantity", disabled: disabledInput },
        {key: "sold", label: "Đã bán", value: product?.salesCount, type: "number", name: "sold", disabled: true },
        {key: "createdAt", label: "Ngày thêm vào", value: formatStringToDate(product?.createdAt), type: "date", name: "createdAt", disabled: true },
        {key: "updatedAt", label: "Cập nhật gần đây", value: formatStringToDate(product?.updatedAt), type: "date", name: "updatedAt", disabled: true },
    ];

    const listButton = [
        // {button: "Dừng bán", icon: faStore, onClick: null},
        {button: "Xóa", icon: faRecycle, onClick: () => handleDeleteProduct()},
        {button: "Chỉnh sửa", icon: faGear, onClick: () => handleDisabledInput()},
        {button: "Lưu", icon: faSave, onClick: () => handleUpdateProduct()},
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
                        layout={<FieldsRenderer fields={fields} onChange={handleFieldChange} />}
                        images={productImages}
                        fileInputRef={fileInputRef}
                        onImageChange={(e) => handleAddImage(e)}
                        onDeleteImage={(e) => handleDeleteProductImage(e)}
                        hiddenImages={true}
                    />
                }
            />
        </>
    )
}
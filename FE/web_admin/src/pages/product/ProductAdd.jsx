import { useNavigate, useParams } from "react-router-dom"
import { faBoxes, faClose, faGear, faHistory, faRecycle, faSave, faStore } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DetailsContent } from "../../components/DetailsContent";
import { useEffect, useState, useRef } from "react";
import { FieldsRenderer } from "../../components/FieldsRenderer";
import { useGetAccount } from "../../hooks/useAdmin";
import { addNewProduct } from "../../app/api/product.api";

export const ProductAdd = () => {
    const [productImages, setProductImages] = useState([])
    const [categories, setCategories] = useState([])
    const [product, setProduct] = useState({category: '', name: '', description: '', price: null, quantity: null})
    const navigate = useNavigate();
    const {data, isLoading, fetchData} = useGetAccount()
    const id = useParams();
    const fileInputRef = useRef(null);

    useEffect(() => {
        if (data?.categories?.length > 0) {
            const cats = data.categories.map(e => e.category);
            setCategories(cats);
            if (!product.category) {
                setProduct(prev => ({ ...prev, category: cats[0] }));
            }
        }
    }, [data])

    if (isLoading) return "Đang tải..."

    const label = {text: 'Thêm sản phẩm', icon: faBoxes}

    const fields = [
        {key: "category", label: "Danh mục", value: product.category, type: "select", name: "category", options: categories, disabled: false},
        {key: "name", label: "Tên sản phẩm", value: product.name, type: "text", name: "name", disabled: false },
        {key: "description", label: "Mô tả", value: product.description, type: "quill", name: "description", disabled: false },
        {key: "price", label: "Giá tiền", value: product.price, type: "number", name: "price", disabled: false },
        {key: "quantity", label: "Sô lượng", value: product.quantity, type: "number", name: "quantity", disabled: false },
    ];

    const handleSaveProduct = async () => {
        const newProduct = { ...product, productImages: productImages };
        setProduct(newProduct);

        const formData = new FormData()
        formData.append('category', product.category)
        formData.append('name', product.name)
        formData.append('description', product.description)
        formData.append('price', product.price)
        formData.append('quantity', product.quantity)
        productImages.forEach((e) => {
            formData.append("productImages", e.file);
        });
        console.log("formData: ", formData)

        const response = await addNewProduct(formData)
        if (response) {
            alert("Thêm sản phẩm thành công!")
            navigate('/product')
        }
    }

    const listButton = [
        {button: "Lưu", icon: faSave, onClick: () => handleSaveProduct()},
        {button: "Đóng", icon: faClose, onClick: () => navigate('/product')},
        {button: "Xóa hết hình ảnh", icon: faClose, onClick: () => setProductImages([])},
    ]

    const handleFieldChange = (key, value) => {
        setProduct(prev => ({ ...prev, [key]: value }));
    };

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files);
        const filesWithPreview = files.map(file => ({
            file,
            preview: URL.createObjectURL(file)
        }));
        setProductImages(prev => [...prev, ...filesWithPreview]);
    };

    const handleDeleteImageInProductImages = (imgObjToDelete) => {
        setProductImages(prev => {
            URL.revokeObjectURL(imgObjToDelete.preview);
            return prev.filter(img => img.preview !== imgObjToDelete.preview);
        });
    };

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
                        onImageChange={handleImageChange}
                        onImageClick={() => fileInputRef.current?.click()}
                        onDeleteImage={handleDeleteImageInProductImages}
                        hiddenImages={true}
                    />
                }
            />
        </>
    )
}
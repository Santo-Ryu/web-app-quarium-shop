import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faCheck, faMessage, faTags, faTimes } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";
import { useGetAccount } from "../../hooks/useAdmin";
import { formatStringToDate } from "../../app/service/Validator";
import { BASE_URL } from "../../app/config/configApi";
import { useEffect, useState } from "react";
import { applyDiscount, destroyDiscount } from "../../app/api/product.api";

export const Discount = () => {
    const [selectedProducts, setSelectedProducts] = useState([]);
    const [products, setProducts] = useState()
    const [disabled, setDisabled] = useState(false)
    const {data, isLoading, fetchData} = useGetAccount()
    const label = {text: 'Giảm giá', icon: faTags}

    useEffect(() => {
        if (data?.products.length > 0) setProducts(data?.products)
    }, [data, isLoading])

    useEffect(() => {
        console.log("selectedProducts: ", selectedProducts)
        console.log("products: ", products)
    }, [selectedProducts, products])

    if (isLoading) return "Đang tải..."

    const handleDisable = () => { setDisabled(prev => !prev) }

    const dataT = (products || []).map(e => (
        {
            id: e.id,
            name: e.name,
            image: data?.productImages.find(p => p.product.id == e.id).name,
            discountPercentage: e.discountPercentage || "",
            discountStartDate: formatStringToDate(e.discountStartDate),
            discountEndDate: formatStringToDate(e.discountEndDate),
        }
    ))

    const handleSelectDiscountProduct = (e, product) => {
        handleDisable()
        if (e.target.checked) {
            setSelectedProducts(prev => [...prev, product]);
        } else {
            setSelectedProducts(prev => prev.filter(p => p.id !== product.id));
        }
    };

    const handleChangeDiscountPercentage = (productId, value) => {
        setProducts(prevProducts =>
            prevProducts.map(product =>
                product.id === productId
                    ? { ...product, discountPercentage: value }
                    : product
            )
        );
    };

    const handleChangeDiscountEndDate = (productId, value) => {
        const today = new Date();
        const selectedDate = new Date(value);

        today.setHours(0, 0, 0, 0);
        selectedDate.setHours(0, 0, 0, 0);

        if (selectedDate < today) {
            alert("Ngày hết hạn không được nhỏ hơn ngày hiện tại!");
            return;
        }

        setProducts(prevProducts => {
            const update = prevProducts.map(product =>
                product.id === productId
                    ? { ...product, discountEndDate: value }
                    : product
            )
            // console.log("Danh sách sau khi cập nhật:", update);
            return update
        });
    };

    const handleApplyDiscount = async () => {
        const invalid = selectedProducts.some(p => {
            const prod = products.find(prod => prod.id === p.id);
            return !prod || Number(prod.discountPercentage) <= 0;
        });

        if (invalid) {
            alert("Vui lòng nhập % giảm giá lớn hơn 0 cho tất cả sản phẩm được chọn!");
            return;
        }
        
        const response = await applyDiscount(selectedProducts);
        if (response) {
            alert("Áp dụng giảm giá thành công!");
        } else {
            alert("Có lỗi xảy ra!");
        }
        fetchData();
        setSelectedProducts([]);
    };

    const handleDestroyDiscount = async () => {
        const invalid = selectedProducts.some(p => {
            const prod = products.find(prod => prod.id === p.id);
            return !prod || Number(prod.discountPercentage) <= 0;
        });

        if (invalid) {
            alert("Chỉ có thể hủy giảm giá các sản phẩm đang có giảm giá hợp lệ!");
            return;
        }

        const response = await destroyDiscount(selectedProducts);
        if (response) {
            alert("Hủy giảm giá thành công!");
        } else {
            alert("Có lỗi xảy ra!");
        }
        fetchData();
        setSelectedProducts([]);
    };

    const buttons = [
        {func: () => handleDestroyDiscount(), name: "Hủy khuyến mãi", icon: faTimes},
        {func: () => handleApplyDiscount(), name: "Áp dụng khuyến mãi", icon: faCheck},
    ]

    const columns = [
        {
            name: <span className="columns__header" style={{ fontSize: "0.9rem" }}> Chọn </span>,
            sortable: true,
            width: "100px",
            cell: (row) => (
                <div>
                    <input  
                        type="checkbox"
                        checked={selectedProducts.some(p => p.id === row.id)}
                        onChange={(e) => handleSelectDiscountProduct(e, row)}
                    />
                </div>
            ),
        },
        {
            name: <span className="column__header">Hình ảnh</span>,
            sortable: true,
            width: '100px',
            cell: row => (<img src={`${BASE_URL}api/public/image?name=${row.image}`} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px', objectFit: 'cover'}} alt='Product Image' />)
        },
        {
            name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Sản phẩm </span>,
            sortable: true,
            selector: (row) => row.name,
        },
        {
            name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Khuyến mãi (%) </span>,
            sortable: true,
            cell: (row) => (
                <input
                    className="column__input"
                    type="text" 
                    value={row.discountPercentage}
                    onChange={e => handleChangeDiscountPercentage(row.id, e.target.value)}
                    disabled={selectedProducts.some(p => p.id === row.id)}
                />
            ),
            width: "200px"
        },
        {
            name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Bắt đầu </span>,
            sortable: true,
            cell: (row) => (
                <input
                    className="column__input"
                    type="date" 
                    value={row.discountStartDate}
                    disabled
                />
            ),
            width: "200px"
        },
        {
            name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Hết hạn </span>,
            sortable: true,
            cell: (row) => (
                <input
                    className="column__input"
                    type="date" 
                    value={row.discountEndDate}
                    onChange={e => handleChangeDiscountEndDate(row.id, e.target.value)}
                    disabled={selectedProducts.some(p => p.id === row.id)}
                />
            ),
            width: "200px"
        }
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        buttons={buttons}
                        columns={columns}
                        data={dataT}
                        rowPerPage={10}
                    />
                }
            />
        </>
    )
}
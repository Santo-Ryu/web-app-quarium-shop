import { CustomTable } from '../../components/CustomTable';
import { useNavigate } from 'react-router-dom';
import { BASE_URL } from '../../app/config/configApi';
import { useGetAccount } from '../../hooks/useAdmin';
import { formatCurrencyVN } from '../../app/service/Validator';
import { useState, useEffect } from "react"
import { deleteProductInOrder, updateOrder } from '../../app/api/order.api';

export const OrderUpdateContent = ({
    id
}) => {
    const navigate = useNavigate();
    const [orderState, setOrderSate] = useState({
        orderId: 0,
        orderStatus: "",
        products: [],
    });
    const {data, isLoading, fetchData } = useGetAccount()

    const orders = data?.orders
    const order = orders?.find(item => item.id == id)
    const customer = order?.customer
    const orderItems = data?.orderItems
    const myOrderItems = orderItems?.filter(item => item.order.id === order.id)
    const productImages = data?.productImages

    useEffect(() => {
        if (order && myOrderItems && productImages && orderState.products.length === 0) {
            const updatedProduct = myOrderItems.map((item) => ({
                productId: item.product.id,
                productName: item.product.name,
                image: productImages.find(pi => pi.product.id === item.product.id)?.name || "loading.jpg",
                quantity: item.quantity,
                price: item.price,
                discount: item.discountPercent,
                totalPrice: item.price * item.quantity * (1 - item.discountPercent / 100),
            }));

            setOrderSate({
                orderId: order.id,
                orderStatus: order.status.statusName,
                products: updatedProduct,
            });
        }
    }, [order, myOrderItems, productImages]);


    if (isLoading || !order) return "Đang tải..."

    const columns = [
        {
            name: <span className="order-table__header">Sản phẩm</span>,
            selector: row => row.productName,
            sortable: true,
        },
        {
            name: <span className="order-table__header">Hình ảnh</span>,
            sortable: true,
            cell: row => (<img src={`${BASE_URL}api/public/image?name=${row.image}`} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
        },
        {
            name: <span className="order-table__header">Số lượng</span>,
            selector: (row, index) => (
                <input 
                    className='order-table__quantity' 
                    type='number' 
                    value={row.quantity} 
                    min={1}
                    onChange={e => {
                        const quantity = parseInt(e.target.value) || 1;
                        const newProduct = [...orderState.products];

                        const discount = newProduct[index].discount || 0;
                        const price = newProduct[index].price;
                        const discountedPrice = discount > 0 
                            ? Math.floor(price * (1 - discount / 100))
                            : price;

                        newProduct[index].quantity = quantity;
                        newProduct[index].totalPrice = discountedPrice * quantity;

                        setOrderSate(prev => ({ ...prev, product: newProduct }));
                    }} 
                />
            ),
            sortable: true,
        },
        {
            name: <span className="order-table__header">Đơn giá</span>,
            sortable: true,
            cell: row => (
                <div>
                    {row.discount > 0 ? (
                        <>
                            <p style={{ textDecoration: 'line-through', color: '#999' }}>{row.price}</p>
                            <p>{formatCurrencyVN(Math.floor(row.price * (1 - parseFloat(row.discount) / 100)))}</p>
                        </>
                    ) : (
                        <p>{formatCurrencyVN(row.price)}</p>
                    )}
                </div>
            )
        },
        {
            name: <span className="order-table__header">Tổng tiền</span>,
            selector: row => formatCurrencyVN(row.totalPrice),
            sortable: true
        },
        {
            name: <span className="order-table__header">Chức năng</span>,
            sortable: true,
            cell: row => (
                <button className='order-table__button--delete' onClick={() => handleDeleteProductInOrder(order.id, row.productId)}>Xóa</button>
            )
        }
    ]

    const handleUpdateOrder = async (request, id) => {
        const response = await updateOrder(request)
        if (response) {
            alert("Cập nhật thành công!")
            navigate(`/order-details/${id}`)
        }else alert("Có lỗi xảy ra!")
    }

    const handleDeleteProductInOrder = async (orderId, productId) => {
        const response = await deleteProductInOrder(orderId, productId)
        if (response) {
            alert("Xóa sản phẩm thành công!")
            navigate("/order")
        }else alert("Có lỗi xảy ra!")
    }

    const buttonList = [
        {buttonName: 'Lưu', className: 'order-details__button order-details__button--edit', onClick: () => {handleUpdateOrder(orderState, order.id)}},
        {buttonName: 'Đóng', className: 'order-details__button order-details__button--cancel', onClick: () => navigate(`/order-details/${order.id}`)}
    ]

    const selectStatus = ["Đã hoàn thành", "Đang xử lý", "Đang vận chuyển"]

    const priceTemp = orderState.products.reduce((sum, item) => sum + item.price * item.quantity, 0);
    const priceDiscount = orderState.products.reduce((sum, item) => {
        if (item.discount > 0) {
            return sum + (item.price * item.quantity * item.discount / 100);
        }
        return sum;
    }, 0);
    const totalPrice = priceTemp - priceDiscount;

    return (
        <article className="order-details">
            <div className="order-details__main">
                <header className="order-details__header">
                    <h4 className="order-details__title">Mã đơn hàng: <u>#{order.id}</u></h4>
                    <select className='order-details__select' 
                        value={orderState.orderStatus} 
                        onChange={e => 
                            setOrderSate(prev => ({
                                ...prev,
                                orderStatus: e.target.value
                            }))
                        }
                        >
                        {selectStatus.map((status, key) => (
                            <option key={key} value={status}>{status}</option>
                        )) }
                    </select>
                </header>

                <div className="order-details__info">
                    <section className="order-details__customer">
                        <h3 className="order-details__section-title">Khách hàng</h3>
                        <div className="order-details__customer-details--input">
                            <p>{customer.name}</p>
                            <p>{customer.phone}</p>
                            <p>{customer.address}</p>
                        </div>
                    </section>
                    <section className="order-details__notes">
                        <h3 className="order-details__section-title">Ghi chú</h3>
                        <div className="order-details__notes-content">
                            {order.note}
                        </div>
                    </section>
                </div>

                <section className="order-details__products">
                    <CustomTable 
                        hiddenSearchBar={false}
                        columns={columns}
                        data={orderState.products}
                        rowPerPage={7}
                    />
                </section>
            </div>

            <div className="order-details__summary">
                <section className="order-details__calculation">
                    <h2 className="order-details__section-title">Hóa đơn thanh toán</h2>
                    <div className="order-details__price-breakdown">
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Tạm tính</dt>
                            <dd className="order-details__price-value">{formatCurrencyVN(priceTemp)}</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Khuyến mãi</dt>
                            <dd className="order-details__price-value">{formatCurrencyVN(priceDiscount)}</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Phí vận chuyển</dt>
                            <dd className="order-details__price-value">miễn phí</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Thành tiền</dt>
                            <dd className="order-details__price-value">{formatCurrencyVN(totalPrice)}</dd>
                        </dl>
                    </div>

                    <footer className="order-details__total">
                        <h3 className="order-details__total-label">Cần thanh toán</h3>
                        <p className="order-details__total-amount">{formatCurrencyVN(totalPrice)}</p>
                    </footer>
                </section>

                <footer className="order-details__actions">
                    {buttonList.map((button, index) => (
                        <button 
                            key={index} 
                            className={button.className}
                            onClick={button.onClick}
                        >
                            {button.buttonName}
                        </button>
                    ))}
                </footer>
            </div>
        </article>
    )
}
import { useNavigate } from 'react-router-dom';
import { CustomTable } from '../../components/CustomTable';
import { useGetAccount } from '../../hooks/useAdmin';
import { formatCurrencyVN } from '../../app/service/Validator';
import { BASE_URL } from '../../app/config/configApi';
import { destroyOrder } from '../../app/api/order.api';

export const OrderDetailsContent = ({
    id
}) => {
    const navigate = useNavigate();
    const {data, isLoading, fetchData } = useGetAccount()

    if (isLoading) return "Đang tải..."

    const orders = data?.orders
    const order = orders?.find(item => item.id == id)
    const customer = order?.customer
    const orderItems = data?.orderItems
    const myOrderItems = orderItems?.filter(item => item.order.id === order.id)
    const productImages = data?.productImages

    let priceTemp = 0
    let priceDiscount = 0

    myOrderItems?.forEach(e => {
        priceTemp += e.price * e.quantity
        if (e.discountPercent > 0) {
            priceDiscount += (e.price * e.quantity) * e.discountPercent / 100
        }
    })

    const columns = [
        {
            name: <span className="order-table__header">Tên sản phẩm</span>,
            selector: row => row.productName,
            sortable: true,
        },
        {
            name: <span className="order-table__header">Hình ảnh</span>,
            sortable: true,
            width: '100px',
            cell: row => (<img src={`${BASE_URL}api/public/image?name=${row.image}`} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
        },
        {
            name: <span className="order-table__header">Số lượng</span>,
            selector: row => row.quantity,
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
        }
    ]


    const dataT = myOrderItems.map((item, index) => (
        { 
            productName: item.product.name,
            image: productImages.find(pi => pi.product.id === item.product.id).name || "loading.jpg", 
            quantity: item.quantity, 
            price: item.price,
            totalPrice: item.subtotal,
            discount: item.discountPercent
        }
    ))

    const handleDestroyOrder = async () => {
        const option = window.confirm("Bạn chắc chắn muốn hủy đơn hàng này!")
        if (option) {
            const response = await destroyOrder(order.id)
            if (response) {
                alert("Đã hủy đơn hàng!")
                navigate("/order")
            }
        }
    }

    const buttonList = [
        {buttonName: 'Sửa đơn', className: 'order-details__button order-details__button--edit', onClick: () => navigate(`/order-update/${order.id}`)},
        {
            buttonName: 'Hủy đơn', 
            className: 'order-details__button order-details__button--cancel', 
            onClick: () => {handleDestroyOrder()}
        }
    ]

    const changeColorStatus = (type) => {
        switch (type) {
            case "Đã hoàn thành": return "#4CAF50";      // Xanh lá
            case "Đang xử lý": return "#FFC107";    // Đỏ
            case "Đang vận chuyển": return "#2196F3";       // Xanh dương
            default: return "#9E9E9E";                // Xám cho các trạng thái chưa rõ
        }
    }

    return (
        <article className="order-details">
            <div className="order-details__main">
                <header className="order-details__header">
                    <h4 className="order-details__title">Mã đơn hàng: <u>#{order.id}</u></h4>
                    <div className="order-details__status" style={{backgroundColor: changeColorStatus(order.status.statusName)}}>
                        <p className="order-details__status-text">{order.status.statusName}</p>
                    </div>
                </header>

                <div className="order-details__info">
                    <section className="order-details__customer">
                        <h3 className="order-details__section-title">Khách hàng</h3>
                        <div className="order-details__customer-details">
                            <p className="order-details__customer-name">{customer.name}</p>
                            <p className="order-details__customer-info">{customer.phone}</p>
                            <p className="order-details__customer-info">{customer.address}</p>
                        </div>
                    </section>
                    <section className="order-details__notes">
                        <h3 className="order-details__section-title">Ghi chú</h3>
                        <div className="order-details__notes-content">
                            {order.note ? order.note : ""}
                        </div>
                    </section>
                </div>

                <section className="order-details__products">
                    <CustomTable 
                        hiddenSearchBar={false}
                        columns={columns}
                        data={dataT}
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
                            <dd className="order-details__price-value">{formatCurrencyVN(order.price)}</dd>
                        </dl>
                    </div>

                    <footer className="order-details__total">
                        <h3 className="order-details__total-label">Cần thanh toán</h3>
                        <p className="order-details__total-amount">{formatCurrencyVN(order.price)}</p>
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
                    <button className="order-details__button order-details__button--close" onClick={() => {navigate("/order")}}>Đóng</button>
                </footer>
            </div>
        </article>
    )
}
import { useNavigate } from 'react-router-dom';
import { CustomTable } from '../../components/CustomTable';

export const OrderDetailsContent = ({
    id
}) => {
    const navigate = useNavigate();

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
            cell: row => (<img src={row.image} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
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
                            <p>{Math.round(parseFloat(row.price) * (1 - row.discount)).toLocaleString()}.000 (-{row.discount*100}%)</p>
                        </>
                    ) : (
                        <p>{row.price}</p>
                    )}
                </div>
            )
        },
        {
            name: <span className="order-table__header">Tổng tiền</span>,
            selector: row => row.totalPrice,
            sortable: true
        }
    ]

    const data = [
        { id: 1, productName: "Cá beta thái", image: "/src/assets/beta1.jpg", quantity: 1, price: "200.000", totalPrice: "160.000", discount: 0.2},
        { id: 2, productName: "Cá beta sữa", image: "/src/assets/beta3.jpg", quantity: 1, price: "120.000", totalPrice: "120.000", discount: 0},
    ]      

    const buttonList = [
        {buttonName: 'Sửa đơn', className: 'order-details__button order-details__button--edit', onClick: () => navigate('/order-update')},
        {buttonName: 'Hủy đơn', className: 'order-details__button order-details__button--cancel', onClick: () => {}}
    ]

    const changeColorStatus = (type) => {
        switch (type) {
            case "Hoàn thành": return "#00B667";      // Xanh lá
            case "Chờ xác nhận": return "#f44336";    // Đỏ
            case "Đang giao": return "#2196F3";       // Xanh dương
            default: return "#9E9E9E";                // Xám cho các trạng thái chưa rõ
        }
    }

    return (
        <article className="order-details">
            <div className="order-details__main">
                <header className="order-details__header">
                    <h4 className="order-details__title">Mã đơn hàng: <u>#89988</u></h4>
                    <div className="order-details__status" style={{backgroundColor: changeColorStatus('Hoàn thành')}}>
                        <p className="order-details__status-text">Hoàn thành</p>
                    </div>
                </header>

                <div className="order-details__info">
                    <section className="order-details__customer">
                        <h3 className="order-details__section-title">Khách hàng</h3>
                        <div className="order-details__customer-details">
                            <p className="order-details__customer-name">Huynh Nhan</p>
                            <p className="order-details__customer-info">035433714</p>
                            <p className="order-details__customer-info">Đà Nẵng</p>
                        </div>
                    </section>
                    <section className="order-details__notes">
                        <h3 className="order-details__section-title">Ghi chú</h3>
                        <div className="order-details__notes-content">
                            Lời nhắc: không có
                        </div>
                    </section>
                </div>

                <section className="order-details__products">
                    <CustomTable 
                        hiddenSearchBar={false}
                        columns={columns}
                        data={data}
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
                            <dd className="order-details__price-value">180.000 đ</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Khuyến mãi</dt>
                            <dd className="order-details__price-value">-40.000 đ</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Phí vận chuyển</dt>
                            <dd className="order-details__price-value">10.000 đ</dd>
                        </dl>
                        <dl className="order-details__price-item">
                            <dt className="order-details__price-label">Thành tiền</dt>
                            <dd className="order-details__price-value">190.000 đ</dd>
                        </dl>
                    </div>

                    <footer className="order-details__total">
                        <h3 className="order-details__total-label">Cần thanh toán</h3>
                        <p className="order-details__total-amount">190.000 đ</p>
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
                    <button className="order-details__button order-details__button--close">Đóng</button>
                </footer>
            </div>
        </article>
    )
}
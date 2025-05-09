import { width } from '@fortawesome/free-brands-svg-icons/fa42Group';
import { CustomTable } from '../../components/CustomTable';

export const OrderUpdateContent = ({
    id
}) => {
    const columns = [
        {
            name: <span className="order-table__header">Sản phẩm</span>,
            selector: row => row.productName,
            sortable: true,
        },
        {
            name: <span className="order-table__header">Hình ảnh</span>,
            sortable: true,
            cell: row => (<img src={row.image} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px'}} alt='Product Image' />)
        },
        {
            name: <span className="order-table__header">Số lượng</span>,
            selector: row => (<input className='order-table__quantity' type='number' value={row.quantity} />),
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
        },
        {
            name: <span className="order-table__header">Chức năng</span>,
            sortable: true,
            cell: row => (
                <button className='order-table__button--delete'>Xóa</button>
            )
        }
    ]

    const data = [
        { id: 1, productName: "Cá beta thái", image: "/src/assets/beta1.jpg", quantity: 1, price: "200.000", totalPrice: "160.000", discount: 0.2},
        { id: 2, productName: "Cá beta sữa", image: "/src/assets/beta3.jpg", quantity: 1, price: "120.000", totalPrice: "120.000", discount: 0},
    ]      

    const buttonList = [
        {buttonName: 'Lưu', className: 'order-details__button order-details__button--edit'},
        {buttonName: 'Đóng', className: 'order-details__button order-details__button--cancel'}
    ]

    const selectStatus = ["Hoàn thành", "Chờ xử lý", "Đang giao"]

    return (
        <article className="order-details">
            <div className="order-details__main">
                <header className="order-details__header">
                    <h4 className="order-details__title">Mã đơn hàng: <u>#89988</u></h4>
                    <select className='order-details__select' name="" id="">
                        {selectStatus.map((status, key) => (
                            <option key={key} value={status}>{status}</option>
                        )) }
                    </select>
                </header>

                <div className="order-details__info">
                    <section className="order-details__customer">
                        <h3 className="order-details__section-title">Khách hàng</h3>
                        <div className="order-details__customer-details--input">
                            <input type="text" name="" placeholder='Khách hàng' id="" value={"Huynh Nhan"} />
                            <input type="tel" name="" placeholder='Số điện thoại' id="" value={"035433714"} />
                            <input type="text" name="" placeholder='Địa chỉ' value={"Đà Nẵng"} id="" />
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
                        >
                            {button.buttonName}
                        </button>
                    ))}
                </footer>
            </div>
        </article>
    )
}
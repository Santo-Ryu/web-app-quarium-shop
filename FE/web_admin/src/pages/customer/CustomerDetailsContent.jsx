import { faClose, faGear, faHistory, faSave } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export const CustomerDetailsContent = ({
    id
}) => {
    const navigate = useNavigate();
    const [disabledInput, setDisabledInput] = useState(true)

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const typeInfo = [
        {label: "ID khách hàng", value: "1", inputType: "text", disabled: disabledInput},
        {label: "Họ và tên", value: "Huynh Nhan", inputType: "text", disabled: disabledInput},
        {label: "Giới tính", value: "Nam", inputType: "text", disabled: disabledInput},
        {label: "Số điện thoại", value: "0354337112", inputType: "tel", disabled: disabledInput},
        {label: "Email", value: "huynhnhan2202@gmail.com", inputType: "email", disabled: disabledInput},
        {label: "Địa chỉ", value: "Đà Nẵng", inputType: "text", disabled: disabledInput},
        {label: "Ngày sinh", value: "1999-02-22", inputType: "date", disabled: disabledInput},
        {label: "Ngày đăng ký", value: "2025-04-25", inputType: "date", disabled: true},
        {label: "Đăng nhập lần cuối", value: "2025-04-27", inputType: "date", disabled: disabledInput}
    ]

    const listButton = [
        {button: "Lịch sử", icon: faHistory, onClick: () => navigate('/customer-order-history')},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: null},
        {button: "Đóng", icon: faClose, onClick: () => navigate('/customer')},
    ]

    return (
        <>
            <main className="customer-details">
                <section className="customer-details__info">
                    <h4 className="customer-details__header">Thông tin</h4>
                    <ul className="customer-details__list">
                        {typeInfo.map((e, key) => (
                            <li className="customer-details__list--item"  key={key}>
                                <label htmlFor="">{e.label}</label>
                                <input className={e.disabled ? `hidden-outline` : ''} type={e.inputType} value={e.value} disabled={e.disabled} />
                            </li>
                        ))}
                    </ul>
                </section>

                <article className="customer-details__func">
                    <h3 className="customer-details__func--header">Hình ảnh</h3>
                    <img className="customer-details__func--image" src="/src/assets/avt2.jpg" alt="Image" />
                    <ul className="customer-details__func--list-button">
                        {listButton.map((e, key) => (
                            <li className="list-button__item" key={key}>
                                <button onClick={e.onClick}>
                                    <FontAwesomeIcon icon={e.icon} />
                                    <p>{e.button}</p>
                                </button>
                            </li>
                        ))}
                    </ul>
                </article>
            </main>
        </>
    )
}
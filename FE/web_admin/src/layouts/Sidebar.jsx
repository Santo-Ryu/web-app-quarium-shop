import { faBox, faBoxArchive, faGear, faHome, faLeaf, faMessage, faUser } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useState } from "react";

export const Sidebar = ({
    openSidebar
}) => {
    const [activeIndex, setActiveIndex] = useState(0); // mặc định chọn mục đầu

    const listIcon = [
        {icon: faHome, label: "Trang chủ"},
        {icon: faMessage, label: "Tin nhắn"},
        {icon: faBoxArchive, label: "Đơn hàng"},
        {icon: faUser, label: "Khách hàng"},
        {icon: faBox, label: "Sản phẩm"},
        {icon: faGear, label: "Cá nhân"},
    ]


    return (
        <>
            <div className={`sidebar ${!openSidebar ? `hidden` : ''}`}>
                <div className="sidebar__logo"><FontAwesomeIcon icon={faLeaf} /></div>
                <hr className="sidebar__line" />
                <ul className="sidebar__menu">
                    {listIcon.map((item, index) => (
                    <li 
                        key={index} 
                        className={`sidebar__item ${activeIndex === index ? `active` : ''}`} 
                        onClick={() => setActiveIndex(index)}
                    >
                        <a href="#" className="sidebar__link">
                            <FontAwesomeIcon icon={item.icon} /> <span>{item.label}</span>
                        </a>
                    </li>
                    ))}
                </ul>
            </div>
        </>
    )
}
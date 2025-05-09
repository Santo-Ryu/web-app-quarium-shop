import { faBox, faBoxArchive, faGear, faHome, faLeaf, faMessage, faUser } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { Link, useLocation } from "react-router-dom";

export const Sidebar = ({
    openSidebar
}) => {
    const location = useLocation();

    const listIcon = [
        {icon: faHome, label: "Trang chủ", link: "/"},
        {icon: faMessage, label: "Tin nhắn", link: "/message"},
        {icon: faBoxArchive, label: "Đơn hàng", link: "/order"},
        {icon: faUser, label: "Khách hàng", link: "/customer"},
        {icon: faBox, label: "Sản phẩm", link: ""},
        {icon: faGear, label: "Cá nhân", link: ""},
    ]

    return (
        <>
            <div className={`sidebar ${!openSidebar ? `hidden` : ''}`}>
                <div className="sidebar__logo"><FontAwesomeIcon icon={faLeaf} /></div>
                <hr className="sidebar__line" />
                <ul className="sidebar__menu">
                    {listIcon.map((item, index) => (
                        <li key={index}  className="sidebar__item" >
                            <Link to={item.link} className="sidebar__link">
                                <FontAwesomeIcon icon={item.icon} />
                                <span>{item.label}</span>
                            </Link>
                        </li>   
                    ))}
                </ul>
            </div>
        </>
    )
}
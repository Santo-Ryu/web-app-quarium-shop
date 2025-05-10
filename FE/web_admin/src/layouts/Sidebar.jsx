import { faBox, faBoxArchive, faBoxes, faGear, faHome, faLeaf, faList, faMessage, faTags, faUser } from "@fortawesome/free-solid-svg-icons"
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
        {icon: faList, label: "Danh mục", link: "/categories"},
        {icon: faBoxes, label: "Sản phẩm", link: "/product"},
        {icon: faTags, label: "Giảm giá", link: "/discount"},
        {icon: faGear, label: "Cá nhân", link: "/personal"},
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
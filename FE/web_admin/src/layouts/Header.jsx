import { faBars, faBell, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"


export const Header = () => {
    return (
        <>
            <header className="header">
                <div className="header__toggle">
                    <FontAwesomeIcon icon={faBars} />
                </div>
                <div className="header__tools">
                    <div className="header__search-bar">
                        <input 
                            type="text" 
                            placeholder="Tìm kiếm"
                        />
                        <FontAwesomeIcon icon={faMagnifyingGlass} />
                    </div>
                    <div className="header__notification">
                        <FontAwesomeIcon icon={faBell} />
                    </div>
                    <div className="header__avatar">
                        <img src="/public/vite.svg" alt="" />
                    </div>
                </div>
            </header>
        </>
    )
}
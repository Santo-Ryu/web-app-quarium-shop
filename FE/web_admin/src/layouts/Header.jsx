import { faBars, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"

export const Header = ({
    handleOpenSidebar
}) => {
    return (
        <>
            <header className="header">
                <div className="header__toggle" onClick={() => handleOpenSidebar()}>
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
                    <div className="header__avatar">
                        <img src="/public/vite.svg" alt="" />
                    </div>
                </div>
            </header>
        </>
    )
}
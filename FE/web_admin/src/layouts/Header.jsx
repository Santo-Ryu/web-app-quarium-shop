import { faBars, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { BASE_URL } from "../app/config/configApi"
import { useState } from "react"
import { useGetAccount } from "../hooks/useAdmin"

export const Header = ({
    handleOpenSidebar
}) => {
    const image = JSON.parse(localStorage.getItem('admin')).image.name
    const [searchInput, setSearchInput] = useState("")
    const {data, isLoading} = useGetAccount()
    return (
        <>
            <header className="header">
                <div className="header__toggle" onClick={() => handleOpenSidebar()}>
                    <FontAwesomeIcon icon={faBars} />
                </div>
                <div className="header__tools">
                    {/* <div className="header__search-box">
                        <div className="header__search-bar">
                            <input 
                                value={searchInput}
                                onChange={e => setSearchInput(e.target.value)}
                                type="text" 
                                placeholder="Tìm kiếm"
                            />
                            <FontAwesomeIcon icon={faMagnifyingGlass} />
                        </div>
                        <ul className="header__search-result">
                            <li className="header__search-item">123</li>
                        </ul>
                    </div> */}
                    <div className="header__avatar">
                        <img src={`${BASE_URL}api/public/image?name=${image != null ? image : 'user.png'}`} alt="Avtar" />
                    </div>
                </div>
            </header>
        </>
    )
}
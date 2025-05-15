import { useState } from "react"
import { Header } from "./Header"
import { Sidebar } from "./Sidebar"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const MainLayout = ({
    label,
    layout
}) => {
    const [openSidebar, setOpenSidebar] = useState(true);
    const handleOpenSidebar = () => {
        setOpenSidebar(prev => !prev)
    }

    return (
        <>
            <main className="main-layout">
                <Sidebar 
                    openSidebar={openSidebar}
                />
                <div className="main-layout__content">
                    {/* <Header 
                        handleOpenSidebar={handleOpenSidebar}
                    /> */}
                    <div className="main-layout__body">
                        <h1 className="main-layout__label"><FontAwesomeIcon icon={label.icon} />{label.text}</h1>
                        {layout}
                    </div>
                </div>
            </main>
        </>
    )
}
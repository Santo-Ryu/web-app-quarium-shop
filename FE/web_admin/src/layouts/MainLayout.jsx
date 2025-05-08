import { useState } from "react"
import { Header } from "./Header"
import { Sidebar } from "./Sidebar"

export const MainLayout = ({
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
                    <Header 
                        handleOpenSidebar={handleOpenSidebar}
                    />
                    {layout}
                </div>
            </main>
        </>
    )
}
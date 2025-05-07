import { Header } from "./Header"
import { Sidebar } from "./Sidebar"

export const MainLayout = ({
    layout
}) => {
    return (
        <>
            <div className="layout-container">
                <Sidebar />
                <div className="layout-main">
                    <Header />
                    {layout}
                </div>
            </div>
        </>
    )
}
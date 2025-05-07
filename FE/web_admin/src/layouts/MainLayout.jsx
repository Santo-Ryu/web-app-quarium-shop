import { Header } from "./Header"
import { Sidebar } from "./Sidebar"

export const MainLayout = ({
    layout
}) => {
    return (
        <>
            <div className="main-layout">
                <Sidebar />
                <div className="main-layout__content">
                    <Header />
                    {layout}
                </div>
            </div>
        </>
    )
}
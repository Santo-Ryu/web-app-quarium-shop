import { Header } from "./Header"
import { Sidebar } from "./Sidebar"

export const MainLayout = ({
    layout
}) => {
    return (
        <>
            <main className="layout-container">
                <Sidebar />
                <div className="layout-main">
                    <Header />
                    {layout}
                </div>
            </main>
        </>
    )
}
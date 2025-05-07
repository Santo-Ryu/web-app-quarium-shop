import { MainContent } from "../../layouts/MainContent";
import { MainLayout } from "../../layouts/MainLayout";
import { Sidebar } from "../../layouts/Sidebar";
import { DoashboardContent } from "./DashBoardContent";

export const Doashboard = () => {
    return (
        <>
            <Helmet><title>Trang chủ</title></Helmet>
            <MainLayout layout={<DoashboardContent/>} />
        </>
    )
}
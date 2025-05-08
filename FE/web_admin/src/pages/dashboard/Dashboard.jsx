import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DoashboardContent } from "./DashboardContent";

export const Doashboard = () => {
    return (
        <>
            <Helmet><title>Trang chủ</title></Helmet>
            <MainLayout layout={<DoashboardContent/>} />
        </>
    )
}
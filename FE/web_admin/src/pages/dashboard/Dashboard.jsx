import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DoashboardContent } from "./DashboardContent";

export const Doashboard = () => {
    const label = 'Trang chủ'
    return (
        <>
            <Helmet><title>{label}</title></Helmet>
            <MainLayout 
                layoutLabel={label}
                layout={<DoashboardContent/>} 
            />
        </>
    )
}
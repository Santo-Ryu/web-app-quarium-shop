import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { DoashboardContent } from "./DashboardContent";
import { faHome } from "@fortawesome/free-solid-svg-icons";

export const Doashboard = () => {
    const label = {text: 'Trang chủ', icon: faHome}
    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={<DoashboardContent/>} 
            />
        </>
    )
}
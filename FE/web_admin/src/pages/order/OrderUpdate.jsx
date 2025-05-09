import { useParams } from "react-router-dom"
import { faBoxArchive } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { OrderUpdateContent } from "./OrderUpdateContent";

export const OrderUpdate = () => {
    const id = useParams();
    const label = {text: 'Chỉnh sửa đơn hàng', icon: faBoxArchive}

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <OrderUpdateContent
                        id={id}    
                    />
                }
            />
        </>
    )
}
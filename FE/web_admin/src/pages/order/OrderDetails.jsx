import { useParams } from "react-router-dom"
import { OrderDetailsContent } from "./OrderDetailsContent"
import { faBoxArchive } from "@fortawesome/free-solid-svg-icons";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";

export const OrderDetails = () => {
    const id = useParams();
    const label = {text: 'Chi tiết đơn hàng', icon: faBoxArchive}

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <OrderDetailsContent
                        id={id}    
                    />
                }
            />
        </>
    )
}
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { CustomerDetailsContent } from "./CustomerDetailsContent";
import { useParams } from "react-router-dom";
import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";

export const CustomerDetails = () => {
    const id = useParams();
    const label = {text: 'Khách hàng', icon: faUser}

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <CustomerDetailsContent
                        id={id}
                    />
                }
            />
        </>
    )
}
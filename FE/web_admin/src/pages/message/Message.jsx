import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { MessageContent } from "./MessageContent";
import { faMessage } from "@fortawesome/free-solid-svg-icons";

export const Message = () => {
    const label = {text: 'Tin nhắn', icon: faMessage}
    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={<MessageContent />} 
            />
        </>
    )
}
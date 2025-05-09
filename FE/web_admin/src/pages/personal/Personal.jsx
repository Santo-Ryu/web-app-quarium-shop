import { Helmet } from "react-helmet-async"
import { MainLayout } from "../../layouts/MainLayout"
import { DetailsContent } from '../../components/DetailsContent';
import { faKey, faSignOut } from "@fortawesome/free-solid-svg-icons";
import { PersonalContent } from "./PersonalContent";

export const Personal = () => {
    const navigate = useNavigate();
    const [disabledInput, setDisabledInput] = useState(true)

    const label = {text: 'Khách hàng', icon: faUser}

    const handleDisabledInput = () => {
        setDisabledInput(prev => !prev)
    }

    const listButton = [
        {button: "Đổi mật khẩu", icon: faKey, onClick: null},
        {button: "Chỉnh sửa", icon: faGear, onClick: handleDisabledInput},
        {button: "Lưu", icon: faSave, onClick: null},
        {button: "Đăng xuất", icon: faSignOut, onClick: null},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout
                label={label}
                layout={
                    <DetailsContent 
                        listButton={listButton}
                        layout={<PersonalContent />}
                    />
                } 
            />
        </>
    )
}
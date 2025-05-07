/* eslint-disable no-unused-vars */
import { Helmet } from "react-helmet-async"

export const AuthForm = ({typeForm}) => {

    const titleType = (type) => {
        switch(type) {
            case "login": return "Đăng nhập";
            case "register": return "Đăng ký";
            case "forgot_password": return "Quên mật khẩu";
            default: log.console("Type is not valid")
        }
    }

    return(
        <>
            <Helmet>
                <title>{titleType(typeForm)}</title>
            </Helmet>

            <div className="auth-form-container">
                <h1 className="auth-form__title">{titleType(typeForm)}</h1>

            </div>
        </>
    )
}
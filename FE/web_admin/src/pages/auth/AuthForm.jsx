/* eslint-disable no-unused-vars */
import { Helmet } from "react-helmet-async"
import { TextFieldWithIcon } from "../../components/TextFieldWithIcon";
import { faEnvelope, faLeaf, faLock } from "@fortawesome/free-solid-svg-icons";
import { faFacebook, faGoogle, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";

export const AuthForm = ({
    type,
    authInfo,
    setAuthInfo
}) => {
    const [typeForm, setTypeForm] = useState(type)

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAuthInfo((prev) => ({...prev, [name]: value}));
    };

    const labelType = (type) => {
        switch(type) {
            case "login": return "Đăng nhập";
            case "register": return "Đăng ký";
            case "forgot_password": return "Quên mật khẩu";
            default: log.console("Type is not valid")
        }
    }

    const authSwitch = (type) => {
        switch(type) {
            case "login": 
                return  <p className="auth-form__redirect">
                            Chưa có tài khoản? <a className="auth-form__redirect-link" onClick={() => setTypeForm("register")} href="#">Đăng ký</a>
                        </p>;
            case "register": 
                return  <p className="auth-form__redirect">
                            Đã có tài khoản? <a className="auth-form__redirect-link" onClick={() => setTypeForm("login")} href="#">Đăng nhập</a>
                        </p>;
            case "forgot_password": 
                return  <p className="auth-form__redirect">
                            Bạn đã nhớ ra mật khẩu? <a className="auth-form__redirect-link" onClick={() => setTypeForm("login")} href="#">Đăng nhập</a>
                        </p>;
            default: log.console("Type is not valid")
        }
    }
    
    const listIcon = [
        { icon: faFacebook, color: "#1877F2" },  // Facebook xanh dương
        { icon: faGoogle, color: "#DB4437" },    // Google đỏ
        { icon: faTwitter, color: "#1DA1F2" },   // Twitter xanh nhạt
    ];

    return(
        <>
            <Helmet>
                <title>{labelType(typeForm)}</title>
            </Helmet>

            <div className="auth-form">
                <div className="auth-form__label">
                    <h1 className="auth-form__heading">{labelType(typeForm)}</h1>
                    <FontAwesomeIcon className="auth-form__icon" icon={faLeaf} />
                </div>

                <TextFieldWithIcon 
                    icon={faEnvelope} 
                    placeholderText={"Email"} 
                    value={authInfo.email}
                    onChange={handleChange}
                    name="email"
                />

                {(typeForm === "register" || typeForm === "login") &&
                    <TextFieldWithIcon 
                        icon={faLock} 
                        placeholderText={"Mật khẩu"} 
                        value={authInfo.password}
                        onChange={handleChange}
                        name="password"
                    />}

                {typeForm === "register" && 
                    <TextFieldWithIcon 
                        icon={faLock} 
                        placeholderText={"Xác nhận mật khẩu"} 
                        value={authInfo.confirmPassword}
                        onChange={handleChange}
                        name="confirmPassword"
                    />}

                {typeForm === "login" && <p className="auth-form__forgot-link"  onClick={() => setTypeForm("forgot_password")}>Quên mật khẩu?</p>}

                <button className="auth-form__button" type="submit">{labelType(typeForm)}</button>

                {authSwitch(typeForm)}

                <div className="auth-form__list-icon">
                    {listIcon.map((e, key) => (
                        <FontAwesomeIcon icon={e.icon} color={e.color} key={key} />
                    ))}
                </div>
            </div>
        </>
    )
}
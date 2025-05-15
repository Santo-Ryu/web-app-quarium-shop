/* eslint-disable no-unused-vars */
import { faFacebook, faGoogle, faTwitter } from "@fortawesome/free-brands-svg-icons";
import { faEnvelope, faLeaf, faLock } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react"
import { Helmet } from "react-helmet-async";
import { TextFieldWithIcon } from "/src/components/TextFieldWithIcon";
import { isValidEmail, isValidFieldNull, isValidPassword, isValidPasswordMatch } from "../../app/service/Validator";
import { encrypt, hashPassword } from "../../app/service/Encrypt";
import { login, register, resetPassword } from "../../app/api/auth.api";
import { useNavigate } from "react-router-dom";

export const AuthForm = ({
    type,
    authInfo,
    setAuthInfo
}) => {
    const navigate = useNavigate()
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

    const handleClick = async (typeForm) => {
        console.log("typeForm: ", typeForm)
        switch(typeForm) {
            case "register": {
                await handleRegister()
                break
            }
            case "login": {
                await handleLogin()
                break
            }
            case "forgot_password": {
                await handleResetPassword()
                break
            }
            default: "Type form is valid!"
        }
    }

    const handleRegister = async () => {
        if (!isValidFieldNull(authInfo.email) || !isValidFieldNull(authInfo.password) || !isValidFieldNull(authInfo.confirmPassword))
            return alert("Thông tin không được để trống!")
        if (!isValidEmail(authInfo.email)) return alert("Email không hợp lệ!")
        if (!isValidPassword(authInfo.password) || !isValidPassword(authInfo.confirmPassword)) 
            return alert("Mật khẩu phải > 6 ký tự")
        if (!isValidPasswordMatch(authInfo.password, authInfo.confirmPassword)) 
            return alert("Mật khẩu không trùng khớp!")

        const request = {
            "email": encrypt(authInfo.email),
            "password": hashPassword(authInfo.password),
            "role": encrypt("ADMIN")
        }
        const response = await register(request)
        if (response) {
            setAuthInfo({
                email: "",
                password: "",
                confirmPassword: ""
            })
            setTypeForm("login")
        }
    }

    const handleLogin = async () => {
        if (!isValidFieldNull(authInfo.email) || !isValidFieldNull(authInfo.password))
            return alert("Thông tin không được để trống!")
        
        const request = {
            "email": encrypt(authInfo.email),
            "password": hashPassword(authInfo.password),
            "role": encrypt("ADMIN")
        }
        const response = await login(request)
        if (response) {
            setAuthInfo({
                email: "",
                password: "",
                confirmPassword: ""
            })
            navigate("/")
        }
    }

    const handleResetPassword = async () => {
        if (!isValidFieldNull(authInfo.email))
            alert("Thông tin không được để trống!")
        
        const request = {
            "email": encrypt(authInfo.email),
            "role": encrypt("ADMIN")
        }
        const response = await resetPassword(request)        
        if (response) {
            setAuthInfo({
                email: "",
                password: "",
                confirmPassword: ""
            })
            setTypeForm("login")
        }
    }

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
                    type="email"
                />

                {(typeForm === "register" || typeForm === "login") &&
                    <TextFieldWithIcon 
                        icon={faLock} 
                        placeholderText={"Mật khẩu"} 
                        value={authInfo.password}
                        onChange={handleChange}
                        name="password"
                        type="password"
                    />}

                {typeForm === "register" && 
                    <TextFieldWithIcon 
                        icon={faLock} 
                        placeholderText={"Xác nhận mật khẩu"} 
                        value={authInfo.confirmPassword}
                        onChange={handleChange}
                        name="confirmPassword"
                        type="password"
                    />}

                {typeForm === "login" && <p className="auth-form__forgot-link"  onClick={() => setTypeForm("forgot_password")}>Quên mật khẩu?</p>}

                <button className="auth-form__button" onClick={() => handleClick(typeForm)} type="submit">{labelType(typeForm)}</button>

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
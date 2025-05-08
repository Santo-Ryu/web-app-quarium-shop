/* eslint-disable no-unused-vars */
import { useParams } from "react-router-dom"
import { AuthForm } from "./AuthForm"
import { useState } from "react"

export const Auth = () => {
    const { type } = useParams() // login, register or forgot_password
    const [authInfo, setAuthInfo] = useState({
        email: "",
        password: "",
        confirmPassword: ""
    })

    return(
        <>
            <AuthForm 
                type={type}
                authInfo={authInfo}
                setAuthInfo={setAuthInfo}
            />
        </>
    )
}
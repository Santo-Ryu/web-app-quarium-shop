/* eslint-disable no-unused-vars */
import { useParams } from "react-router-dom"
import { AuthForm } from "../../components/AuthForm"

export const Auth = () => {
    const { type } = useParams() // login, register or forgot_password

    return(
        <>
            <AuthForm typeForm={type} />
        </>
    )
}
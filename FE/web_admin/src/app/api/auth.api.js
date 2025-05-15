import axios from "axios";

import { BASE_URL } from "../config/configApi.jsx"

export const register = async (request) => {
    try {
        const URL = `${BASE_URL}api/auth/admin/register`
        const response = await axios.post(
            URL,
            request,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        if (response.status === 200 || response.status === 201) {
            alert("Đăng ký thành công!")
            return true
        }
    }catch(error) {
            alert("Đăng ký thất bại!")
        throw error;
    }
}

export const login = async (request) => {
    try {
        const URL = `${BASE_URL}api/auth/admin/login`
        const response = await axios.post(
            URL,
            request,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        if (response.status === 200) {
            alert("Đăng nhập thành công!")
            console.log(response.data)

            const data = response.data.data
            const token = data.token
            const admin = data.account.admin

            localStorage.setItem("token", token)
            localStorage.setItem("admin", JSON.stringify(admin))

            console.log('token',  localStorage.getItem("token"))
            console.log('admin',  JSON.parse(localStorage.getItem("admin")))

            return true
        }
    }catch(error) {
        alert("Đăng nhập thất bại")
        throw error;
    }
}

export const resetPassword = async (request) => {
    try {
        const URL = `${BASE_URL}api/auth/admin/password_reset`
        const response = await axios.post(
            URL,
            request,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        if (response.status === 200) {
            alert("Gửi mật khẩu thành công!")
            return true
        }
    }catch(error) {
        console.error('Error: ', error);
            alert("Gửi mật khẩu thất bại!")
        throw error;
    }
}
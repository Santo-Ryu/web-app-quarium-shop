import axios from "axios";
import { BASE_URL } from "../config/configApi.jsx"

export const getAccount = async () => {
    try {
        const token = localStorage.getItem('token')
        const admin = JSON.parse(localStorage.getItem('admin'))
        if (token && admin?.id) {
            const URL = `${BASE_URL}api/admin/get_account`
            const response = await axios.get(
                URL,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {id: admin.id}
                }
            );
    
            if (response.status === 200) {
               console.log(response.data.data)
               return response.data.data
            }
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const deleteCustomer = async (id) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/delete_customer`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {id: id}
                }
            );
    
            return response.status === 200
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const updateCustomer = async (customer) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/update_customer`
            const response = await axios.post(
                URL, customer,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            );
    
            return response.status === 200
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const updateImage = async (id, type, file) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/image/update_image`

            const formData = new FormData()
            formData.append('id', id)
            formData.append('type', type)
            formData.append('file', file)

            const response = await axios.post(
                URL, formData,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            );
    
            return response.status === 200
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}


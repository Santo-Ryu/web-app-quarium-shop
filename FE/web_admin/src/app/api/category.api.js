import axios from "axios";
import { BASE_URL } from "../config/configApi.jsx"

export const deleteCategory = async (id) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/delete_category`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {
                        id: id
                    }
                }
            );
    
            if (response.status === 200) {
               return true
            }
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const addCategory = async (categoryName) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/add_category`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {
                        categoryName: categoryName
                    }
                }
            );
    
            if (response.status === 200) {
               return true
            }else return false
        }
    }catch(error) {
        alert(error?.response?.data?.message)
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const updateCategory = async (id, newCategoryName) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/update_category`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {
                        id: id,
                        newCategoryName: newCategoryName
                    }
                }
            );
    
            if (response.status === 200) {
               return true
            }
            alert(response.data.message)
        }
    }catch(error) {
        console.log(error?.response?.data?.message);
        throw error;
    }
}

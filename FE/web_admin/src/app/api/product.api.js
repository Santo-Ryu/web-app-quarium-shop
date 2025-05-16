import axios from "axios";
import { BASE_URL } from "../config/configApi";

export const addNewProduct = async (formData) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/add_new_product`
            const response = await axios.post(
                URL, formData,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
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

export const deleteProduct = async (id) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/delete_product`
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
            }else return false
        }
    }catch(error) {
        alert(error?.response?.data?.message)
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const deleteProductImage = async (id) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/delete_product_image`
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
            }else return false
        }
    }catch(error) {
        alert(error?.response?.data?.message)
        console.log(error?.response?.data?.message);
        throw error;
    }
}

export const updateProduct= async (product) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/update_product`
            const response = await axios.post(
                URL, product,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
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

export const applyDiscount= async (list) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/apply_discount`
            const response = await axios.post(
                URL, list,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
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

export const destroyDiscount= async (list) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/destroy_discount`
            const response = await axios.post(
                URL, list,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
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
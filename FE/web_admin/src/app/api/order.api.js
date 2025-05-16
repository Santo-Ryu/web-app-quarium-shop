import axios from "axios";
import { BASE_URL } from "../config/configApi.jsx"

export const destroyOrder = async (orderId) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/destroy_order`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {
                        id: orderId
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


export const deleteProductInOrder = async (orderId, productId) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/delete_product_in_order`
            const response = await axios.post(
                URL, null,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: {
                        orderId: orderId,
                        productId: productId
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

export const updateOrder = async (request) => {
    try {
        const token = localStorage.getItem('token')
        if (token) {
            const URL = `${BASE_URL}api/admin/update_order`
            const response = await axios.post(
                URL, request,
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
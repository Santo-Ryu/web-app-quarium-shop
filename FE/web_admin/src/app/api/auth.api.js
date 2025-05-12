import axios from "axios";

import { BASE_URL } from "../config/configApi.jsx"

export const register = async (request) => {
    try {
        const URL = `${BASE_URL}api/auth/admin/register`
        console.log(URL)
        const response = await axios.post(
                URL,
                request,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );

            console.log("response: ", response)
    }catch(error) {
        console.error('Error: ', error);
        throw error;
    }
}
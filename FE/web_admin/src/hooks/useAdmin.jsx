import { getAccount } from "../app/api/admin.api";
import { useEffect, useState } from "react";

export const useGetAccount = () => {
    const [data, setData] = useState();
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = async () => {
        setIsLoading(true);
        try {
            const response = await getAccount();
            if (response) {
                setData(response);
            } else {
                setData(null);
            }
        } catch (err) {
            console.error("Failed to fetch customer:", err);
        } finally {
            setIsLoading(false);
        }
    };

    console.log(data)
    useEffect(() => {
        fetchData();
    }, []);

    return { data, isLoading, fetchData };
};

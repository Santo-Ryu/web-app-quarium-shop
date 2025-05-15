import { useEffect } from "react"

export const useTokenExpiration = () => {
    useEffect(() => {
        if (localStorage.getItem('token')) {
            const checkExpiration = () => {
                const token = localStorage.getItem('token')
                const tokenParts = token.split('.')
                const payload = JSON.parse(atob(tokenParts[1]));
                const exp = payload.exp;

                const expParseInt = parseInt(exp);

                const currentTime = Math.floor(Date.now() / 1000);
                
                const timeLeft = expParseInt - currentTime;

                // thời gian hiển thị H-m-s
                const hours = Math.floor(timeLeft / 3600); 
                const minutes = Math.floor((timeLeft % 3600) / 60);
                const seconds = timeLeft % 60;

                const formattedTimeLeft = `${hours}:${minutes}:${seconds}`;
                const expirationTime = new Date(parseInt(expParseInt) * 1000).toLocaleDateString();
                console.log('Checking expiration...');
                console.log('Expiration time:', expirationTime);
                console.log('Count time left: ', formattedTimeLeft);
    
                if (timeLeft <= 0) {
                    console.log('Token expired!');
                    localStorage.clear();
                    window.location.reload();
                }
            };
    
            checkExpiration();
    
            const interval = setInterval(checkExpiration, 2000);
            
            return () => {
                console.log('Cleaning up interval:', interval);
                clearInterval(interval);
            };
        }
    }, []);
}
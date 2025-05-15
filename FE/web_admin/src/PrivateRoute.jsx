/* eslint-disable react/prop-types */
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const isAuthenticated = localStorage.getItem('token');
  
  return isAuthenticated ? children : <Navigate to="/auth/login" />;
};

export default PrivateRoute;
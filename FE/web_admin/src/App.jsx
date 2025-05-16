import { Route, BrowserRouter as Router, Routes } from "react-router-dom"
import { Auth } from "./pages/auth/Auth"
import { HelmetProvider } from "react-helmet-async"
import { Doashboard } from "./pages/dashboard/Dashboard"
import { Message } from "./pages/message/Message"
import { OrderPage } from "./pages/order/OrderPage";
import { OrderDetails } from "./pages/order/OrderDetails"
import { OrderUpdate } from "./pages/order/OrderUpdate"
import { Customer } from "./pages/customer/Customer"
import { CustomerDetails } from "./pages/customer/CustomerDetails"
import { CustomerOrderHistory } from "./pages/customer/CustomerOrderHistory"
import { Product } from "./pages/product/Product"
import { ProductDetails } from "./pages/product/ProductDetails"
import { Personal } from "./pages/personal/Personal"
import { Discount } from "./pages/discount/Discount"
import { Categories } from "./pages/categories/Categories"
import PrivateRoute from './PrivateRoute'
import { useTokenExpiration } from './hooks/useTokenExpiration'
import { ProductAdd } from "./pages/product/ProductAdd"

function App() {
  useTokenExpiration()
  // localStorage.clear()
  return (
    <>
      <HelmetProvider>
        <Router>
          <Routes>
            
            <Route path="/auth/:type" element={<Auth />} />

            <Route path="/" element={
              <PrivateRoute><Doashboard /></PrivateRoute>
            } />

            <Route path="/message" element={
              <PrivateRoute><Message /></PrivateRoute>
            } />

            <Route path="/order" element={<PrivateRoute><OrderPage /></PrivateRoute>} />
            <Route path="/order-details/:id" element={<PrivateRoute><OrderDetails /></PrivateRoute>} /> {/* /order-details/:id */}
            <Route path="/order-update/:id" element={<PrivateRoute><OrderUpdate /></PrivateRoute>} /> {/* /order-update/:id */}

            <Route path="/customer" element={<PrivateRoute><Customer /></PrivateRoute>} />
            <Route path="/customer-details/:id" element={<PrivateRoute><CustomerDetails /></PrivateRoute>} /> {/* /customer-details/:id */}
            <Route path="/customer-order-history/:id" element={<PrivateRoute><CustomerOrderHistory /></PrivateRoute>} /> {/* /customer-order-history/:id */}

            <Route path="/categories" element={<PrivateRoute><Categories /></PrivateRoute>} />

            <Route path="/product" element={<PrivateRoute><Product /></PrivateRoute>} />
            <Route path="/product-details/:id" element={<PrivateRoute><ProductDetails /></PrivateRoute>} /> {/* /customer-details/:id */}
            <Route path="/product-add" element={<PrivateRoute><ProductAdd /></PrivateRoute>} />

            <Route path="/personal" element={<PrivateRoute><Personal /></PrivateRoute>} />

            <Route path="/discount" element={<PrivateRoute><Discount /></PrivateRoute>} />

          </Routes>
        </Router>
      </HelmetProvider>
    </>
  )
}

export default App

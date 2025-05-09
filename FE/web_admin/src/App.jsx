import { Route, BrowserRouter as Router, Routes } from "react-router-dom"
import { Auth } from "./pages/auth/Auth"
import { HelmetProvider } from "react-helmet-async"
import { Doashboard } from "./pages/dashboard/Dashboard"
import { Message } from "./pages/message/Message"
import { Order } from "./pages/order/order";
import { OrderDetails } from "./pages/order/OrderDetails"
import { OrderUpdate } from "./pages/order/OrderUpdate"
import { Customer } from "./pages/customer/Customer"
import { CustomerDetails } from "./pages/customer/CustomerDetails"
import { CustomerOrderHistory } from "./pages/customer/CustomerOrderHistory"
import { Product } from "./pages/product/Product"
import { ProductDetails } from "./pages/product/ProductDetails"
import { Personal } from "./pages/personal/Personal"
import { Discount } from "./pages/discount/Discount"

function App() {
  return (
    <>
      <HelmetProvider>
        <Router>
          <Routes>
            
            <Route path="/" element={<Doashboard />} />

            <Route path="/auth/:type" element={<Auth />} />

            <Route path="/message" element={<Message />} />

            <Route path="/order" element={<Order />} />
            <Route path="/order-details" element={<OrderDetails />} /> {/* /order-details/{id} */}
            <Route path="/order-update" element={<OrderUpdate />} /> {/* /order-update/{id} */}

            <Route path="/customer" element={<Customer />} />
            <Route path="/customer-details" element={<CustomerDetails />} /> {/* /customer-details/{id} */}
            <Route path="/customer-order-history" element={<CustomerOrderHistory />} /> {/* /customer-order-history/{id} */}

            <Route path="/product" element={<Product />} />
            <Route path="/product-details" element={<ProductDetails />} /> {/* /customer-details/{id} */}

            <Route path="/personal" element={<Personal />} />

            <Route path="/discount" element={<Discount />} />

          </Routes>
        </Router>
      </HelmetProvider>
    </>
  )
}

export default App

import { Route, BrowserRouter as Router, Routes } from "react-router-dom"
import { Auth } from "./pages/auth/Auth"
import { HelmetProvider } from "react-helmet-async"
import { Doashboard } from "./pages/dashboard/Dashboard"
import { Message } from "./pages/message/Message"
import { Order } from "./pages/order/order"
import { OrderDetails } from "./pages/order/OrderDetails"
import { OrderUpdate } from "./pages/order/OrderUpdate"
import { Customer } from "./pages/customer/Customer"
import { CustomerDetails } from "./pages/customer/CustomerDetails"

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
            <Route path="/customer-details" element={<CustomerDetails />} />

          </Routes>
        </Router>
      </HelmetProvider>
    </>
  )
}

export default App

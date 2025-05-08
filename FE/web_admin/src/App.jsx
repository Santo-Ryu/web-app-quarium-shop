import { Route, BrowserRouter as Router, Routes } from "react-router-dom"
import { Auth } from "./pages/auth/Auth"
import { HelmetProvider } from "react-helmet-async"
import { Doashboard } from "./pages/dashboard/Dashboard"
import { Message } from "./pages/message/Message"

function App() {
  return (
    <>
      <HelmetProvider>
        <Router>
          <Routes>
            <Route path="/auth/:type" element={<Auth />} />
            <Route path="/" element={<Doashboard />} />
            <Route path="/message" element={<Message />} />
          </Routes>
        </Router>
      </HelmetProvider>
    </>
  )
}

export default App

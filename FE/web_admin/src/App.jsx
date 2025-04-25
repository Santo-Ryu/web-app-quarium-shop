import { Route, BrowserRouter as Router, Routes } from "react-router-dom"
import { Auth } from "./pages/auth/Auth"
import { HelmetProvider } from "react-helmet-async"

function App() {
  return (
    <>
      <HelmetProvider>
        <Router>
          <Routes>
            <Route path="/auth/:type" element={<Auth />} />
          </Routes>
        </Router>
      </HelmetProvider>
    </>
  )
}

export default App

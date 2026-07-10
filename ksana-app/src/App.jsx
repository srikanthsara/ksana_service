import { BrowserRouter, Routes, Route } from "react-router-dom";

import ProductPage from "./pages/ProductPage";
import CartPage from "./pages/CartPage";
import OrderPage from "./pages/OrderPage";
import OrderSuccessPage from "./pages/OrderSuccessPage";
import LoginPage from "./pages/LoginPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import LogoutPage from "./pages/LogoutPage";

function App() {

    const token = localStorage.getItem("token");

    return (

        <BrowserRouter>
            <Header />
            <Routes>

                <Route
                    path="/login"
                    element={<LoginPage />}
                />


                <Route
                    path="/"
                    element={<ProductPage />}
                />

                <Route
                    path="/cart"
                    element={<CartPage />}
                />

                <Route
                    path="/orders"
                    element={<OrderPage />}
                />

                <Route
                    path="/order-success"
                    element={<OrderSuccessPage />}
                />

                <Route
                    path="/logout"
                    element={<LogoutPage />}
                />
                
            </Routes>
            <Footer />
        </BrowserRouter >
    );
}

export default App;
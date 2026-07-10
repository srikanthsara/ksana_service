import { useLocation, useNavigate } from "react-router-dom";

export default function OrderSuccessPage() {

    const navigate = useNavigate();

    const { state } = useLocation();

    return (

        <div className="container mt-5 text-center">

            <div className="card shadow p-5">

                <h1 className="text-success">
                    ✅ Order Confirmed
                </h1>

                <hr />

                <h4>
                    Order Id :
                    {state?.orderId}
                </h4>

                <h4>
                    Amount :
                    ₹ {state?.totalAmount}
                </h4>

                <p className="mt-3">

                    Thank you for shopping
                    with Ksana Grocery.

                </p>

                <button
                    className="btn btn-primary me-2"
                    onClick={() => navigate("/orders")}
                >
                    My Orders
                </button>

                <button
                    className="btn btn-success"
                    onClick={() => navigate("/")}
                >
                    Continue Shopping
                </button>

            </div>

        </div>
    );
}
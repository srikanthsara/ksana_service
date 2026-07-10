import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import API_BASE_URL from "../config/api";

export default function CartPage() {


    const navigate = useNavigate();

    const [cart, setCart] = useState(null);

    const [loading, setLoading] = useState(false);

    const [paymentProvider, setPaymentProvider] =
        useState("GooglePay");

    const [paymentType, setPaymentType] =
        useState("UPI");

    useEffect(() => {

        loadCart();

    }, []);

    const loadCart = () => {

        axios.get(
            `${API_BASE_URL}/cart/${localStorage.getItem("customerId")}`
        )
            .then(response => {

                setCart(response.data);

            })
            .catch(error => {

                console.error(error);

            });
    };

    const placeOrder = () => {

        if (loading) {
            return;
        }

        setLoading(true);

        const request = {

            customerId: "CUST1001",

            customerName: "Srikanth",

            customerEmail: "srikanth@gmail.com",

            paymentProvider: paymentProvider,

            paymentType: paymentType

        };

        axios.post(
            `${API_BASE_URL}/orders/checkout`,
            request
        )
            .then(response => {

                toast.success(
                    `Order Created Successfully. Order Id: ${response.data.orderId}`
                );

                setTimeout(() => {

                    navigate("/orders");

                }, 2000);
            })
            .catch(error => {

                console.error(error);

                toast.error("Order Creation Failed");

            })
            .finally(() => {

                setLoading(false);

            });
    };

    const updateQuantity = (
        cartItemId,
        quantity
    ) => {

        if (quantity < 1) {
            return;
        }

        axios.put(
            `${API_BASE_URL}/cart/item/${cartItemId}?quantity=${quantity}`
        )
            .then(response => {

                setCart(response.data);

            })
            .catch(error => {

                console.error(error);

                alert("Failed To Update Quantity");

            });
    };

    const removeItem = (
        cartItemId
    ) => {

        axios.delete(
            `${API_BASE_URL}/cart/item/${cartItemId}`
        )
            .then(() => {

                loadCart();

            })
            .catch(error => {

                console.error(error);

                alert("Failed To Remove Item");

            });
    };

    if (!cart) {

        return <h2>Loading...</h2>;
    }

    return (
        
            <div className="container mt-4">

                <h2>My Cart</h2>

                <table className="table table-bordered">

                    <thead>

                        <tr>

                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>GST</th>
                            <th>Total</th>
                            <th>Remove</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            cart.cartItems.map(item => (

                                <tr key={item.cartItemId}>

                                    <td>
                                        {item.productName}
                                    </td>

                                    <td>

                                        <button
                                            className="btn btn-danger btn-sm"
                                            onClick={() =>
                                                updateQuantity(
                                                    item.cartItemId,
                                                    item.quantity - 1
                                                )
                                            }
                                        >
                                            -
                                        </button>

                                        <span className="mx-3">
                                            {item.quantity}
                                        </span>

                                        <button
                                            className="btn btn-success btn-sm"
                                            onClick={() =>
                                                updateQuantity(
                                                    item.cartItemId,
                                                    item.quantity + 1
                                                )
                                            }
                                        >
                                            +
                                        </button>

                                    </td>

                                    <td>
                                        ₹ {item.unitPrice}
                                    </td>

                                    <td>
                                        ₹ {item.gstAmount}
                                    </td>

                                    <td>
                                        ₹{
                                            (
                                                Number(item.totalPrice)
                                                +
                                                Number(item.gstAmount)
                                            ).toFixed(2)
                                        }
                                    </td>

                                    <td>

                                        <button
                                            className="btn btn-danger btn-sm"
                                            onClick={() =>
                                                removeItem(
                                                    item.cartItemId
                                                )
                                            }
                                        >
                                            Remove
                                        </button>

                                    </td>

                                </tr>

                            ))

                        }

                    </tbody>

                </table>

                <h3>
                    Grand Total : ₹ {cart.totalAmount}
                </h3>

                <div className="mb-3">

                    <label className="form-label">
                        Payment Provider
                    </label>

                    <select
                        className="form-select"
                        value={paymentProvider}
                        onChange={(e) =>
                            setPaymentProvider(
                                e.target.value
                            )
                        }
                    >

                        <option value="GooglePay">
                            GooglePay
                        </option>

                        <option value="PhonePe">
                            PhonePe
                        </option>

                        <option value="Paytm">
                            Paytm
                        </option>

                    </select>

                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Payment Type
                    </label>

                    <select
                        className="form-select"
                        value={paymentType}
                        onChange={(e) =>
                            setPaymentType(
                                e.target.value
                            )
                        }
                    >

                        <option value="UPI">
                            UPI
                        </option>

                        <option value="CARD">
                            CARD
                        </option>

                        <option value="NET_BANKING">
                            NET BANKING
                        </option>

                    </select>

                </div>

                <button
                    className="btn btn-success"
                    disabled={loading}
                    onClick={placeOrder}
                >
                    {
                        loading
                            ? "Processing..."
                            : "Checkout"
                    }
                </button>

            </div>
            
        
    );
}


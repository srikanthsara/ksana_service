import { useEffect, useState } from "react";
import axios from "axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import API_BASE_URL from "../config/api";

export default function OrderPage() {

    const [orders, setOrders] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);


    useEffect(() => {

        loadOrders();

    }, [page]);

    const loadOrders = () => {

        axios.get(
                `${API_BASE_URL}/orders/customer/${localStorage.getItem("customerId")}?page=${page}&size=5`
            )
            .then(response => {

                setOrders(
                    response.data.content
                );

                setTotalPages(
                    response.data.totalPages
                );

            })
            .catch(error => {

                console.error(error);

            });
    };

    return (
            
            <div className="container mt-4">

                <h2>My Orders</h2>

                <table className="table table-bordered">

                    <thead>

                        <tr>

                            <th>Order Id</th>

                            <th>Customer</th>

                            <th>Total Amount</th>

                            <th>Status</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            orders.map(order => (

                                <tr key={order.orderId}>

                                    <td>{order.orderId}</td>

                                    <td>{order.customerName}</td>

                                    <td>
                                        ₹ {order.totalAmount}
                                    </td>

                                    <td>
                                        {order.orderStatus}
                                    </td>

                                </tr>

                            ))

                        }

                    </tbody>

                </table>
                <div className="mt-4 text-center">

                    <button
                        className="btn btn-secondary me-2"
                        disabled={page === 0}
                        onClick={() =>
                            setPage(page - 1)
                        }
                    >
                        Previous
                    </button>

                    <span>

                        Page {page + 1}
                        of
                        {totalPages}

                    </span>

                    <button
                        className="btn btn-secondary ms-2"
                        disabled={
                            page + 1 >= totalPages
                        }
                        onClick={() =>
                            setPage(page + 1)
                        }
                    >
                        Next
                    </button>

                </div>

            </div>
            
        
    );
}
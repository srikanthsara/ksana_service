export default function OrderCard({
    order
}) {

    return (

        <tr>

            <td>
                {order.orderId}
            </td>

            <td>
                {order.customerName}
            </td>

            <td>
                ₹ {order.totalAmount}
            </td>

            <td>

                <span
                    className={
                        order.orderStatus ===
                        "PAYMENT_COMPLETED"

                            ? "badge bg-success"

                            : "badge bg-danger"
                    }
                >

                    {order.orderStatus}

                </span>

            </td>

        </tr>
    );
}
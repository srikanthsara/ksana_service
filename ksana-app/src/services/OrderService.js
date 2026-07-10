import axios from "axios";
import API_BASE_URL from "../config/api";


const ORDER_URL =
    "${API_BASE_URL}/orders";

class OrderService {

    checkout(
        request
    ) {

        return axios.post(
            '${ORDER_URL}/checkout',
            request
        );
    }

    getOrders(
        customerId
    ) {

        return axios.get(
            '${ORDER_URL}/customer/${customerId}'
        );
    }

    getOrderById(
        orderId
    ) {

        return axios.get(
            '${ORDER_URL}/${orderId}'
        );
    }
}

export default new OrderService();
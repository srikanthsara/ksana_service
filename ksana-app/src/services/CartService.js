import axios from "axios";
import API_BASE_URL from "../config/api";


const CART_URL =
    "${API_BASE_URL}/cart";

class CartService {

    getCart(customerId) {

        return axios.get(
            '${CART_URL}/${customerId}'
        );
    }

    addToCart(request) {

        return axios.post(
            '${CART_URL}/add',
            request
        );
    }

    updateQuantity(
        cartItemId,
        quantity
    ) {

        return axios.put(
            '${CART_URL}/item/${cartItemId}?quantity=${quantity}'
        );
    }

    removeItem(
        cartItemId
    ) {

        return axios.delete(
            '${CART_URL}/item/${cartItemId}'
        );
    }
}

export default new CartService();
import axios from "axios";
import API_BASE_URL from "../config/api";



const PRODUCT_URL = "${API_BASE_URL}/products";

class ProductService {

    getAllProducts() {

        return axios.get(PRODUCT_URL);
    }

 searchProducts(name) {

    return axios.get(
        "${API_BASE_URL}/products/search",
        {
            params: {
                name: name
            }
        }
    );
}
    getProductById(productId) {

        return axios.get(
            '${PRODUCT_URL}/${productId}'
        );
    }
}

export default new ProductService();
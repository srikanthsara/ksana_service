import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Header from "../components/Header";
import Footer from "../components/Footer";
import ProductService from "../services/ProductService";
import API_BASE_URL from "../config/api";



export default function ProductPage() {

    const [products, setProducts] = useState([]);
    const [searchKeyword, setSearchKeyword] = useState("");
    const navigate = useNavigate();

    useEffect(() => {

        ProductService.getAllProducts()

            .then(response => {

                setProducts(
                    response.data
                );

            });

    }, []);


    const loadProducts = () => {

        ProductService.getAllProducts()
            .then(response => {

                setProducts(
                    response.data
                );

            });
    };

    const searchProducts = () => {

        if (
            searchKeyword.trim() === ""
        ) {

            loadProducts();

            return;
        }

        ProductService.searchProducts(
            searchKeyword
        )
            .then(response => {

                setProducts(
                    response.data
                );

            })
            .catch(error => {

                console.error(error);

            });
    };

    const addToCart = (product) => {

        const payload = {

            customerId:
                localStorage.getItem(
                    "customerId"
                ),

            customerName:
                localStorage.getItem(
                    "username"
                ),

            productId:
                product.productId,

            quantity: 1
        };

        axios
            .post(
                `${API_BASE_URL}/cart/add`,
                payload
            )
            .then(response => {

                navigate("/cart");

            })
            .catch(error => {

                console.error(error);

                alert("Failed To Add Product");
            });
    };

    return (
        
            
            <div className="container mt-4">
                <div className="row mb-3">

                    <div className="col-md-8">

                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search Product..."
                            value={searchKeyword}
                            onChange={(e) => {

                                const value = e.target.value;
                                setSearchKeyword(value);

                                if (value.trim().length >= 2) {

                                    ProductService
                                        .searchProducts(value)
                                        .then(response => {

                                            setProducts(
                                                response.data
                                            );

                                        });
                                }
                            }}
                        />

                    </div>

                    <div className="col-md-2">

                        <button
                            className="btn btn-primary w-100"
                            onClick={searchProducts}
                        >
                            Search
                        </button>

                    </div>

                    <div className="col-md-2">

                        <button
                            className="btn btn-secondary w-100"
                            onClick={loadProducts}
                        >
                            Reset
                        </button>

                    </div>

                </div>
                <h2>Grocery Products</h2>

                <table className="table table-bordered table-striped">

                    <thead>

                        <tr>

                            <th>Product Id</th>

                            <th>Product Name</th>

                            <th>Brand</th>

                            <th>Price</th>

                            <th>GST %</th>

                            <th>Stock</th>

                            <th>Action</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            products.map(product => (

                                <tr key={product.productId}>

                                    <td>{product.productId}</td>

                                    <td>{product.productName}</td>

                                    <td>{product.brand}</td>

                                    <td>₹ {product.price}</td>

                                    <td>{product.gstPercentage}%</td>

                                    <td>{product.availableQuantity}</td>

                                    <td>

                                        <button
                                            className="btn btn-primary"
                                            onClick={() => addToCart(product)}
                                        >

                                            Add To Cart

                                        </button>

                                    </td>

                                </tr>

                            ))
                        }

                    </tbody>

                </table>

            </div>

          
    );
}
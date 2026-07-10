import { Link } from "react-router-dom";

export default function Header() {


    const logout = () => {
        localStorage.clear();
        window.location.href = "/login";
    };


    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-success">

            <div className="container">

                <Link
                    className="navbar-brand"
                    to="/"
                >
                    Ksana Grocery
                </Link>

                

                    <ul className="navbar-nav ms-auto">

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/"
                            >
                                Products
                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/cart"
                            >
                                Cart
                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/orders"
                            >
                                Orders
                            </Link>

                        </li>

                        <li className="nav-item ms-3">

                        <button
                            className="btn btn-danger"
                            onClick={logout}
                        >
                            Logout
                        </button>

                    </li>

                    </ul>

                </div>

            

        </nav>
    );
}
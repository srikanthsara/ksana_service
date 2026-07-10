import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";
import API_BASE_URL from "../config/api";


export default function LoginPage() {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");

    const [password, setPassword] = useState("");

    const [error, setError] = useState("");

    const [loading, setLoading] = useState(false);

    useEffect(() => {

        const token =
            localStorage.getItem("token");

        if (token) {

            navigate("/");
        }

    }, []);

    const login = () => {

        const request = {

            username: username,

            password: password
        };
        if (
            !username.trim() ||
            !password.trim()
        ) {

            setError(
                "Username and Password are required"
            );

            return;
        }
        setLoading(true);
        axios.post(
            `${API_BASE_URL}/auth/login`,
            request
        )
            .then(response => {
                if (response.data.token) {

                    localStorage.setItem(
                        "token",
                        response.data.token
                    );

                    localStorage.setItem(
                        "username",
                        response.data.username
                    );

                    localStorage.setItem(
                        "role",
                        response.data.role
                    );
                    
                    localStorage.setItem(
                        "customerId",
                        response.data.customerId
                    );

                    navigate("/");
                }
            })
            .catch(error => {

                console.error(error);

                setError(
                    "Invalid Username or Password"
                );
            }).finally(() => {

                setLoading(false);

            });
    };

    return (

        <div
            className="container"
            style={{
                maxWidth: "400px",
                marginTop: "100px"
            }}
        >

            <div className="card shadow">

                <div className="card-body">

                    <h2
                        className="text-center mb-4"
                    >
                        Ksana Login
                    </h2>

                    {

                        error &&

                        <div
                            className="alert alert-danger"
                        >
                            {error}
                        </div>
                    }



                    <form
                        onSubmit={(e) => {

                            e.preventDefault();

                            login();
                        }}
                    >

                        <div className="mb-3">

                            <label>Username</label>

                            <input
                                type="text"
                                className="form-control"
                                value={username}
                                onChange={(e) => {

                                    setUsername(e.target.value);
                                    setError("");

                                }}
                            />

                        </div>

                        <div className="mb-3">

                            <label>Password</label>
                            <input
                                type="password"
                                className="form-control"
                                value={password}
                                onChange={(e) => {

                                    setPassword(e.target.value);
                                    setError("");

                                }}
                            />

                        </div>


                        <button
                                type="submit"
                                className="btn btn-success w-100"
                                disabled={loading}
                            >
                            {
                                loading
                                    ? "Logging In..."
                                    : "Login"
                            }
                        </button>
                    </form>
                </div>

            </div>

        </div>
    );
}
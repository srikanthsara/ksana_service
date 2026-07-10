import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function LogoutPage() {

    const navigate = useNavigate();

    useEffect(() => {

        localStorage.clear();

        navigate("/login");

    }, [navigate]);

    return <h3>Logging Out...</h3>;
}
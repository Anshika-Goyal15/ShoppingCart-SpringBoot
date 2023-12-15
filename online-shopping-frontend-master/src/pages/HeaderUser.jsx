import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useEffect, useState } from "react";
import axios from "axios";

const HeaderUser = () => {
  let navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem("active-user"));
  const[cartItems, setCartItems] = useState("");

  useEffect(() => {
    const getMyCart = async () => {
      const myCart = await retrieveMyCart();
      if(myCart){
        setCartItems(myCart.cartData.length);
      }
    };

    getMyCart();
  }, []);

  const retrieveMyCart = async () => {
    const response = await axios.get(
      "http://localhost:5050/api/user/mycart?userId=" + user.id
    );
    console.log("qwerty",response.data);
    return response.data;
  };

  const userLogout = () => {
    toast.success("logged out!!!", {
      position: "top-center",
      autoClose: 1000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
    sessionStorage.removeItem("active-user");
    navigate("/home");
    window.location.reload(true);
  };

  return (
    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 me-5">
      <li class="nav-item">
        <Link to="/user/mycart" class="nav-link active" aria-current="page">
          <b className="text-color">My Cart {cartItems}</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link to="/user/myorder" class="nav-link active" aria-current="page">
          <b className="text-color">My Order</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link
          to=""
          class="nav-link active"
          aria-current="page"
          onClick={userLogout}
        >
          <b className="text-color">Logout</b>
        </Link>
        <ToastContainer />
      </li>
    </ul>
  );
};

export default HeaderUser;

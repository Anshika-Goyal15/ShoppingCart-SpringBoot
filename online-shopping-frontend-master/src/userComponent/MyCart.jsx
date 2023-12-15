import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";

const MyCart = () => {
  const navigate = useNavigate();
  const updateProductInCart = (cartId, productId, newQuantity) => {
    const updateRequest = {
      cartId : cartId,
      productId : productId,
      quantity : newQuantity,
      userId : user.id,
    };
    axios.post("http://localhost:5050/api/user/mycart/update", updateRequest)
      .then((response) => {
        console.log("Product in cart updated successfully qwerty:", response);
        alert("Product quantity updated !!")
        // Refresh the cart data after updating
        retrieveMyCart();
      })
      .catch((error) => {
        console.error("Error updating product in cart:", error);
        alert("Certain quantities left !!")
        window.location.reload();
      });
  };
  const handleQuantityChange = (cartId, productId, newQuantity) => {
    setMyCartData((prevCartData) => {
      const updatedCartData = prevCartData.map((cartData) =>
        cartData.cartId === cartId
          ? { ...cartData, quantity: newQuantity }
          : cartData
      );
      return updatedCartData;
     });
    // updateProductInCart(cartId, productId, newQuantity);
  };
  const handleUpdateClick = (cartId, productId, quantity) => {
    updateProductInCart(cartId, productId, quantity);
    //alert("Product quantity updated successfully!!")
  };

  const user = JSON.parse(sessionStorage.getItem("active-user"));
  const [totatPrice, setTotalPrice] = useState("");
  const [myCartData, setMyCartData] = useState([]);

  useEffect(() => {
    const getMyCart = async () => {
      const myCart = await retrieveMyCart();
      if (myCart) {
        console.log("cart data is present :)");
        console.log(myCart.totalCartPrice);
        console.log(myCart.cartData);
        setTotalPrice(myCart.totalCartPrice);
        setMyCartData(myCart.cartData);
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

  const deleteProductFromCart = (cartId, e) => {
    const response = axios.get(
      "http://localhost:5050/api/user/mycart/remove?cartId=" + cartId
    );

    console.log(response);
  };

  const checkout = (e) => {
    e.preventDefault();
    console.log("CHECKOUT PAGE REQUEST");
    navigate("/user/order/payment", { state: { priceToPay: totatPrice } });
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 custom-bg border-color"
        style={{
          height: "45rem",
        }}
      >
        <div className="card-header text-center bg-color custom-bg-text">
          <h2>My Cart</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover custom-bg-text text-center">
              <thead className="bg-color table-bordered border-color">
                <tr>
                  <th scope="col">Product</th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Quantity</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody className="text-color">
                {myCartData.map((cartData) => {
                  return (
                    <tr key={cartData.cartId} >
                      <td>
                        <img
                          src={
                            "http://localhost:5050/api/product/" +
                            cartData.productImage
                          }
                          class="img-fluid"
                          alt="product_pic"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{cartData.productName}</b>
                      </td>
                      <td>
                        <b>{cartData.productDescription}</b>
                      </td>
                      <td>
                        <b>{cartData.quantity}</b>
                      </td>
                      <td>
                      <input
                        type="number"
                        min="1"
                        value={cartData.quantity}
                        onChange={(e) => handleQuantityChange(cartData.cartId, cartData.productId, e.target.value)}
                      />
                      </td>
                      <td>
                        <button
                          className="btn btn-primary"
                          onClick={() => handleUpdateClick(cartData.cartId, cartData.productId, cartData.quantity)}
                        >
                          Update
                        </button>
                      </td>
                      <td>
                        <button
                          className="btn btn-danger custom-bg-text btn-sm"
                          onClick={() => deleteProductFromCart(cartData.cartId)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
        <div className="card-footer custom-bg">
          <div className="float-right">
            <div
              className="text-color me-2"
              style={{
                textAlign: "right",
              }}
            >
              <h5>Total Price: &#8377; {totatPrice}/-</h5>
            </div>

            <div className="float-end me-2">
              <button
                type="submit"
                className="btn bg-color custom-bg-text mb-3"
                onClick={checkout}
              >
                Checkout
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyCart;

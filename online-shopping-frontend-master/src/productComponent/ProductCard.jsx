import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link} from "react-router-dom";
import CategoryNavigator from "./CategoryNavigator";
import axios from "axios";

const ProductCard = (product) => {
  const navigate = useNavigate();

  console.log("swdgeuihfc", product.item);
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
  
  const handleEdit = () => {
    navigate("/editproduct",{state:product.item});
  };

  const deleteProduct= (productId, e) => {
    const response = axios.get(
      "http://localhost:5050/api/product/remove?productId=" + productId
    );

    console.log(response);
  };
  
   return (
    <div className="col">
    <div class="card border-color rounded-card card-hover product-card custom-bg h-100">
      <img
        src={"http://localhost:5050/api/product/" + product.item.imageName}
        class="card-img-top rounded mx-auto d-block m-2"
        alt="img"
        style={{
          maxHeight: "270px",
          maxWidth: "100%",
          width: "auto",
        }}
      />

      <div class="card-body text-color">
        <h5 class="card-title d-flex justify-content-between">
          <div>
            <b>{product.item.title}</b>
          </div>
          <CategoryNavigator
            item={{
              id: product.item.category.id,
              title: product.item.category.title,
            }}
          />
        </h5>
        <p className="card-text">
          <b>{product.item.description}</b>
        </p>
      </div>
      <div class="card-footer">
        <div className="text-center text-color">
          <p>
            <span>
              <h4>Price : &#8377;{product.item.price}</h4>
            </span>
          </p>
        </div>
        <div className="d-flex justify-content-between">
          {admin ==null &&
          <Link
            to={`/product/${product.item.id}/category/${product.item.category.id}`}
            className="btn bg-color custom-bg-text"
          >
            Add to Cart
          </Link>
}
            {admin!=null && 
            <button
                className="btn btn-primary custom-bg-text btn-sm ml-2"
                onClick={handleEdit}
              >
                Edit
            </button>
}
           {admin!=null && 
          <button
            className="btn btn-danger custom-bg-text btn-sm"

            onClick={() => deleteProduct(product.item.id)}
          >
            Delete
          </button>
          }
          {admin != null &&
          <p class="text-color">
            <b>
              <i>Stock :</i> {product.item.quantity}
            </b>
          </p>
}
        </div>
      </div>
    </div>
    </div>
  );
};

export default ProductCard;

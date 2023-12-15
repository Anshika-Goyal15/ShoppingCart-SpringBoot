import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import axios from "axios";

const EditProduct = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const data = location.state;
    console.log("12",data);

    const [editedProduct, setEditedProduct] = useState({
        title: data.title,
        description: data.description,
        price: data.price,
        quantity: data.quantity,

    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditedProduct((prevProduct) => ({
          ...prevProduct,
          [name]: value,
        }));
      };

      const handleUpdate = () => {
        // Send a request to update the product on the backend
        axios.post(`http://localhost:5050/api/product/update/${data.id}`, editedProduct)
          .then((response) => {
            alert("Product updated successfully");
          })
          .catch((error) => {
            console.error("Error updating product:", error);
          });
          navigate("/home");
      };


  return (
    <div>
      <div class="mt-2 d-flex aligns-items-center justify-content-center">
        <div
          class="card form-card border-color custom-bg"
          style={{ width: "25rem" }}
        >
          <div className="card-header bg-color custom-bg-text text-center">
            <h5 class="card-title">Edit Product</h5>
          </div>
          <div class="card-body text-color">
            <form>
              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Product Title</b>
                </label>
                <input
                  type="text"
                  class="form-control"
                  id="title"
                  name="title"
                  onChange={handleInputChange}
                  value={editedProduct.title}
                />
              </div>
              <div class="mb-3">
                <label for="description" class="form-label">
                  <b>Product Description</b>
                </label>
                <textarea
                  class="form-control"
                  id="description"
                  name="description"
                  rows="3"
                  onChange={handleInputChange}
                  value={editedProduct.description}
                />
              </div>

              <div class="mb-3 mt-1">
                <label for="quantity" class="form-label">
                  <b>Product Quantity</b>
                </label>
                <input
                  type="number"
                  class="form-control"
                  id="quantity"
                  name="quantity"
                  onChange={handleInputChange}
                  value={editedProduct.quantity}
                />
              </div>

              <div class="mb-3">
                <label for="price" class="form-label">
                  <b>Product Price</b>
                </label>
                <input
                  type="number"
                  class="form-control"
                  id="price"
                  name="price"
                  onChange={handleInputChange}
                  value={editedProduct.price}
                />
              </div>

              <button
                type="submit"
                class="btn bg-color custom-bg-text"
                onClick={handleUpdate}
              >
                Update Product
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditProduct;

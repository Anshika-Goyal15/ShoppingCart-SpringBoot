package com.onlineshopping.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshopping.dao.CartDao;
import com.onlineshopping.dao.ProductDao;
import com.onlineshopping.dao.UserDao;
import com.onlineshopping.dto.AddToCartRequest;
import com.onlineshopping.dto.CartDataResponse;
import com.onlineshopping.dto.CartResponse;
import com.onlineshopping.model.Cart;
import com.onlineshopping.model.Product;
import com.onlineshopping.model.User;

@RestController
@RequestMapping("api/user/")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@PostMapping("cart/add")
	public ResponseEntity add(@RequestBody AddToCartRequest addToCartRequest) {
	    System.out.println("request came for ADD PRODUCT TO CART");
	    System.out.println(addToCartRequest);

	    Optional<User> optionalUser = userDao.findById(addToCartRequest.getUserId());
	    User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

	    Optional<Product> optionalProduct = productDao.findById(addToCartRequest.getProductId());
	    Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found"));

	    int requestedQuantity = addToCartRequest.getQuantity();
	    int availableStock = product.getQuantity();

	    if (requestedQuantity > availableStock) {
	        return new ResponseEntity("Insufficient stock for the requested quantity", HttpStatus.BAD_REQUEST);
	    }

	    // Decrease the stock quantity and save the product
	    product.setQuantity(availableStock - requestedQuantity);
	    productDao.save(product);

	    // Add the product to the cart
	    Cart cart = new Cart();
	    cart.setProduct(product);
	    cart.setQuantity(requestedQuantity);
	    cart.setUser(user);
	    cartDao.save(cart);

	    return new ResponseEntity(HttpStatus.OK);
	}

	
	@GetMapping("mycart")
	public ResponseEntity getMyCart(@RequestParam("userId") String userId) throws JsonProcessingException {
		
		System.out.println("request came for MY CART for USER ID : "+userId);
		
		List<CartDataResponse> cartDatas = new ArrayList<>();
		
		List<Cart> userCarts = cartDao.findByUser_id(userId);
		
		double totalCartPrice = 0;
		
		for (Cart cart : userCarts) {
			CartDataResponse cartData = new CartDataResponse();
			cartData.setCartId(cart.getId());
			cartData.setProductDescription(cart.getProduct().getDescription());
			cartData.setProductName(cart.getProduct().getTitle());
			cartData.setProductImage(cart.getProduct().getImageName());
			cartData.setQuantity(cart.getQuantity());
			cartData.setProductId(cart.getProduct().getId());
			
			cartDatas.add(cartData);
			
			double productPrice = Double.parseDouble(cart.getProduct().getPrice().toString());
			
			totalCartPrice =  totalCartPrice + (cart.getQuantity() * productPrice);
			
		}
		
		CartResponse cartResponse = new CartResponse();
		cartResponse.setTotalCartPrice(String.valueOf(totalCartPrice));
		cartResponse.setCartData(cartDatas);
		
		String json = objectMapper.writeValueAsString(cartResponse);
		
		System.out.println(json);
		
		return new ResponseEntity(cartResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("mycart/remove")
	public ResponseEntity removeCartItem(@RequestParam("cartId") String cartId) throws JsonProcessingException {
		
		System.out.println("request came for DELETE CART ITEM WHOSE ID IS : "+cartId);
		
		Optional<Cart> optionalCart = this.cartDao.findById(cartId);
		Cart cart = new Cart();
		
		if(optionalCart.isPresent()) {
			cart = optionalCart.get();
		}
		
		this.cartDao.delete(cart);
		
		return new ResponseEntity("SUCCESS", HttpStatus.OK);
		
	}
	@PostMapping("mycart/update")
    public ResponseEntity updateCartItem(@RequestBody AddToCartRequest addToCartRequest) {
        System.out.println("Request came for UPDATE PRODUCT IN CART");
        System.out.println(addToCartRequest.getCartId());
        System.out.println(addToCartRequest);

        Optional<Cart> optionalCart = cartDao.findById(addToCartRequest.getCartId());
        Cart cart = optionalCart.orElseThrow(() -> new RuntimeException("Cart item not found"));

        Optional<Product> optionalProduct = productDao.findById(addToCartRequest.getProductId());
        Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found"));

        int requestedQuantity = addToCartRequest.getQuantity();
        int availableStock = product.getQuantity();

        if (requestedQuantity > availableStock + cart.getQuantity()) {
        	System.out.println("qfeywgdt7ugeuf");
            return new ResponseEntity("Insufficient stock for the requested quantity", HttpStatus.BAD_REQUEST);
            
        }
        availableStock += cart.getQuantity();
        // Increase the stock quantity of the previous product
        product.setQuantity(availableStock);
        productDao.save(product);

        // Decrease the stock quantity and update the cart item with the new quantity
        product.setQuantity(availableStock - requestedQuantity);
        productDao.save(product);

        cart.setQuantity(requestedQuantity);
        cartDao.save(cart);

        return new ResponseEntity(HttpStatus.OK);
    }
}

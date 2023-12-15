package com.onlineshopping.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshopping.dao.CategoryDao;
import com.onlineshopping.dao.ProductDao;
import com.onlineshopping.dao.UserDao;
import com.onlineshopping.dto.ProductAddRequest;
import com.onlineshopping.dto.ProductUpdateRequest;
import com.onlineshopping.model.Cart;
import com.onlineshopping.model.Category;
import com.onlineshopping.model.Product;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.utility.StorageService;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostMapping("add")
	public ResponseEntity<?> addProduct(ProductAddRequest productDto) {
		System.out.println("recieved request for ADD PRODUCT");
		System.out.println(productDto);
		Product product=ProductAddRequest.toEntity(productDto);
		
		Optional<Category> optional = categoryDao.findById(productDto.getCategoryId());
		Category category = null;
		if(optional.isPresent()) {
			category = optional.get();
		}
		
		product.setCategory(category);
		productService.addProduct(product, productDto.getImage());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(product);
		
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllProducts() {
		
		System.out.println("request came for getting all products");
		
		List<Product> products = new ArrayList<Product>();
		
		products = productDao.findAll();
		
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(products);
		
	}
	
	@GetMapping("id")
	public ResponseEntity<?> getProductById(@RequestParam("productId") String productId) {
		
		System.out.println("request came for getting Product by Product Id");
		
		Product product = new Product();
		
		Optional<Product> optional = productDao.findById(productId);
		
		if(optional.isPresent()) {
			product = optional.get();
		}
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(product);
		
	}
	@GetMapping("category")
	public ResponseEntity<?> getProductsByCategories(@RequestParam("categoryId") String categoryId) {

	    System.out.println("request came for getting all products by category");

	    MatchOperation matchOperation = Aggregation.match(Criteria.where("categoryId").is(categoryId));

	    TypedAggregation<Product> aggregation = Aggregation.newAggregation(Product.class, matchOperation);

	    AggregationResults<Product> results = mongoTemplate.aggregate(aggregation, Product.class);

	    List<Product> products = results.getMappedResults();

	    System.out.println("response sent!!!");

	    return ResponseEntity.ok(products);
	}
	
	@GetMapping(value="/{productImageName}", produces = "image/*")
	public void fetchProductImage(@PathVariable("productImageName") String productImageName, HttpServletResponse resp) {
		System.out.println("request came for fetching product pic");
		System.out.println("Loading file: " + productImageName);
		Resource resource = storageService.load(productImageName);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}
	@GetMapping("remove")
	public ResponseEntity removeProduct(@RequestParam("productId") String productId) throws JsonProcessingException {
		
		System.out.println("request came for DELETE PRODUCT ITEM WHOSE ID IS : "+productId);
		
		Optional<Product> optionalProduct = this.productDao.findById(productId);
		Product product = new Product();
		
		if(optionalProduct.isPresent()) {
			product = optionalProduct.get();
		}
		
		this.productDao.delete(product);
		
		return new ResponseEntity("SUCCESS", HttpStatus.OK);
		
	}
	
	@PostMapping("update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                           @RequestBody ProductUpdateRequest productDto) {
        System.out.println("recieved request for UPDATE PRODUCT: " + productId);
        System.out.println(productDto);

        Optional<Product> optionalProduct = productDao.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // Update fields from DTO
            existingProduct.setTitle(productDto.getTitle());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setQuantity(productDto.getQuantity());

            // Set other fields as needed

            //productService.updateProduct(existingProduct, productDto.getImage());
            productDao.save(existingProduct);
            
            System.out.println("response sent!!!");
            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

}

package controller;

import java.util.List;

import domain.Product;

public interface ProductController {

	List<Product> getProducts();
	
	boolean addProduct(Product product);
	
	boolean removeProduct(Product product);
	
	
	
	
	
}

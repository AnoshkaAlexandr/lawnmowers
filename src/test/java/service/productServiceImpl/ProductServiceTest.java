package service.productServiceImpl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.impl.List.ProductDaoInList;
import domain.Product;
import service.IProductService;

public class ProductServiceTest {

	IProductService productService = new ProductService(ProductDaoInList.getImplementation());
	
	Product product;
	
	void init() {
		product = new Product("test name", "test description", "test uri", 100);
	}
	
	
	@Test
	public void testAdded() {
		init();
		assertTrue(productService.saveOrUpdate(product));
	}
	
	@Test
	public void testLoadAll() {
		assertTrue(productService.loadAll() != null);
	}
	
	@Test
	public void testRemove() {
		init();
		assertTrue(productService.saveOrUpdate(product));
		assertTrue(productService.remove(product));
	}
}

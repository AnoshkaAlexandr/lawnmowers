package service.productServiceImpl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.impl.inMemory.ProductDaoInMemory;
import domain.Product;
import exception.ServiceException;
import service.IProductService;

public class ProductServiceTest {

	IProductService productService = new ProductService(ProductDaoInMemory.getImplementation());
	
	Product product;
	
	void init() {
		product = new Product("test name", "test description", "test uri", 100);
	}
	
	
	@Test
	public void testAdded() throws ServiceException {
		init();
		assertTrue(productService.saveOrUpdate(product));
	}
	
	@Test
	public void testLoadAll() throws ServiceException {
		assertTrue(productService.loadAll() != null);
	}
	
	@Test
	public void testRemove() throws ServiceException {
		init();
		assertTrue(productService.saveOrUpdate(product));
		assertTrue(productService.remove(product));
	}
}

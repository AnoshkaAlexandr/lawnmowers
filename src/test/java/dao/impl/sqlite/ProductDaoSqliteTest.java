package dao.impl.sqlite;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.IProductDao;
import domain.Product;

public class ProductDaoSqliteTest {
	IProductDao productController = ProductDaoSqlite.getImpl();
	private Product product;

	void init() {
		product = new Product("Test name", "test description", "test uri", 10);
	}

	@Test
	public void testAdd() {
		init();
		assertTrue(productController.add(product));
	}
	
	@Test
	public void testLoadAll() {
		
		assertTrue(productController.loadAll() != null);
	}

	@Test
	public void testRemove() {
		init();
		assertTrue(productController.add(product));
		assertTrue(productController.remove(product));
	}
}

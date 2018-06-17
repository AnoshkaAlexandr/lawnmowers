package dao.impl.list;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.IProductDao;
import dao.impl.List.ProductDaoInList;
import domain.Product;

public class ProductDaoTest {

	IProductDao productDao = ProductDaoInList.getImplementation();
	
	Product product;
	
	void init() {
		product = new Product("test name", "test description", "test link", 100);
	}
	
	@Test
	public void testAdded() {
		init();
		assertTrue(productDao.add(product));
	}
	
	@Test
	public void testLoadAll() {
		assertTrue(productDao.loadAll() != null);
	}
	
	@Test
	public void testRemove() {
		init();
		assertTrue(productDao.add(product));
		assertTrue(productDao.remove(product));
	}
}

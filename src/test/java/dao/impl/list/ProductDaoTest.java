package dao.impl.list;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.IProductDao;
import dao.impl.inMemory.ProductDaoInMemory;
import domain.Product;
import exception.DaoException;

public class ProductDaoTest {

	IProductDao productDao = ProductDaoInMemory.getImplementation();
	
	Product product;
	
	void init() {
		product = new Product("test name", "test description", "test link", 100);
	}
	
	@Test
	public void testAdded() throws DaoException {
		init();
		assertTrue(productDao.add(product));
	}
	
	@Test
	public void testLoadAll() throws DaoException {
		assertTrue(productDao.loadAll() != null);
	}
	
	@Test
	public void testRemove() throws DaoException {
		init();
		assertTrue(productDao.add(product));
		assertTrue(productDao.remove(product));
	}
}

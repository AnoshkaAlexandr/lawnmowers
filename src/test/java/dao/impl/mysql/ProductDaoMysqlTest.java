package dao.impl.mysql;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.IProductDao;
import domain.Product;
import exception.DaoException;

public class ProductDaoMysqlTest {

	private IProductDao productDao = ProductDaoMysql.getImpl();
	
	Product product;
	
	void init() {
		product = new Product("test name", "test description", "test link", 200);
	}
	
	@Test
	public void testAdd() throws DaoException {
		init();
		assertTrue(productDao.add(product));
		productDao.remove(product);
	}

	@Test
	public void testRemove() throws DaoException {
		init();
		productDao.add(product);
		assertTrue(productDao.remove(product));
	}

	@Test
	public void loagAll() throws DaoException {
		init();
		productDao.add(product);
		assertTrue(productDao.loadAll() != null);
		productDao.remove(product);
	}
}

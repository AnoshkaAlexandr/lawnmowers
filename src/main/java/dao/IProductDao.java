package dao;


import java.util.List;

import domain.Product;
import exception.DaoException;

public interface IProductDao {

	boolean add(Product addObject) throws DaoException;

	boolean remove(Product removeObject) throws DaoException;

	List<Product> loadAll() throws DaoException;
	
	
	
	
	
}

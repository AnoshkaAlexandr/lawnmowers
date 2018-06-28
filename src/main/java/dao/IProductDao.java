package dao;


import java.util.List;

import domain.Product;
import exception.DaoException;

public interface IProductDao {
	Product getById(int id) throws DaoException;

	boolean edit(Product editObject) throws DaoException;

	boolean add(Product addObject) throws DaoException;

	boolean remove(Product removeObject) throws DaoException;

	List<Product> loadAll() throws DaoException;
	
	
	
	
	
}

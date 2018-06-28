package service;

import java.util.List;

import domain.Product;
import exception.ServiceException;

public interface IProductService {
	Product getById(int id) throws  ServiceException;

	boolean saveOrUpdate(Product addObject) throws ServiceException;

	boolean remove(Product removeObject)  throws ServiceException;

	List<Product> loadAll()  throws ServiceException;

}

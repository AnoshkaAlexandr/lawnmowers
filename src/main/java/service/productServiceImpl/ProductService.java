package service.productServiceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import dao.IProductDao;
import domain.Product;
import exception.DaoException;
import exception.ServiceException;
import service.IProductService;

public class ProductService implements IProductService {

	private static final Logger logger = Logger.getLogger(ProductService.class);
	private IProductDao productController;

	public ProductService(IProductDao productController) {
		this.productController = productController;
	}

	@Override
	public boolean saveOrUpdate(Product addObject) throws ServiceException {
		if (addObject != null) {
			if (addObject.getId() != 0) {
				logger.warn("Product is null");
				return false;

			} else {
				try {
					productController.add(addObject);
				} catch (DaoException e) {
					logger.error("Can't add product " + e.getMessage(), e);
					throw new ServiceException("Can't add products " + e.getMessage(), e);
				}
				logger.info("Product is added");
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Product removeObject) throws ServiceException {
		if (removeObject != null) {
			try {
				productController.remove(removeObject);
			} catch (DaoException e) {
				logger.error("Can't remove products " + e.getMessage(), e);
				throw new ServiceException("Can't remove products " + e.getMessage(), e);
			}
			logger.info("Product was removed");
			return true;
		}
		return false;
	}

	@Override
	public List<Product> loadAll() throws ServiceException {

		try {
			return productController.loadAll();
		} catch (DaoException e) {
			logger.error("Can't get products " + e.getMessage(), e);
			throw new ServiceException("Can't get products " + e.getMessage(), e);
		}
	}

}

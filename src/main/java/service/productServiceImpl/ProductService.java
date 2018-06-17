package service.productServiceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import dao.IProductDao;
import domain.Product;
import service.IProductService;

public class ProductService implements IProductService {

	private static final Logger logger = Logger.getLogger(ProductService.class);
	private IProductDao productController;

	public ProductService(IProductDao productController) {
		this.productController = productController;
	}

	@Override
	public boolean saveOrUpdate(Product addObject) {
		if (addObject != null) {
			if (addObject.getId() != 0) {
				// todo implements update product
				logger.warn("Product is null");
				return false;

			} else {
				productController.add(addObject);
				logger.info("Product is added");
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Product removeObject) {
		if (removeObject != null) {
			productController.remove(removeObject);
			logger.info("Product was removed");
			return true;
		}
		return false;
	}

	@Override
	public List<Product> loadAll() {

		return productController.loadAll();
	}

}

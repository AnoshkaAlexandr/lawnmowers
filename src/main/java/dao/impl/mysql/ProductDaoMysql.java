package dao.impl.mysql;


import dao.impl.ProductDao;
import org.apache.log4j.Logger;
import dao.IProductDao;
import exception.DaoException;

public class ProductDaoMysql extends ProductDao implements IProductDao {

    private static ProductDaoMysql productDaoMysql;
    private static final Logger logger = Logger.getLogger(ProductDaoMysql.class);
    private static final String DATABASE_PATH = "jdbc:mysql://localhost/test?user=nosha&password=1234";

    private ProductDaoMysql() throws DaoException {
        super(DATABASE_PATH);
        createConnection();
    }

    private void createConnection() throws DaoException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("Can't create class for connector " + e.getMessage(), e);
            throw new DaoException("Can't create class for connector " + e.getMessage(), e);

        }

    }

    public static ProductDaoMysql getImpl() {
        if (productDaoMysql == null) {
            try {
                productDaoMysql = new ProductDaoMysql();
            } catch (DaoException e) {
                logger.error("Can't get implementation " + e.getMessage(), e);
            }
        }
        return productDaoMysql;
    }


}

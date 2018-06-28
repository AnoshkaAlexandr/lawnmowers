package dao.impl.sqlite;


import dao.impl.ProductDao;
import org.apache.log4j.Logger;

import dao.IProductDao;
import exception.DaoException;

public class ProductDaoSqlite extends ProductDao implements IProductDao {
    private static final Logger logger = Logger.getLogger(ProductDaoSqlite.class);


    private static String path = ProductDaoSqlite.class.getResource("").toString().substring(5)
            .replace("dao/impl/sqlite/", "");
    private static final String DATABASE_PATH = "jdbc:sqlite:" + path + "site.db";


    private static ProductDaoSqlite pcsqlite;

    private ProductDaoSqlite() throws DaoException {
        super(DATABASE_PATH);
        createConnection();

    }

    public static ProductDaoSqlite getImpl() {
        if (pcsqlite == null) {
            try {
                pcsqlite = new ProductDaoSqlite();
            } catch (DaoException e) {
                logger.error("Can't create implementation" + e.getMessage(), e);
            }
        }
        return pcsqlite;
    }

    private void createConnection() throws DaoException {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error("Can't create class for connector " + e.getMessage(), e);
            throw new DaoException("Can't create class for connector " + e.getMessage(), e);

        }

    }


}

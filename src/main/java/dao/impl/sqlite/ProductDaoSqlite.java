package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.IProductDao;
import domain.Product;
import exception.DaoException;

public class ProductDaoSqlite implements IProductDao {
	private static final Logger logger = Logger.getLogger(ProductDaoSqlite.class);

	private static final String SQL_SELECT_ALL = "SELECT * FROM PRODUCT;";

	private Connection connection = null;
	private Statement stmt = null;

	private static String path = ProductDaoSqlite.class.getResource("").toString().substring(5)
			.replace("dao/impl/sqlite/", "");

	private static ProductDaoSqlite pcsqlite;

	private ProductDaoSqlite() {
		createConnection();


	}

	public static ProductDaoSqlite getImpl() {
		if (pcsqlite == null) {
			pcsqlite = new ProductDaoSqlite();
		}
		return pcsqlite;
	}

	@Override
	public List<Product> loadAll() {
		List<Product> products = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String imgLink = rs.getString("link");
				int cost = rs.getInt("price");

				products.add(new Product(id, name, description, imgLink, cost));
			}

		} catch (Exception e) {
			logger.error("Can't get products " + e.getMessage(), e);
			throw new DaoException("Can't get products " + e.getMessage(), e);

		}
		return products;
	}

	@Override
	public boolean add(Product product) {
		if (product != null) {

			try {

				stmt = connection.createStatement();
				String sql = "INSERT INTO PRODUCT (NAME,DESCRIPTION,LINK,PRICE) " + "VALUES ( '" + product.getName()
						+ "', '" + product.getDescription() + "', '" + product.getImgLink() + "', " + product.getCost()
						+ ");";
				stmt.executeUpdate(sql);

			}

			catch (SQLException e) {
				logger.error("From add product " + e.getMessage(), e);
				throw new DaoException("From add product " + e.getMessage(), e);
			}

			return true;
		} else

		{
			logger.error("Product by add is null ");
			return false;

		}
	}

	@Override
	public boolean remove(Product product) {
		if (product != null) {
			try {
				String sql = "DELETE from PRODUCT where ID=" + product.getId() + ";";
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				logger.error("From remove " + e.getMessage(), e);
			}
			return true;
		} else {
			logger.error("Product by remove is null ");
			return false;

		}
	}

	private void createConnection() {

		if (connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				//logger.warn("jdbc:sqlite:" + path + "site.db");
				connection = DriverManager.getConnection("jdbc:sqlite:" + path + "site.db");
logger.info("Open connection sqlite");
				connection.setAutoCommit(false);

				stmt = connection.createStatement();

			} catch (Exception e) {
				logger.error("Can't open connection to sqlite " + e.getMessage(), e);
				throw new DaoException("Can't open connection to sqlite " + e.getMessage(), e);
			}
		}
	}

	void closeConnection(Connection con, Statement st) {

		try {
			st.close();
			con.close();
			logger.info("Close connection sqlite");

		} catch (SQLException e) {
			logger.error("Can't creat connection to sqlite " + e.getMessage(), e);
			throw new DaoException("Can't open connection to sqlite " + e.getMessage(), e);

		}
	}
}

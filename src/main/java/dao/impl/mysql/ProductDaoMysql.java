package dao.impl.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.IProductDao;
import domain.Product;
import exception.DaoException;

public class ProductDaoMysql implements IProductDao {
	private static ProductDaoMysql productDaoMysql;
	private static final Logger logger = Logger.getLogger(ProductDaoMysql.class);
	private static final String DATABASE_PATH = "jdbc:mysql://localhost/test?user=nosha&password=1234";
	private static final String SQL_SELECT_ALL = "SELECT * FROM product;";
	

	private ProductDaoMysql() {
		createConnection();
	}

	private void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("Can't create class for connector " + e.getMessage(), e);
			throw new DaoException("Can't create class for connector " + e.getMessage(), e);

		}

	}

	public static ProductDaoMysql getImpl() {
		if (productDaoMysql == null) {
			productDaoMysql = new ProductDaoMysql();
		}
		return productDaoMysql;
	}

	@Override
	public boolean add(Product addObject) {
		if (addObject != null) {
			Connection connection = null;
			Statement statement = null;
			try {
				connection = DriverManager.getConnection(DATABASE_PATH);
				statement = connection.createStatement();

				String sql = "INSERT INTO product (NAME,DESCRIPTION,LINK,PRICE) " + "VALUES ( '" + addObject.getName()
						+ "', '" + addObject.getDescription() + "', '" + addObject.getImgLink() + "', "
						+ addObject.getCost() + ");";
				statement.executeUpdate(sql);

				statement.close();
				connection.close();

			} catch (Exception e) {
				logger.error("Can't add product from mysql " + e.getMessage(), e);
				throw new DaoException("Can't add product from mysql " + e.getMessage(), e);
			}
			return true;
		} else {
			
			return false;
		}
	}

	@Override
	public boolean remove(Product removeObject) {
		if (removeObject != null) {
			Connection connection = null;
			Statement statement = null;
			try {
				connection = DriverManager.getConnection(DATABASE_PATH);
				statement = connection.createStatement();

				String sql = "DELETE from product where id=" + removeObject.getId() + ";";
				statement.executeUpdate(sql);

				statement.close();
				connection.close();

			} catch (Exception e) {
				logger.error("Can't remove product from mysql " + e.getMessage(), e);
				throw new DaoException("Can't remove product from mysql " + e.getMessage(), e);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Product> loadAll() {

		Connection connection = null;
		Statement statement = null;
		 List<Product> products = new ArrayList<>();

		try {
			connection = DriverManager.getConnection(DATABASE_PATH);
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String imgLink = rs.getString("link");
				int cost = rs.getInt("price");

				products.add(new Product(id, name, description, imgLink, cost));
			}

			statement.close();
			connection.close();

		} catch (Exception e) {
			logger.error("Can't get products from mysql " + e.getMessage(), e);
			throw new DaoException("Can't get products from mysql " + e.getMessage(), e);
		}
		return products;

	}
}

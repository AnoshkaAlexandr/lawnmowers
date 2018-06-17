package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Product;

public class ProductControllerSqlite implements ProductController {
	private Connection c = null;
	private Statement stmt = null;

	private static String path = ProductControllerSqlite.class.getResource("").toString().substring(5)
			.replace("controller/", "");

	private static ProductControllerSqlite pcsqlite;

	private ProductControllerSqlite() {
		createConnection();

	}

	public static ProductControllerSqlite getImpl() {
		if (pcsqlite == null) {
			pcsqlite = new ProductControllerSqlite();
		}
		return pcsqlite;
	}

	@Override
	public List<Product> getProducts() {
		List<Product> products = new ArrayList<>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT;");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String imgLink = rs.getString("link");
				int cost = rs.getInt("price");

				products.add(new Product(id, name, description, imgLink, cost));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
		return products;
	}

	@Override
	public boolean addProduct(Product product) {
		createConnection();

		try {

			stmt = c.createStatement();
			String sql = "INSERT INTO PRODUCT (NAME,DESCRIPTION,LINK,PRICE) " + "VALUES ( '" + product.getName()
					+ "', '" + product.getDescription() + "', '" + product.getImgLink() + "', " + product.getCost()
					+ ");";
			stmt.executeUpdate(sql);

			stmt.close();
		}

		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return true;
	}

	@Override
	public boolean removeProduct(Product product) {
		try {
			stmt = c.createStatement();
			String sql = "DELETE from PRODUCT where ID=" + product.getId() + ";";
			stmt.executeUpdate(sql);
			// c.commit();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return true;
	}

	private void createConnection() {
		if (c == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + path + "site.db");
				System.out.println("jdbc:sqlite:" + path + "site.db");
				//for local db 
				//c = DriverManager.getConnection("jdbc:sqlite:site.db");

				c.setAutoCommit(false);

				stmt = c.createStatement();
				/*
				 * String sql = "CREATE TABLE PRODUCT " +
				 * "(ID INTEGER PRIMARY KEY  AUTOINCREMENT," +
				 * " NAME           TEXT    NOT NULL, " +
				 * " DESCRIPTION            TEXT     NOT NULL, " +
				 * " LINK        TEXT NOT NULL, " + " PRICE INTEGER)"; stmt.executeUpdate(sql);
				 */

			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}

}

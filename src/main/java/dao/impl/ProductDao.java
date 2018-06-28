package dao.impl;

import dao.IProductDao;
import domain.Product;
import exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    private static final String SQL_CREATE_PRODUCT = "INSERT INTO product (NAME,DESCRIPTION,LINK,PRICE) VALUES ( ?, ?, ?, ?);";
    private static final String SQL_DELETE_PRODUCT = "DELETE from product where id=?;";
    private static final String SQL_SELECT_ALL = "SELECT * FROM product;";
    private static final String SQL_GET_PRODUCT = "SELECT * FROM product WHERE id=?;";
    private static final String SQL_EDIT_PRODUCT = "UPDATE product SET NAME=?, DESCRIPTION=?, LINK=?, PRICE=? WHERE id=?;";

    private static String DATABASE_PATH = null;
    private static final Logger logger = Logger.getLogger(ProductDao.class);

    public ProductDao(String path) {
        DATABASE_PATH = path;
    }

    @Override
    public Product getById(int id) throws DaoException {
        Product product = new Product();
        try (Connection connection = DriverManager.getConnection(DATABASE_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();


        } catch (Exception e) {
            logger.error("Can't getting product by id from db " + e.getMessage(), e);
            throw new DaoException("Can't getting product by id from db " + e.getMessage(), e);
        }
        return product;

    }

    @Override
    public boolean edit(Product editObject) throws DaoException {
        int id = editObject.getId();
        String name = editObject.getName();
        String description = editObject.getDescription();
        String link = editObject.getImgLink();
        int price = editObject.getCost();


        try (Connection connection = DriverManager.getConnection(DATABASE_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_PRODUCT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, link);
            preparedStatement.setInt(4, price);
            preparedStatement.setInt(5, id);


            preparedStatement.execute();


        } catch (Exception e) {
            logger.error("Can't add product from db " + e.getMessage(), e);
            throw new DaoException("Can't add product from db " + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean add(Product addObject) throws DaoException {

        String name = addObject.getName();
        String description = addObject.getDescription();
        String link = addObject.getImgLink();
        int price = addObject.getCost();


        try (Connection connection = DriverManager.getConnection(DATABASE_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_PRODUCT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, link);
            preparedStatement.setInt(4, price);

            preparedStatement.execute();


        } catch (Exception e) {
            logger.error("Can't add product from db " + e.getMessage(), e);
            throw new DaoException("Can't add product from db " + e.getMessage(), e);
        }
        return true;

    }

    @Override
    public boolean remove(Product removeObject) throws DaoException {

        int id = removeObject.getId();

        try (Connection connection = DriverManager.getConnection(DATABASE_PATH);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();


        } catch (Exception e) {
            logger.error("Can't remove product from db " + e.getMessage(), e);
            throw new DaoException("Can't remove product from db " + e.getMessage(), e);
        }
        return true;

    }

    @Override
    public List<Product> loadAll() throws DaoException {


        List<Product> products = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DATABASE_PATH);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SQL_SELECT_ALL)) {


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imgLink = rs.getString("link");
                int cost = rs.getInt("price");

                products.add(new Product(id, name, description, imgLink, cost));
            }


        } catch (Exception e) {
            logger.error("Can't get products from db " + e.getMessage(), e);
            throw new DaoException("Can't get products from db " + e.getMessage(), e);
        }
        return products;

    }
}

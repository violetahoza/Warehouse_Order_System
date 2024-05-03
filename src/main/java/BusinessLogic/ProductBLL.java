package BusinessLogic;

import BusinessLogic.Validators.ProductValidator;
import DataAccess.AbstractDAO;
import DataAccess.ProductDAO;
import Model.Product;
import Presentation.AddController;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/** Business Logic class for handling operations related to products. It provides functionalities for inserting, updating, deleting, and finding products in the database.*/
public class ProductBLL {
    ProductDAO productDAO = new ProductDAO(Product.class);

    public ProductBLL(){}

    /**
     * Inserts a new product into the database after validating the data. Displays a success message upon successful insertion.
     * @param fields The list of fields containing product data.
     * @throws RuntimeException If an SQL error occurs while interacting with the database.
     */
    public void insert(List<Object> fields) {
        validateData(fields);
        Product product = new Product();
        product.setId((int) fields.get(0));
        product.setName((String) fields.get(1));
        product.setPrice(Double.parseDouble((String) fields.get(2)));
        product.setQuantity(Integer.parseInt((String) fields.get(3)));
        try {
            productDAO.insert(product);
            AddController.showMessageDialog("The product was inserted successfully!", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all products from the database.
     * @return A list of all products in the database.
     */
    public List<Product> findAll(){
        return productDAO.findAll();
    }

    /**
     * Deletes a product from the database. Displays a success message upon successful deletion.
     * @param product The product to be deleted.
     * @throws RuntimeException If an SQL error occurs while interacting with the database.
     */
    public void delete(Product product) {
        try {
            productDAO.delete(product);
            AddController.showMessageDialog("The product was deleted successfully!", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a product in the database after validating the data. Displays a success message upon successful update.
     * @param fields The list of fields containing updated product data.
     * @param product The product to be updated.
     * @throws RuntimeException If an SQL error occurs while interacting with the database.
     */
    public void edit(List<Object> fields, Product product){
        validateData(fields);
        product.setName((String) fields.get(1));
        product.setPrice(Double.parseDouble((String) fields.get(2)));
        product.setQuantity(Integer.parseInt((String) fields.get(3)));
        try {
            update(product);
            AddController.showMessageDialog("Successful update!", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the data for a product.
     * Throws an exception if the number of fields is invalid or if data validation fails.
     * @param fields The list of fields containing product data.
     * @throws IllegalArgumentException If the number of fields is invalid for a product object.
     */
    public void validateData(List<Object> fields){
        if (fields.size() < 4) {
            throw new IllegalArgumentException("Invalid number of fields for Product object");
        }
        Product product = new Product();
        product.setPrice(Double.parseDouble((String) fields.get(2)));
        product.setQuantity(Integer.parseInt((String) fields.get(3)));
        ProductValidator productValidator = new ProductValidator();
        productValidator.validate(product);
    }

    /**
     * Updates a product in the database.
     * @param product The product to be updated.
     * @throws SQLException If an SQL error occurs while interacting with the database.
     */
    public void update(Product product) throws SQLException {
        productDAO.update(product);
    }
}

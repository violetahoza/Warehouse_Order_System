package BusinessLogic;

import BusinessLogic.Validators.ProductValidator;
import DataAccess.AbstractDAO;
import DataAccess.ProductDAO;
import Model.Product;
import Presentation.AddController;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ProductBLL {
    ProductDAO productDAO = new ProductDAO(Product.class);

    public ProductBLL(){}
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
    public List<Product> findAll(){
        return productDAO.findAll();
    }
    public void delete(Product product) {
        try {
            productDAO.delete(product);
            AddController.showMessageDialog("The product was deleted successfully!", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void edit(List<Object> fields, Product product){
        validateData(fields);
        product.setName((String) fields.get(1));
        product.setPrice(Double.parseDouble((String) fields.get(2)));
        product.setQuantity(Integer.parseInt((String) fields.get(3)));
        try {
            productDAO.update(product);
            AddController.showMessageDialog("Successful update!", "Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
    public void update(Product product) throws SQLException {
        productDAO.update(product);
    }
}

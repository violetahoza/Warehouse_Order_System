package businessLogic.validators;

import model.Product;
import presentation.AddController;

/**
 * Validator class for validating Product objects.
 */
public class ProductValidator implements Validator<Product> {
    /**
     * Validates the given Product object.
     * Checks if the price and quantity are greater than 0. If not, displays an error message and throws an IllegalArgumentException.
     * @param product The Product object to validate.
     * @throws IllegalArgumentException If the price or quantity is not greater than 0.
     */
    @Override
    public void validate(Product product) {
        if(product.getPrice() <= 0){
            AddController.showErrorDialog("The price must be greater than 0!", "Input error");
            throw new IllegalArgumentException("The price must be greater than 0!");
        }
        if(product.getQuantity() <= 0){
            AddController.showErrorDialog("The quantity must be greater than 0!", "Input error");
            throw new IllegalArgumentException("The quantity must be greater than 0!");
        }
    }
}

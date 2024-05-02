package BusinessLogic.Validators;

import Model.Product;
import Presentation.AddController;

public class ProductValidator implements Validator<Product> {
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

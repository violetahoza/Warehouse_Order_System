package DataAccess;

import Model.Product;

public class ProductDAO extends AbstractDAO<Product>{
    public ProductDAO(Class<Product> type) {
        super(type);
    }
}

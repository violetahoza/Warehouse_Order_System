package DataAccess;

import Model.Product;

/**
 * Data Access Object (DAO) for performing CRUD operations on Product objects.
 * Extends the AbstractDAO class, providing generic CRUD functionalities.
 */
public class ProductDAO extends AbstractDAO<Product>{
    public ProductDAO(Class<Product> type) {
        super(type);
    }
}

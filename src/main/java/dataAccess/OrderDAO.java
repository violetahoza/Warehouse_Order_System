package dataAccess;

import model.Order;

/**
 * Data Access Object (DAO) for performing CRUD operations on Order objects.
 * Extends the AbstractDAO class, providing generic CRUD functionalities.
 */
public class OrderDAO extends AbstractDAO<Order> {
    public OrderDAO(Class<Order> type) {
        super(type);
    }
}

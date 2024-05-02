package DataAccess;

import Model.Order;

public class OrderDAO extends AbstractDAO<Order> {
    public OrderDAO(Class<Order> type) {
        super(type);
    }
}

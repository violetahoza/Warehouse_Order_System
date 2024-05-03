package DataAccess;

import Connection.ConnectionFactory;
import Model.Bill;

import java.sql.SQLException;

/** The BillDAO class provides methods to interact with the database regarding bill entities.*/
public class BillDAO {
    public BillDAO(){}

    /**
     * Constructs an insert query string for inserting a bill into the log table.
     * @param bill The bill to be inserted.
     * @return An insert query string for the bill.
     */
    private String insertBillQuery(Bill bill) {
        StringBuilder query = new StringBuilder("INSERT INTO bills (client, product, amount, date) VALUES (");
        query.append("'").append(bill.client().getName()).append("', ");
        query.append("'").append(bill.product().getName()).append("', ");
        query.append(bill.amount()).append(", ");
        query.append("'").append(bill.timestamp()).append("')");
        return query.toString();
    }

    /**
     * Inserts a bill into the log table.
     * @param bill The bill to be inserted.
     * @throws SQLException if a database access error occurs.
     */
    public void insertBill(Bill bill) throws SQLException {
        String SQL = insertBillQuery(bill);
        ConnectionFactory.connectAndExecute(SQL);
    }
}

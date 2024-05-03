package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Order;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;

public class OrderController extends JFrame {
    private JComboBox clientsBox, productsBox;
    private JTextField quantity = new JTextField();
    private JButton submit = new JButton("Submit");
    private ClientBLL clientBLL = new ClientBLL(); // Business Logic Layer object for handling client-related operations
    private ProductBLL productBLL = new ProductBLL(); // Business Logic Layer object for handling product-related operations

    public OrderController(){
        List<Client> clients = clientBLL.findAll();
        List<Product> products = productBLL.findAll();

        String[] clientsArray = new String[clients.size()];
        for(Client client : clients){
            clientsArray[clients.indexOf(client)] = client.toString();
        }
        String[] productsArray = new String[products.size()];
        for(Product product : products){
            productsArray[products.indexOf(product)] = product.toString();
        }
        orderView(clientsArray, productsArray);
        submit.addActionListener(e -> {
            Client client = clients.get(clientsBox.getSelectedIndex());
            Product product = products.get(productsBox.getSelectedIndex());
            if(!NumberUtils.isNumber(quantity.getText()))
                throw new NumberFormatException("The quantity must be a natural number!");
            else if (Integer.parseInt(quantity.getText()) <= 0) {
                AddController.showErrorDialog("The quantity must be greater than 0!", "Input error");
                throw new IllegalArgumentException("The quantity must be greater than 0!");
            }
            OrderBLL orderBLL = new OrderBLL();
            Order order = new Order(client.getId(), product.getId(), Integer.parseInt(quantity.getText()));
            try {
                if(orderBLL.insert(order, product)){
                    product.setQuantity(product.getQuantity() - order.getQuantity());
                    productBLL.update(product);
                    productsBox.removeAllItems();
                    for(Product product1 : products)
                        productsBox.addItem(product1.toString());
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void orderView(String[] clientsList, String[] productsList) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Place Order");
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        inputPanel.setBackground(new Color(200, 160, 255));

        clientsBox = new JComboBox(clientsList);
        productsBox = new JComboBox(productsList);

        JLabel clientLabel = new JLabel("Select Client:");
        JLabel productLabel = new JLabel("Select Product:");
        JLabel quantityLabel = new JLabel("Enter Quantity:");

        AddController.addRow(inputPanel, clientLabel, clientsBox);
        AddController.addRow(inputPanel, productLabel, productsBox);
        AddController.addRow(inputPanel, quantityLabel, quantity);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        buttonPanel.setBackground(new Color(200, 160, 255));
        StartController.customizeComponent(submit);
        buttonPanel.add(submit);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

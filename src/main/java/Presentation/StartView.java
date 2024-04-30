package Presentation;

import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
    JLabel selection = new JLabel("Choose the action to be performed: ");
    JButton clientOP = new JButton("Client operations");
    JButton productOP = new JButton("Product operations");
    JButton productOrders = new JButton("Make a new order");

    public StartView(String name){
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4,1));
        this.getContentPane().setBackground(new Color(200, 160, 255));
        this.setPreferredSize(new Dimension(350, 200));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color foreground = new Color(128, 0, 128);
        Color background = new Color(220, 200, 250);

        selection.setFont(labelFont);
        selection.setForeground(foreground);
        selection.setHorizontalAlignment(SwingConstants.CENTER);
        clientOP.setFont(buttonFont);
        productOP.setFont(buttonFont);
        productOrders.setFont(buttonFont);
        clientOP.setForeground(foreground);
        clientOP.setBackground(background);
        productOP.setBackground(background);
        productOP.setForeground(foreground);
        productOrders.setForeground(foreground);
        productOrders.setBackground(background);

        this.add(selection);
        this.add(clientOP);
        this.add(productOP);
        this.add(productOrders);

        this.setVisible(true);
        this.setResizable(false);
        this.pack();

        clientOP.addActionListener(e -> performOperation(Client.class));
        productOP.addActionListener(e -> performOperation(Product.class));
        productOrders.addActionListener(e -> makeOrder());
    }

    // Method to perform operations based on the button clicked
    private void performOperation(Class<?> type) {
        new OperationController<>(type);
    }

    // Method to handle making a new order
    private void makeOrder() {
        new OrderController();
    }
}

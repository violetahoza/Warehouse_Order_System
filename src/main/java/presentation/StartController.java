package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;

public class StartController extends JFrame {
    JLabel selection = new JLabel("Choose the action to be performed: ");
    JButton clientOP = new JButton("Client operations");
    JButton productOP = new JButton("Product operations");
    JButton productOrders = new JButton("Make a new order");

    public StartController(String name){
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(4,1));
        this.getContentPane().setBackground(new Color(200, 160, 255));
        this.setPreferredSize(new Dimension(350, 200));

        selection.setFont(new Font("Arial", Font.BOLD, 16));
        selection.setForeground( new Color(128, 0, 128));
        selection.setHorizontalAlignment(SwingConstants.CENTER);

        customizeComponent(clientOP);
        customizeComponent(productOP);
        customizeComponent(productOrders);

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
    public static void customizeComponent(JComponent component){
        Font componentFont = new Font("Arial", Font.BOLD, 16);
        Color foreground = new Color(128, 0, 128);
        Color background = new Color(220, 200, 250);

        component.setBackground(background);
        component.setFont(componentFont);
        component.setForeground(foreground);
    }
}

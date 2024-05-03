package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AddController<T> extends JFrame {
    private JLabel nameLabel, label1, label2, label3;
    private JTextField nametf = new JTextField(), tf1 = new JTextField(), tf2 = new JTextField(), tf3 = new JTextField();
    private JButton submit = new JButton("Submit");

    public AddController(Class<T> tClass) {
        String type = tClass.getSimpleName();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));
        this.setLocationRelativeTo(null);
        this.setTitle("Add " + tClass.getSimpleName());
        JPanel panel = new JPanel();
        panel.setBackground(new Color(220, 200, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        nameLabel = new JLabel("Insert " + type + " name: ");
        if (type.equals("Client")) {
            panel.setLayout(new GridLayout(5, 2));
            label1 = new JLabel("Insert age: ");
            label2 = new JLabel("Insert address: ");
            label3 = new JLabel("Insert email: ");
        } else if (type.equals("Product")) {
            panel.setLayout(new GridLayout(4, 2));
            label1 = new JLabel("Insert price: ");
            label2 = new JLabel("Insert quantity");
        }
        this.add(panel);
        StartController.customizeComponent(submit);
        addRow(panel, nameLabel, nametf);
        addRow(panel, label1, tf1);
        addRow(panel, label2, tf2);
        if (type.equals("Client")) {
            addRow(panel, label3, tf3);
        }

        submit.addActionListener(e -> {
            try {
                handleAddOperation(tClass);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(200, 160, 255));
        buttonPanel.add(submit);
        panel.add(buttonPanel);

        this.setVisible(true);
        this.pack();
        this.setResizable(false);
        panel.setBackground(new Color(200, 160, 255));
    }
    private void handleAddOperation(Class<T> type) throws SQLException {
        List<Object> attributes = new ArrayList<>();
        attributes.clear();
        attributes.add(ThreadLocalRandom.current().nextInt(1, 50000));
        if(nametf.getText().isEmpty())
        {
            showErrorDialog("Please enter the " + type.getSimpleName() + " name!", "Input error");
            throw new IllegalArgumentException("The name cannot be empty!");
        }
        if(tf1.getText().isEmpty() || tf2.getText().isEmpty())
        {
            showErrorDialog("Please complete all the fields!", "Input error");
            throw new IllegalArgumentException("The fields cannot be empty!");
        }
        if(type.getSimpleName().equals("Client") && tf3.getText().isEmpty()){
            showErrorDialog("Please enter the email!", "Input error");
            throw new IllegalArgumentException("The email cannot be empty!");
        }
        attributes.add(nametf.getText());
        attributes.add(tf1.getText());
        attributes.add(tf2.getText());
        attributes.add(tf3.getText());
        if(type.getSimpleName().equals("Client")){
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.insert(attributes);
        } else if (type.getSimpleName().equals("Product")) {
            ProductBLL productBLL = new ProductBLL();
            productBLL.insert(attributes);
        }
        this.dispose();
    }
    public static void addRow(JPanel panel, JLabel label, JComponent textField) {
        StartController.customizeComponent(label);
        StartController.customizeComponent(textField);
        panel.add(label);
        panel.add(textField);
    }
    public static void showMessageDialog(String message, String title) {
        Font font = new Font("Times New Roman", Font.BOLD, 18);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.messageForeground", Color.MAGENTA);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showErrorDialog(String message, String title) {
        Font font = new Font("Times New Roman", Font.BOLD, 18);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.messageForeground", Color.RED);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}

package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * EditController class represents the window for editing objects of a specified type.
 * @param <T> The type of object to edit.
 */
public class EditController<T> extends JFrame {
    private JLabel nameLabel, label1, label2, label3;
    private JTextField nametf = new JTextField(), tf1 = new JTextField(), tf2 = new JTextField(), tf3 = new JTextField();
    private JComboBox comboBox;
    private JButton submit = new JButton("Submit");
    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();

    public EditController(Class<T> tClass){
        java.util.List<T> list = null;
        if(tClass.isAssignableFrom(Product.class)) {
            list = (java.util.List<T>) productBLL.findAll();
        } else if(tClass.isAssignableFrom(Client.class)) {
            list = (java.util.List<T>) clientBLL.findAll();
        }

        String[] array = new String[list.size()];
        for(T object : list)
            array[list.indexOf(object)] = object.toString();
        editView(array, tClass.getSimpleName(), list);
        List<T> finalList = list;
        submit.addActionListener(e -> handleSubmitButton(finalList, tClass));
    }


    /**
     * Handles the submit button action by updating the selected object's attributes.
     * @param list   The list of objects.
     * @param tClass The class type of the object.
     */
    private void handleSubmitButton(List<T> list, Class<T> tClass) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            T t = list.get(selectedIndex);
            if (tClass.isAssignableFrom(Client.class)){
                List<Object> attributes = new ArrayList<>();
                attributes.add(((Client) t).getId());
                attributes.add(nametf.getText());
                attributes.add(tf1.getText());
                attributes.add(tf2.getText());
                attributes.add(tf3.getText());
                clientBLL.edit(attributes, (Client) t);
            } else if (tClass.isAssignableFrom(Product.class)) {
                List<Object> attributes = new ArrayList<>();
                attributes.add(((Product) t).getId());
                attributes.add(nametf.getText());
                attributes.add(tf1.getText());
                attributes.add(tf2.getText());
                productBLL.edit(attributes, (Product) t);
            }
            comboBox.removeAllItems();
            for(T object : list){
                comboBox.addItem(object.toString());
            }
        }
    }

    public void editView(String[] objects, String type, List<T> list){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));
        this.setLocationRelativeTo(null);
        this.setTitle("Edit " + type);

        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.setBackground(new Color(200, 160, 255));
        comboBox = new JComboBox<>(objects);
        StartController.customizeComponent(comboBox);
        comboBoxPanel.add(comboBox);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(type.equals("Client") ? 5 : 4, 2)); // Adjust rows based on type
        mainPanel.setBackground(new Color(200, 160, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        if (type.equals("Client")) {
            nameLabel = new JLabel("Insert client name: ");
            label1 = new JLabel("Insert age: ");
            label2 = new JLabel("Insert address: ");
            label3 = new JLabel("Insert email: ");
        } else if (type.equals("Product")) {
            nameLabel = new JLabel("Insert product name: ");
            label1 = new JLabel("Insert price: ");
            label2 = new JLabel("Insert quantity");
        }

        AddController.addRow(mainPanel, nameLabel, nametf);
        AddController.addRow(mainPanel, label1, tf1);
        AddController.addRow(mainPanel, label2, tf2);
        if (type.equals("Client")) {
            AddController.addRow(mainPanel, label3, tf3);
        }

        comboBox.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex != -1) {
                T selectedItem = list.get(selectedIndex);
                if (selectedItem instanceof Client) {
                    Client client = (Client) selectedItem;
                    nametf.setText(client.getName());
                    tf1.setText(String.valueOf(client.getAge()));
                    tf2.setText(client.getAddress());
                    tf3.setText(client.getEmail());
                } else if (selectedItem instanceof Product) {
                    Product product = (Product) selectedItem;
                    nametf.setText(product.getName());
                    tf1.setText(String.valueOf(product.getPrice()));
                    tf2.setText(String.valueOf(product.getQuantity()));
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(200, 160, 255));
        StartController.customizeComponent(submit);
        buttonPanel.add(submit);
        mainPanel.add(buttonPanel);

        this.add(comboBoxPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
        this.pack();
        this.setResizable(false);
    }
}

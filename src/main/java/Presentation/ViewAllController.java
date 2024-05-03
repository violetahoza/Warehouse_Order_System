package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

public class ViewAllController<T> extends JFrame {
    JTable table;
    ProductBLL productBLL = new ProductBLL();
    ClientBLL clientBLL = new ClientBLL();

    public ViewAllController(Class<T> tClass){
        List<T> list = null;
        if(tClass.isAssignableFrom(Product.class)) {
            list = (List<T>) productBLL.findAll();
        } else if(tClass.isAssignableFrom(Client.class)) {
            list = (List<T>) clientBLL.findAll();
        }
        generateTable(list);
    }

    // This method generates a table view based on the provided list of entities. It takes a list of entities of type T as input.
    private void generateTable(List<T> list){
        Class<?> tClass = list.get(0).getClass(); // Get the class of the entities in the list

        // Get the names of the fields of the class
        String[] columnNames = new String[tClass.getDeclaredFields().length];
        for(int i = 0; i < tClass.getDeclaredFields().length; i++){
            columnNames[i] = tClass.getDeclaredFields()[i].getName();
        }

        // Initialize a 2D array to hold the data for the table
        Object[][] objects = new Object[list.size()][tClass.getDeclaredFields().length];
        // Populate the 2D array with data from the list
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < tClass.getDeclaredFields().length;j ++){
                Field field = tClass.getDeclaredFields()[j];
                field.setAccessible(true);
                try {
                    objects[i][j] = field.get(list.get(i));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        setupView(objects, columnNames, tClass.getSimpleName()); // Set up the view with the generated data
    }
    private void setupView(Object[][] data, String[] columnNames, String tClass){
        this.setTitle("View " + tClass + "s");
        this.setLocationRelativeTo(null);
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        StartController.customizeComponent(table);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(128, 0, 128));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(200, 160, 255));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(200, 160, 255));

        this.getContentPane().setBackground(new Color(200, 160, 255));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(scrollPane);
        this.setVisible(true);
        this.pack();
    }
}

package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteController<T> extends JFrame {
    private JButton delete = new JButton("Delete");
    private JComboBox comboBox;
    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();
    public DeleteController(Class<T> tClass){
        List<T> list = null;
        if(tClass.isAssignableFrom(Product.class)) {
            list = (List<T>) productBLL.findAll();
        } else if(tClass.isAssignableFrom(Client.class)) {
            list = (List<T>) clientBLL.findAll();
        }

        String[] array = new String[list.size()];
        for(T object : list)
            array[list.indexOf(object)] = object.toString();
        deleteView(array);
        List<T> finalList = list;
        delete.addActionListener(e -> handleDeleteButton(finalList, tClass));
    }

    private void handleDeleteButton(List<T> list, Class<T> tClass) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            T t = list.get(selectedIndex);
            if (tClass.isAssignableFrom(Client.class))
                clientBLL.delete((Client) t);
            else if (tClass.isAssignableFrom(Product.class))
                productBLL.delete((Product) t);
            list.remove(t);
            comboBox.removeItemAt(selectedIndex);
        }
    }

    private void deleteView(String[] objects){
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));
        this.setTitle("Delete object");
        comboBox = new JComboBox(objects);
        StartController.customizeComponent(comboBox);

        delete.setBackground(new Color(128, 0, 128));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Arial", Font.BOLD, 14));
        delete.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(200, 160, 255));
        buttonPanel.add(delete);

        this.add(comboBox);
        this.add(buttonPanel);
        this.getContentPane().setBackground(new Color(200, 160, 255));
        this.setVisible(true);
        this.pack();
    }
}

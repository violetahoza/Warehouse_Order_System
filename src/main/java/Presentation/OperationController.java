package Presentation;

import javax.swing.*;
import java.awt.*;

public class OperationController<T> extends JFrame {
    JButton add, edit, delete, viewAll;
    public OperationController(Class<T> tClass){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        add = new JButton("New " + tClass.getSimpleName());
        edit = new JButton("Edit " + tClass.getSimpleName());
        delete = new JButton("Delete " + tClass.getSimpleName());
        viewAll = new JButton("View all " + tClass.getSimpleName() + "s");

        add.addActionListener(e -> handleOperation("Add", tClass));
        edit.addActionListener(e -> handleOperation("Edit", tClass));
        delete.addActionListener(e -> handleOperation("Delete", tClass));
        viewAll.addActionListener(e -> handleOperation("ViewAll", tClass));

        this.add(add);
        this.add(edit);
        this.add(delete);
        this.add(viewAll);

        this.setVisible(true);
        this.pack();
    }
    private void handleOperation(String operation, Class<?> type) {
        switch (operation) {
            case "Add":
                new AddController<>(type);
                break;
            case "Edit":
                new EditController<>(type);
                break;
            case "Delete":
                new DeleteController<>(type);
                break;
            case "ViewAll":
                new TableController<>(type);
                break;
            default:
                break;
        }
    }
}

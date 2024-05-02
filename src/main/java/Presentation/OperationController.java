package Presentation;

import javax.swing.*;
import java.awt.*;

public class OperationController<T> extends JFrame {
    JButton add, edit, delete, view;
    public OperationController(Class<T> tClass){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(4, 1));
        this.setPreferredSize(new Dimension(350, 200));
        this.setTitle(tClass.getSimpleName() + " operations");

        add = new JButton("Add " + tClass.getSimpleName());
        edit = new JButton("Edit " + tClass.getSimpleName());
        delete = new JButton("Delete " + tClass.getSimpleName());
        view = new JButton("View " + tClass.getSimpleName() + "s");

        add.addActionListener(e -> handleOperation("Add", tClass));
        edit.addActionListener(e -> handleOperation("Edit", tClass));
        delete.addActionListener(e -> handleOperation("Delete", tClass));
        view.addActionListener(e -> handleOperation("View", tClass));

        StartController.customizeComponent(add);
        StartController.customizeComponent(edit);
        StartController.customizeComponent(view);
        StartController.customizeComponent(delete);

        this.add(add);
        this.add(edit);
        this.add(delete);
        this.add(view);

        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }
    private void handleOperation(String operation, Class<T> type) {
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
            case "View":
                new ViewAllController<>(type);
                break;
            default:
                break;
        }
    }
}

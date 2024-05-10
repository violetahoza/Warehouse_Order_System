package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * OperationController class represents the window for performing CRUD (Create, Read, Update, Delete) operations on a specific type of object.
 * @param <T> The type of object on which the operations are performed.
 */
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

    /**
     * Handles the specified operation on the given type of object.
     * @param operation The operation to perform (e.g., Add, Edit, Delete, View).
     * @param type The class type of the object on which the operation is performed.
     */
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

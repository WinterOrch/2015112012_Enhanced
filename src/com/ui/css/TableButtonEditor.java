package com.ui.css;

import com.ui.component.TableButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TableButtonEditor extends DefaultCellEditor{
    private TableButton button;
    private MyEvent event;

    private TableButtonEditor() {
        super(new JTextField());
        this.setClickCountToStart(1);
        button = new TableButton();
        button.addActionListener(e -> event.invoke(e));

    }

    public TableButtonEditor(MyEvent e) {
        this();
        this.event = e;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        button.setRow(row);
        button.setColumn(column);
        return button;
    }

    public abstract static class MyEvent {
        public abstract void invoke(ActionEvent e);
    }
}

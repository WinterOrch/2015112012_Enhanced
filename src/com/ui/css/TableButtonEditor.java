package com.ui.css;

import com.ui.component.TableButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TableButtonEditor extends DefaultCellEditor{
    private TableButton button;
    private MyEvent event;

    public TableButtonEditor() {
        super(new JTextField());
        this.setClickCountToStart(1);
        button = new TableButton();
        button.addActionListener(e -> {
            //这里调用自定义的事件处理方法
            event.invoke(e);
        });

    }

    public TableButtonEditor(MyEvent e) {
        this();
        this.event = e;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
//      setClickCountToStart(1);
//将这个被点击的按钮所在的行和列放进button里面
        button.setRow(row);
        button.setColumn(column);
        return button;
    }

    public abstract static class MyEvent {
        public abstract void invoke(ActionEvent e);
    }
}

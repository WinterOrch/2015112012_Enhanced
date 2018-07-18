package com.ui.css;

import com.ui.component.TableButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableButtonRender implements TableCellRenderer {
    private TableButton button;
    private int rowLength;

    public TableButtonRender(int RowLength) {
        button = new TableButton();
        rowLength = RowLength;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if( row < rowLength )
            return button;
        else
            return null;
    }
}

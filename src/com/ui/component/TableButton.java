package com.ui.component;

import com.ui.constant.UIConstant;

import javax.swing.*;

public class TableButton extends JButton{
    private int row;
    private int column;

    public TableButton() {
        super("Get", UIConstant.ICON_GET);
        this.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        this.setFont(UIConstant.FONT_BUTTON);
        this.setFocusable(false);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}

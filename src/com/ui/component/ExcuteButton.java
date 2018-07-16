package com.ui.component;

import com.ui.constant.UIConstant;
import com.ui.css.RoundBorder;

import javax.swing.*;

public class ExcuteButton extends JButton{
    private float strokeWidth;

    public ExcuteButton(String text, float stroke) {
        super(text);

        strokeWidth = stroke;
        initialize();
    }

    private void initialize(){

        this.setBorder(new RoundBorder(UIConstant.NAVI_BAR_BACK_COLOR, strokeWidth));
        this.setBackground(UIConstant.MAIN_BACK_COLOR);
        this.setOpaque(false);
        this.setFocusPainted(false);
        this.setFocusable(true);
        this.setForeground(UIConstant.NAVI_BAR_BACK_COLOR);
        this.setFont(UIConstant.FONT_BUTTON);

    }

    public void setDisabled() {
        this.setEnabled(false);
        this.setBorder(new RoundBorder(UIConstant.DISABLED_BUTTON_COLOR, strokeWidth));
    }

    public void setEnabled() {
        this.setEnabled(true);
        this.setBorder(new RoundBorder(UIConstant.NAVI_BAR_BACK_COLOR, strokeWidth));
    }
}

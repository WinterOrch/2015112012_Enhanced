package com.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * Component of Navigation Bar
 * @author Frankel.Y
 *  23:27 2018/4/28
 */
public class NaviIconButton extends JButton{
    private ImageIcon buttonEnable, buttonReady;
    private String naviTip;

    /**
     * Constructor
     * created in 23:35 2018/4/28
     * @param imageNormal   Normal
     * @param imageReady    Passed
     * @param imageEnable   Pressed
     * @param tip           Tip
     */
    public NaviIconButton(ImageIcon imageNormal, ImageIcon imageEnable, ImageIcon imageReady, String tip) {

        super(imageNormal);

        this.buttonEnable = imageEnable;
        this.buttonReady = imageReady;
        this.naviTip = tip;

        initialize();
    }

    private void initialize(){

        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusable(true);
        this.setMargin(new Insets(0, 0, 0, 0));

        this.setRolloverIcon(buttonReady);
        this.setSelectedIcon(buttonEnable);
        this.setPressedIcon(buttonEnable);
        //this.setDisabledIcon(buttonDisabled);

        if(!naviTip.isEmpty()){
            this.setToolTipText(naviTip);
        }
    }

}

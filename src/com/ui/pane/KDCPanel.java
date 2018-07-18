package com.ui.pane;

import com.ui.constant.UIConstant;

import javax.swing.*;
import java.awt.*;

public class KDCPanel extends FunctionPanel{

    public KDCPanel() {
        super("kdc");
    }

    @Override
    public JPanel getCenterPanel() {
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());

        // Initialize
        com.ui.pane.kdc.KDCPanel mainPanel = new com.ui.pane.kdc.KDCPanel();

        panelCenter.add(mainPanel,BorderLayout.CENTER);

        return panelCenter;
    }

    @Override
    public void addListener() {

    }
}

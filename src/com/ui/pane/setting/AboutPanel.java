package com.ui.pane.setting;

import com.ui.component.NaviIconButton;
import com.ui.constant.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutPanel extends JPanel {
    private final JLabel labelURL = new JLabel("https://github.com/WinterOrch/2015112012_Enhanced");

    /**
     * Constructor
     */
    public AboutPanel() {
        initialize();
        addComponent();
    }

    private void initialize() {
        this.setBackground(UIConstant.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    private void addComponent() {

        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    private JPanel getCenterPanel() {
        // Center Panel
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(3, 1));

        // Icon And Version
        JPanel panelGridIcon = new JPanel();
        panelGridIcon.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelGridIcon.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 0));

        // Initialize Components
        NaviIconButton icon = new NaviIconButton(UIConstant.GITHUB, UIConstant.GITHUB,
                UIConstant.GITHUB, "");

        // Font
        labelURL.setFont(UIConstant.FONT_URI);

        // Set Size
        Dimension size = new Dimension(300, 40);
        labelURL.setPreferredSize(size);

        // Add Elements
        panelGridIcon.add(icon);
        panelGridIcon.add(labelURL);

        JPanel panelGridHelp = new JPanel();
        panelGridHelp.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelGridHelp.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 0));

        labelURL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                URI github;
                try {
                    github = new URI(labelURL.getText());
                    Desktop dst = Desktop.getDesktop();

                    if(Desktop.isDesktopSupported() && dst.isSupported(Desktop.Action.BROWSE)) {
                        dst.browse(github);
                    }
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panelCenter.add(panelGridIcon);
        return panelCenter;
    }

    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, UIConstant.MAIN_H_GAP, 15));

        JLabel labelInfo = new JLabel("2018 Summer | Frankel.Y");
        labelInfo.setFont(UIConstant.FONT_NORMAL);
        labelInfo.setForeground(Color.gray);

        panelDown.add(labelInfo);

        return panelDown;
    }
}

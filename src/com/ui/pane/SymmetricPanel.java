package com.ui.pane;

import com.system.PropertiesLocale;
import com.ui.MainWindow;
import com.ui.constant.UIConstant;
import com.ui.pane.symmetric.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SymmetricPanel extends FunctionPanel{
    private static JPanel listPanelEncrypt;
    private static JPanel listPanelDecrypt;
    private static JPanel symmetricMain;
    private static EncryptPanel symmetricPanelEncrypt;
    private static DecryptPanel symmetricPanelDecrypt;

    public SymmetricPanel() {
        super("symmetric");
    }

    /**
     * Center Main Pane
     * created in 23:32 2018/5/3
     */
    public JPanel getCenterPanel() {
        // Initialize
        symmetricPanelEncrypt = new EncryptPanel();
        symmetricPanelDecrypt = new DecryptPanel();
        // Center
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());
        // List
        JPanel panelList = new JPanel();
        Dimension preferredSize = new Dimension(245, UIConstant.MAIN_WINDOW_HEIGHT);
        panelList.setPreferredSize(preferredSize);
        panelList.setBackground(UIConstant.LIST_BACK_COLOR);
        panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        listPanelEncrypt = new JPanel();
        listPanelEncrypt.setBackground(UIConstant.PRESSED_BACK_COLOR);
        listPanelEncrypt.setLayout(UIConstant.listPanelLayout);
        listPanelEncrypt.setPreferredSize(UIConstant.LIST_ITEM_SIZE);
        listPanelDecrypt = new JPanel();
        listPanelDecrypt.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        listPanelDecrypt.setLayout(UIConstant.listPanelLayout);
        listPanelDecrypt.setPreferredSize(UIConstant.LIST_ITEM_SIZE);

        Font fontListItem = new Font(PropertiesLocale.getProperty("UI.FONT"), Font.PLAIN, 15);
        JLabel labelEncrypt = new JLabel(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT"));
        JLabel labelDecrypt = new JLabel(PropertiesLocale.getProperty("UI.SYMMETRIC.DECRYPT"));
        labelEncrypt.setFont(fontListItem);
        labelDecrypt.setFont(fontListItem);
        labelEncrypt.setForeground(Color.white);
        labelDecrypt.setForeground(Color.white);
        listPanelEncrypt.add(labelEncrypt);
        listPanelDecrypt.add(labelDecrypt);

        panelList.add(listPanelEncrypt);
        panelList.add(listPanelDecrypt);

        symmetricMain = new JPanel();
        symmetricMain.setBackground(UIConstant.MAIN_BACK_COLOR);
        symmetricMain.setLayout(new BorderLayout());
        symmetricMain.add(symmetricPanelEncrypt);
        //TODO
        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(symmetricMain, BorderLayout.CENTER);


        return panelCenter;
    }

    /**
     * Add Listener
     * created in 23:34 2018/5/3
     */
    public void addListener() {
        listPanelEncrypt.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelEncrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelEncrypt.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!listPanelEncrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelEncrypt.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelEncrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelEncrypt.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    listPanelDecrypt.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    SymmetricPanel.symmetricMain.removeAll();
                    SymmetricPanel.symmetricMain.add(symmetricPanelEncrypt);
                    symmetricPanelEncrypt.checkConfig();
                    MainWindow.symmetricPanel.updateUI();
                }
            }
        });

        listPanelDecrypt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelDecrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelEncrypt.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelDecrypt.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    SymmetricPanel.symmetricMain.removeAll();
                    SymmetricPanel.symmetricMain.add(symmetricPanelDecrypt);
                    symmetricPanelDecrypt.checkConfig();
                    MainWindow.symmetricPanel.updateUI();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!listPanelDecrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelDecrypt.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelDecrypt.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelDecrypt.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
        });
    }
}

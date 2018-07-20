package com.ui.pane;

import com.system.PropertiesLocale;
import com.ui.MainWindow;
import com.ui.constant.UIConstant;
import com.ui.pane.caeser.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CaeserPanel extends FunctionPanel {
    private static JPanel listPanelText;
    private static JPanel listPanelFile;
    private static JPanel ceaserMain;
    private static Text caeserPanelText;
    private static File caeserPanelFile;

    public CaeserPanel() {
        super("caeser");
    }

    /**
     * Center Main Pane
     * created in 23:32 2018/5/3
     */
    public JPanel getCenterPanel() {
        // Initialize
        caeserPanelText = new Text();
        caeserPanelFile = new File();
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

        listPanelText = new JPanel();
        listPanelText.setBackground(UIConstant.PRESSED_BACK_COLOR);
        listPanelText.setLayout(UIConstant.listPanelLayout);
        listPanelText.setPreferredSize(UIConstant.LIST_ITEM_SIZE);
        listPanelFile = new JPanel();
        listPanelFile.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        listPanelFile.setLayout(UIConstant.listPanelLayout);
        listPanelFile.setPreferredSize(UIConstant.LIST_ITEM_SIZE);

        Font fontListItem = new Font(PropertiesLocale.getProperty("UI.FONT"), Font.PLAIN, 15);
        JLabel labelEncrypt = new JLabel(PropertiesLocale.getProperty("UI.CAESER.TEXT"));
        JLabel labelDecrypt = new JLabel(PropertiesLocale.getProperty("UI.CAESER.FILE"));
        labelEncrypt.setFont(fontListItem);
        labelDecrypt.setFont(fontListItem);
        labelEncrypt.setForeground(Color.white);
        labelDecrypt.setForeground(Color.white);
        listPanelText.add(labelEncrypt);
        listPanelFile.add(labelDecrypt);

        panelList.add(listPanelText);
        panelList.add(listPanelFile);

        ceaserMain = new JPanel();
        ceaserMain.setBackground(UIConstant.MAIN_BACK_COLOR);
        ceaserMain.setLayout(new BorderLayout());
        ceaserMain.add(caeserPanelText);
        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(ceaserMain, BorderLayout.CENTER);


        return panelCenter;
    }

    /**
     * Add Listener
     * created in 23:34 2018/5/3
     */
    public void addListener() {
        listPanelText.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelText.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelText.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!listPanelText.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelText.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelText.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelText.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    listPanelFile.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    CaeserPanel.ceaserMain.removeAll();
                    CaeserPanel.ceaserMain.add(caeserPanelText);
                    MainWindow.symmetricPanel.updateUI();
                }
            }
        });

        listPanelFile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelFile.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelText.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelFile.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    CaeserPanel.ceaserMain.removeAll();
                    CaeserPanel.ceaserMain.add(caeserPanelFile);
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
                if(!listPanelFile.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelFile.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelFile.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelFile.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
        });
    }
}

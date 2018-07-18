package com.ui.pane;

import com.system.PropertiesLocale;
import com.ui.MainWindow;
import com.ui.constant.UIConstant;
import com.ui.pane.exchange.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExchangePanel extends FunctionPanel {
    private static JPanel listPanelSource;
    private static JPanel listPanelTarget;
    private static JPanel listPanelLocal;
    private static JPanel ExchangePanelMain;
    private static SourcePanel exchangePanelSource;
    private static TargetPanel exchangePanelTarget;
    private static LocalPanel exchangePanelLocal;
    
    public ExchangePanel() {
        super("exchange");
    }

    /**
     * Center Main Pane
     * created in 23:32 2018/5/3
     */
    public JPanel getCenterPanel() {
        // Initialize
        exchangePanelSource = new SourcePanel();
        exchangePanelTarget = new TargetPanel();
        exchangePanelLocal = new LocalPanel();
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

        listPanelSource = new JPanel();
        listPanelSource.setBackground(UIConstant.PRESSED_BACK_COLOR);
        listPanelSource.setLayout(UIConstant.listPanelLayout);
        listPanelSource.setPreferredSize(UIConstant.LIST_ITEM_SIZE);
        listPanelTarget = new JPanel();
        listPanelTarget.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        listPanelTarget.setLayout(UIConstant.listPanelLayout);
        listPanelTarget.setPreferredSize(UIConstant.LIST_ITEM_SIZE);
        listPanelLocal = new JPanel();
        listPanelLocal.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        listPanelLocal.setLayout(UIConstant.listPanelLayout);
        listPanelLocal.setPreferredSize(UIConstant.LIST_ITEM_SIZE);

        Font fontListItem = new Font(PropertiesLocale.getProperty("UI.FONT"), Font.PLAIN, 15);
        JLabel labelSource = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE"));
        JLabel labelTarget = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.TARGET"));
        JLabel labelLocal = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL"));
        labelSource.setFont(fontListItem);
        labelTarget.setFont(fontListItem);
        labelLocal.setFont(fontListItem);
        labelSource.setForeground(Color.white);
        labelTarget.setForeground(Color.white);
        labelLocal.setForeground(Color.white);
        listPanelSource.add(labelSource);
        listPanelTarget.add(labelTarget);
        listPanelLocal.add(labelLocal);

        panelList.add(listPanelSource);
        panelList.add(listPanelTarget);
        panelList.add(listPanelLocal);

        ExchangePanelMain = new JPanel();
        ExchangePanelMain.setBackground(UIConstant.MAIN_BACK_COLOR);
        ExchangePanelMain.setLayout(new BorderLayout());
        ExchangePanelMain.add(exchangePanelSource);
        //TODO
        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(ExchangePanelMain, BorderLayout.CENTER);

        return panelCenter;
    }

    /**
     * Add Listener
     * created in 23:34 2018/5/3
     */
    public void addListener() {
        listPanelSource.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelSource.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelSource.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!listPanelSource.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelSource.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelSource.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelSource.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    listPanelTarget.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelLocal.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    ExchangePanel.ExchangePanelMain.removeAll();
                    ExchangePanel.ExchangePanelMain.add(exchangePanelSource);
                    MainWindow.exchangePanel.updateUI();
                    exchangePanelSource.refreshData();
                }
            }
        });

        listPanelLocal.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelLocal.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelSource.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelTarget.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelLocal.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    ExchangePanel.ExchangePanelMain.removeAll();
                    ExchangePanel.ExchangePanelMain.add(exchangePanelLocal);
                    exchangePanelLocal.checkConfig();
                    MainWindow.exchangePanel.updateUI();
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
                if(!listPanelLocal.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelLocal.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelLocal.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelLocal.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
        });

        listPanelTarget.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!listPanelTarget.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelSource.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    listPanelTarget.setBackground(UIConstant.PRESSED_BACK_COLOR);
                    listPanelLocal.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                    ExchangePanel.ExchangePanelMain.removeAll();
                    ExchangePanel.ExchangePanelMain.add(exchangePanelTarget);
                    MainWindow.exchangePanel.updateUI();
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
                if(!listPanelTarget.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelTarget.setBackground(UIConstant.ROLL_OVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!listPanelTarget.getBackground().equals(UIConstant.PRESSED_BACK_COLOR)){
                    listPanelTarget.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
                }
            }
        });
    }
}

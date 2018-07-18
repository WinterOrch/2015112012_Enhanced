package com.ui.pane;

import com.system.PropertiesLocale;
import com.ui.MainWindow;
import com.ui.component.NaviIconButton;
import com.ui.constant.UIConstant;

import javax.swing.*;
import java.awt.*;

/**
 * Navigation Bar of The Main Window
 * @author Frankel.Y
 * Created in 0:46 2018/4/30
 */
public class NaviBar extends JPanel{
    private static NaviIconButton exchangeButton;
    private static NaviIconButton symEncryptButton;
    private static NaviIconButton kdcButton;
    private static NaviIconButton settingButton;

    public NaviBar() {
        initialize();
        addButton();
        addListener();
    }

    private void initialize(){
        Dimension preferredSize = new Dimension(UIConstant.NAVI_BAR_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    private void addButton(){

        JPanel upPanel = new JPanel();
        upPanel.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        upPanel.setLayout(new FlowLayout(FlowLayout.CENTER, -2, -4));
        JPanel downPanel = new JPanel();
        downPanel.setBackground(UIConstant.NAVI_BAR_BACK_COLOR);
        downPanel.setLayout(new BorderLayout(0, 0));

        exchangeButton = new NaviIconButton(UIConstant.ICON_EXCHANGE, UIConstant.ICON_EXCHANGE_PRESSED,
                UIConstant.ICON_EXCHANGE_READY, PropertiesLocale.getProperty("UI.EXCHANGE.TITLE"));
        symEncryptButton = new NaviIconButton(UIConstant.ICON_SYMMETRIC, UIConstant.ICON_SYMMETRIC_PRESSED,
                UIConstant.ICON_SYMMETRIC_READY, PropertiesLocale.getProperty("UI.SYMMETRIC.TITLE"));
        kdcButton = new NaviIconButton(UIConstant.ICON_KDC, UIConstant.ICON_KDC_PRESSED,
                UIConstant.ICON_KDC_READY, PropertiesLocale.getProperty("UI.KDC.TITLE"));
        settingButton = new NaviIconButton(UIConstant.ICON_SETTING, UIConstant.ICON_SETTING_PRESSED,
                UIConstant.ICON_SETTING_READY, PropertiesLocale.getProperty("UI.SETTING.TITLE"));

        upPanel.add(exchangeButton);
        upPanel.add(symEncryptButton);
        upPanel.add(kdcButton);

        downPanel.add(settingButton, BorderLayout.SOUTH);
        this.add(upPanel);
        this.add(downPanel);
    }

    private void addListener(){
        exchangeButton.addActionListener(e -> {
            exchangeButton.setIcon(UIConstant.ICON_EXCHANGE_PRESSED);
            //TODO
            symEncryptButton.setIcon(UIConstant.ICON_SYMMETRIC);
            kdcButton.setIcon(UIConstant.ICON_KDC);
            settingButton.setIcon(UIConstant.ICON_SETTING);

            MainWindow.centerPanel.removeAll();
            //MessagePanel.getContent();
            MainWindow.centerPanel.add(MainWindow.exchangePanel, BorderLayout.CENTER);
            MainWindow.centerPanel.updateUI();
        });

        symEncryptButton.addActionListener(e -> {
            exchangeButton.setIcon(UIConstant.ICON_EXCHANGE);
            //TODO
            symEncryptButton.setIcon(UIConstant.ICON_SYMMETRIC_PRESSED);
            kdcButton.setIcon(UIConstant.ICON_KDC);
            settingButton.setIcon(UIConstant.ICON_SETTING);

            MainWindow.centerPanel.removeAll();
            //PicturePanel.getContent();
            MainWindow.centerPanel.add(MainWindow.symmetricPanel, BorderLayout.CENTER);
            MainWindow.centerPanel.updateUI();
        });

        settingButton.addActionListener(e -> {
            exchangeButton.setIcon(UIConstant.ICON_EXCHANGE);
            symEncryptButton.setIcon(UIConstant.ICON_SYMMETRIC);
            kdcButton.setIcon(UIConstant.ICON_KDC);
            //TODO
            settingButton.setIcon(UIConstant.ICON_SETTING_PRESSED);

            MainWindow.centerPanel.removeAll();
            //SettingPanel.getContent();
            MainWindow.centerPanel.add(MainWindow.settingPanel, BorderLayout.CENTER);
            MainWindow.centerPanel.updateUI();
        });

        kdcButton.addActionListener(e -> {
            exchangeButton.setIcon(UIConstant.ICON_EXCHANGE);
            symEncryptButton.setIcon(UIConstant.ICON_SYMMETRIC);
            kdcButton.setIcon(UIConstant.ICON_KDC_PRESSED);
            //TODO
            settingButton.setIcon(UIConstant.ICON_SETTING);

            MainWindow.centerPanel.removeAll();
            //SettingPanel.getContent();
            MainWindow.centerPanel.add(MainWindow.kdcPanel, BorderLayout.CENTER);
            MainWindow.centerPanel.updateUI();
            com.ui.pane.kdc.KDCPanel.refreshData();
        });
    }

    /**
     * Hot Change
     * created in 23:26 2018/4/30
     */
    public static void refreshLocale(){
        //TODO
        exchangeButton.setToolTipText(PropertiesLocale.getProperty("UI.EXCHANGE.TITLE"));
        symEncryptButton.setToolTipText(PropertiesLocale.getProperty("UI.SYMMETRIC.TITLE"));
        kdcButton.setToolTipText(PropertiesLocale.getProperty("UI.KDC.TITLE"));
        settingButton.setToolTipText(PropertiesLocale.getProperty("UI.SETTING.TITLE"));
    }
}

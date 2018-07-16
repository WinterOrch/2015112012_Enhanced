package com.ui;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.system.leveldb.Data;
import com.ui.constant.UIConstant;
import com.ui.pane.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MainWindow {
    public static JFrame frame;

    public static JPanel mainPanel;
    public static JPanel centerPanel;
    public static ExchangePanel exchangePanel;
    public static SymmetricPanel symmetricPanel;
    public static KDCPanel kdcPanel;

    public static SettingPanel settingPanel;

    /**
     * Constructor
     * created in 0:38 2018/4/30
     */
    private MainWindow(){
        initialize();
    }

    /**
     * Initialize
     * created in 0:39 2018/4/30
     */
    private void initialize(){
        // Initialize Properties
        PropertiesLocale.initialize();

        // Set Default Style
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Initialize Frame
        frame = new JFrame();
        frame.setBounds(UIConstant.MAIN_WINDOW_X, UIConstant.MAIN_WINDOW_Y, UIConstant.MAIN_WINDOW_WIDTH,
                UIConstant.MAIN_WINDOW_HEIGHT);
        frame.setTitle(SystemConstant.APP_NAME + "  -  " + SystemConstant.APP_VERSION);
        frame.setIconImage(UIConstant.IMAGE_ICON);
        frame.setBackground(UIConstant.MAIN_BACK_COLOR);
        mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        NaviBar naviBar = new NaviBar();
        exchangePanel = new ExchangePanel();
        symmetricPanel = new SymmetricPanel();
        settingPanel = new SettingPanel();
        kdcPanel = new KDCPanel();

        mainPanel.add(naviBar, BorderLayout.WEST);

        centerPanel = new JPanel(true);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(exchangePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

        frame.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub
                try {
                    Data.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void freshLocale(){
        NaviBar.refreshLocale();
        settingPanel.refreshLocale();
        exchangePanel.refreshLocale();
        symmetricPanel.refreshLocale();
        kdcPanel.refreshLocale();
        //TODO
    }

    /**
     * Main Method
     * created in 0:35 2018/4/30
     */
    public static void main(String[] args) {
        try {
            Data.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                MainWindow window = new MainWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

package com.ui.constant;

import com.ui.MainWindow;
import static com.system.PropertiesLocale.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UIConstant {
    /**
     * Size of Main Window
     */
    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 100;
    public final static int MAIN_WINDOW_WIDTH = 1025;
    public final static int MAIN_WINDOW_HEIGHT = 636;

    /**
     * Icon of Main Window
     */
    public final static Image IMAGE_ICON = Toolkit.getDefaultToolkit()
            .getImage(MainWindow.class.getResource("/icon/icon.png"));

    /**
     * Background Color of The Main Window
     */
    public final static Color MAIN_BACK_COLOR = Color.WHITE;

    /**
     * Font
     */
    // Title
    public final static Font FONT_TITLE = new Font(getProperty("UI.FONT.FAMILY"), Font.PLAIN, 27);
    // Common
    public final static Font FONT_NORMAL = new Font(getProperty("UI.FONT.FAMILY"), Font.PLAIN, 13);
    // Button
    public final static Font FONT_BUTTON = new Font(getProperty("UI.FONT.FAMILY"), Font.BOLD, 13);

    /**
     * Size And Color of Navigation Bar
     */
    public final static Color NAVI_BAR_BACK_COLOR = new Color(33, 150, 243);
    public final static Color PRESSED_BACK_COLOR = new Color(38, 157, 243);
    public final static Color LIST_BACK_COLOR = new Color(62, 62, 62);
    public final static Color ROLL_OVER_COLOR = new Color(97, 176, 239);
    public final static int NAVI_BAR_WIDTH = 48;
    public final static Color DISABLED_BUTTON_COLOR = new Color(165, 165,165);

    /**
     * Navigation Icons
     */
    public final static ImageIcon ICON_SETTING = new ImageIcon(MainWindow.class.getResource("/icon/settings-normal.png"));
    public final static ImageIcon ICON_SETTING_READY = new ImageIcon(MainWindow.class.getResource("/icon/settings-ready.png"));
    public final static ImageIcon ICON_SETTING_PRESSED = new ImageIcon(MainWindow.class.getResource("/icon/settings-pressed.png"));

    public final static ImageIcon ICON_SYMMETRIC = new ImageIcon(MainWindow.class.getResource("/icon/picture-normal.png"));
    public final static ImageIcon ICON_SYMMETRIC_READY = new ImageIcon(MainWindow.class.getResource("/icon/picture-ready.png"));
    public final static ImageIcon ICON_SYMMETRIC_PRESSED = new ImageIcon(MainWindow.class.getResource("/icon/picture-pressed.png"));

    public final static ImageIcon ICON_EXCHANGE = new ImageIcon(MainWindow.class.getResource("/icon/message-normal.png"));
    public final static ImageIcon ICON_EXCHANGE_READY = new ImageIcon(MainWindow.class.getResource("/icon/message-ready.png"));
    public final static ImageIcon ICON_EXCHANGE_PRESSED = new ImageIcon(MainWindow.class.getResource("/icon/message-pressed.png"));

    public final static ImageIcon ICON_KDC = new ImageIcon(MainWindow.class.getResource("/icon/signiture-normal.png"));
    public final static ImageIcon ICON_KDC_READY = new ImageIcon(MainWindow.class.getResource("/icon/signiture-ready.png"));
    public final static ImageIcon ICON_KDC_PRESSED = new ImageIcon(MainWindow.class.getResource("/icon/signiture-pressed.png"));

    public final static URL PLACE_HOLDER = MainWindow.class.getResource("/icon/placeholder.png");

    /**
     * Background Color of Table Line
     */
    public final static Color TABLE_LINE_COLOR = new Color(229, 229, 229);

    /**
     * Style
     */
    // Gap of Main Pane
    public final static int MAIN_H_GAP = 25;
    // Size of List Label
    public final static Dimension LIST_ITEM_SIZE = new Dimension(245, 48);
    // Size of Label
    public final static Dimension LABLE_SIZE = new Dimension(1300, 30);
    // Size of Item Label
    public final static Dimension LABLE_SIZE_ITEM = new Dimension(93, 28);
    // Size of Item Text Field
    public final static Dimension TEXT_FIELD_SIZE_ITEM = new Dimension(525, 24);
    public final static Dimension REVERSED_TEXT_FIELD_SIZE_ITEM = new Dimension(445,24);
    public final static Dimension PROGRESS_BAR_SIZE = new Dimension(825,5);
    public final static Dimension PICTURE_SIZE = new Dimension(300,200);
    // Size of Radio
    public final static Dimension RADIO_SIZE = new Dimension(1300, 60);
    // Size of Advanced Option Pane
    public final static Dimension PANEL_ITEM_SIZE = new Dimension(1300, 40);
    // Layout
    public final static FlowLayout listPanelLayout = new FlowLayout(FlowLayout.LEFT, 30, 13);

}

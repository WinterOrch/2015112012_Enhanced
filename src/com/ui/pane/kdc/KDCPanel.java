package com.ui.pane.kdc;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.system.leveldb.Data;
import com.system.leveldb.Output;
import com.tool.coding.Serialize;
import com.tool.security.KeyPairUtility;
import com.ui.MainWindow;
import com.ui.component.PaddingFrame;
import com.ui.component.TableButton;
import com.ui.constant.UIConstant;
import com.ui.css.TableButtonEditor;
import com.ui.css.TableButtonRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class KDCPanel extends JPanel{
    private Logger logger;

    private JLabel identityLabel = new JLabel();
    private JTextField inputIdentityText = new JTextField();
    private JButton searchButton = new JButton();

    private DefaultTableModel model;
    private JTable table;

    private JLabel tip;

    private ArrayList<String> result;

    public KDCPanel(){
        initialize();
        addComponents();
        addListener();
    }

    private void initialize(){
        logger = LoggerFactory.getLogger(com.ui.pane.kdc.KDCPanel.class);

        this.setBackground(UIConstant.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());

        this.refreshData();
    }

    private void addComponents(){
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);
    }

    private JPanel getCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(UIConstant.MAIN_BACK_COLOR);
        centerPanel.setLayout(new GridLayout(1,1));

        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 20));
        JPanel secondPanelGridSetting = new JPanel();
        secondPanelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
        secondPanelGridSetting.setLayout(new FlowLayout(FlowLayout.CENTER, UIConstant.MAIN_H_GAP, 7));
        
        identityLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.IDENTITY"));
        searchButton.setText(PropertiesLocale.getProperty("UI.SEARCH"));
                
        // Set Size
        identityLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputIdentityText.setPreferredSize(UIConstant.FULL_TEXT_FIELD_SIZE);
        searchButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        // Table
        String[][] tableData = new String[20][2];
        String[] tableTitles = {PropertiesLocale.getProperty("UI.KDC.TABLE.IDENTITY"),
                PropertiesLocale.getProperty("UI.KDC.TABLE.GET")};

        // Get Table Model
        model = new DefaultTableModel(tableData,tableTitles);

        table = new JTable(model);
        table.setPreferredSize(UIConstant.TABLE_SIZE);
        table.getColumnModel().getColumn(0).setPreferredWidth(700);
        table.getColumnModel().getColumn(1).setPreferredWidth(93);

        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(UIConstant.MAIN_BACK_COLOR);

        table.setFont(UIConstant.FONT_NORMAL);
        table.getTableHeader().setFont(UIConstant.FONT_NORMAL);

        table.setRowHeight(25);

        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        scrollPane.setBackground(UIConstant.MAIN_BACK_COLOR);
        scrollPane.setPreferredSize(UIConstant.TABLE_SIZE);
        scrollPane.setOpaque(true);

        // Set Font
        identityLabel.setFont(UIConstant.FONT_NORMAL);
        inputIdentityText.setFont(UIConstant.FONT_NORMAL);
        searchButton.setFont(UIConstant.FONT_NORMAL);

        // Add Components
        panelGridSetting.add(identityLabel);
        panelGridSetting.add(inputIdentityText);
        panelGridSetting.add(searchButton);

        panelGridSetting.add(scrollPane);

        centerPanel.add(panelGridSetting);

        return centerPanel;
    }

    private JPanel getDownPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setBackground(UIConstant.MAIN_BACK_COLOR);
        southPanel.setLayout(new GridLayout(1,2));

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        tip = new JLabel("");
        tip.setFont(UIConstant.FONT_NORMAL);
        westPanel.add(tip);

        JPanel eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));


        southPanel.add(westPanel);
        southPanel.add(eastPanel);
        return southPanel;
    }

    @SuppressWarnings("Duplicates")
    private void addListener() {
        searchButton.addActionListener(e -> {
            if(inputIdentityText.getText().trim().isEmpty()) {
                tip.setText(PropertiesLocale.getProperty("UI.KDC.IDENTITY.EMPTY"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.KDC.IDENTITY.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                // Record Search In Log
                logger.info("Search For {}",inputIdentityText.getText().trim());

                // Clear Table
                model.setRowCount(0);

                // Perform Search
                result = Output.getSearchResult(inputIdentityText.getText().trim());
                int resultLength = 0;

                // Add Results To Table
                for (String ele : result) {
                    resultLength++;
                    model.addRow(new String[]{ele, null});
                }

                if(0 == resultLength) {
                    // INFO: No Result Found
                    table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer());
                    tip.setText(PropertiesLocale.getProperty("UI.KDC.TABLE.SEARCH.NONE"));
                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.KDC.TABLE.SEARCH.NONE"),
                            PropertiesLocale.getProperty("UI.MESSAGE.INFO"),JOptionPane.INFORMATION_MESSAGE);
                }else {
                    TableButtonEditor.MyEvent eve = new TableButtonEditor.MyEvent() {
                        @Override
                        public void invoke(ActionEvent e) {
                            TableButton button = (TableButton)e.getSource();

                            String target = (String)table.getValueAt(button.getRow(),0);
                            try {
                                byte[] key = target.getBytes(SystemConstant.DATABASE_CODE_FORMAT);
                                byte[] value = Data.get(key);
                                if(null == value) {
                                    // Something Wrong When Getting Item In Data Base
                                    tip.setText(PropertiesLocale.getProperty("UI.KDC.TABLE.GET.FAILURE"));
                                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.KDC.TABLE.GET.FAILURE"),
                                            PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                                }else {
                                    KeyPairUtility.saveSecretKeyFile(Serialize.byte2Map(value));
                                }
                            } catch (UnsupportedEncodingException e1) {
                                logger.error(e1.toString());
                            }
                        }
                    };

                    // Set Render And Editor To Activate Buttons
                    table.getColumnModel().getColumn(1).setCellRenderer(new TableButtonRender(resultLength));
                    table.getColumnModel().getColumn(1).setCellEditor(new TableButtonEditor(eve));
                }
            }
        });
    }

    public void refreshData() {
        PaddingFrame.setupAutoComplete(this.inputIdentityText, Output.getKeyList());
    }
}

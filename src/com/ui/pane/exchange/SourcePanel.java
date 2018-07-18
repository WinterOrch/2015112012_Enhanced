package com.ui.pane.exchange;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.system.leveldb.Data;
import com.system.leveldb.Output;
import com.tool.coding.Character;
import com.tool.coding.HexConver;
import com.tool.coding.Serialize;
import com.tool.security.KeyPairUtility;
import com.tool.security.dh.DiffieHellman;
import com.ui.MainWindow;
import com.ui.component.ExecuteButton;
import com.ui.component.PaddingFrame;
import com.ui.constant.UIConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SourcePanel extends JPanel {
        private Logger logger;

        private JLabel inputIdentityLabel = new JLabel();
        private JTextField inputIdentityText = new JTextField();
        private JCheckBox accessDB;

        private JLabel inputPassWordLabel = new JLabel();
        private JTextField inputPassWordText = new JTextField();
        private JButton inputRandomButton = new JButton();

        private JComboBox<String> saveKeyComboBox = new JComboBox<>();
        private JLabel saveKeyLabel = new JLabel();

        private JTextArea keyTextArea = new JTextArea(30,21);

        private ExecuteButton activateButton;
        private ExecuteButton saveButton;

        private JLabel tip;

        // Flags
        private Map<String,Object> keyMap;
        private boolean isKeyGenerated;

        public SourcePanel(){
            initialize();
            addComponents();
            addListener();
        }

        private void initialize(){
            logger = LoggerFactory.getLogger(com.ui.pane.exchange.SourcePanel.class);

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
            centerPanel.setLayout(new GridLayout(2,1));

            JPanel panelGridSetting = new JPanel();
            panelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
            panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 7));
            JPanel secondPanelGridSetting = new JPanel();
            secondPanelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
            secondPanelGridSetting.setLayout(new FlowLayout(FlowLayout.CENTER, UIConstant.MAIN_H_GAP, 7));

            inputIdentityText.setEnabled(false);
            saveKeyComboBox.setEnabled(false);

            inputIdentityLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.IDENTITY"));
            accessDB = new JCheckBox(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.ACCESS"),false);

            inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PASSWORD"));
            saveKeyLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE"));
            inputRandomButton.setText(PropertiesLocale.getProperty("UI.RANDOM"));

            saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                    + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PUBLIC_KEY"));
            saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                    + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PRIVATE_KEY"));
            saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                    + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.BOTH"));

            // Set Size
            inputIdentityLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
            inputIdentityText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
            accessDB.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

            inputPassWordLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
            inputPassWordText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
            inputRandomButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

            saveKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
            saveKeyComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

            accessDB.setBackground(UIConstant.MAIN_BACK_COLOR);
            accessDB.setOpaque(true);

            keyTextArea.setLineWrap(true);
            keyTextArea.setWrapStyleWord(true);
            keyTextArea.setBackground(UIConstant.MAIN_BACK_COLOR);
            keyTextArea.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
            keyTextArea.setOpaque(true);

            JScrollPane scrollPane = new JScrollPane(keyTextArea);
            scrollPane.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(UIConstant.LIST_BACK_COLOR),
                    PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.KEY_PAIR"),
                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_JUSTIFICATION,
                    UIConstant.FONT_NORMAL));
            scrollPane.setBackground(UIConstant.MAIN_BACK_COLOR);
            scrollPane.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
            scrollPane.setOpaque(true);

            // Set Font
            inputIdentityLabel.setFont(UIConstant.FONT_NORMAL);
            inputIdentityText.setFont(UIConstant.FONT_NORMAL);
            accessDB.setFont(UIConstant.FONT_NORMAL);

            inputPassWordLabel.setFont(UIConstant.FONT_NORMAL);
            inputPassWordText.setFont(UIConstant.FONT_NORMAL);
            inputRandomButton.setFont(UIConstant.FONT_BUTTON);

            saveKeyLabel.setFont(UIConstant.FONT_NORMAL);
            saveKeyComboBox.setFont(UIConstant.FONT_NORMAL);

            keyTextArea.setFont(UIConstant.FONT_TEXT_AREA);

            // Add Components
            panelGridSetting.add(inputIdentityLabel);
            panelGridSetting.add(inputIdentityText);
            panelGridSetting.add(accessDB);

            panelGridSetting.add(inputPassWordLabel);
            panelGridSetting.add(inputPassWordText);
            panelGridSetting.add(inputRandomButton);

            panelGridSetting.add(saveKeyLabel);
            panelGridSetting.add(saveKeyComboBox);

            secondPanelGridSetting.add(scrollPane);

            centerPanel.add(panelGridSetting);
            centerPanel.add(secondPanelGridSetting);
            return centerPanel;
        }

        private JPanel getDownPanel() {
            JPanel southPanel = new JPanel();
            southPanel.setBackground(UIConstant.MAIN_BACK_COLOR);
            southPanel.setLayout(new GridLayout(1,2));

            JPanel westPanel = new JPanel();
            westPanel.setOpaque(false);
            westPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

            tip = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.TIP.OVERALL"));
            tip.setFont(UIConstant.FONT_NORMAL);
            westPanel.add(tip);

            JPanel eastPanel = new JPanel();
            eastPanel.setOpaque(false);
            eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));

            activateButton = new ExecuteButton(PropertiesLocale.getProperty("UI.GENERATE"),2F);
            activateButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

            saveButton = new ExecuteButton(PropertiesLocale.getProperty("UI.SAVE"),2F);
            saveButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
            saveButton.setEnabled(false);

            eastPanel.add(activateButton);
            eastPanel.add(saveButton);

            southPanel.add(westPanel);
            southPanel.add(eastPanel);
            return southPanel;
        }

        @SuppressWarnings("Duplicates")
        private void addListener() {
            accessDB.addItemListener(e -> inputIdentityText.setEnabled(accessDB.isSelected()));

            inputRandomButton.addActionListener(e ->
                    inputPassWordText.setText(Character.getRandomString(KeyPairUtility.RANDOM_KEY_SIZE)));

            activateButton.addActionListener(e -> {
                if(inputPassWordText.getText().trim().isEmpty()) {
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PASSWORD.EMPTY"));
                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PASSWORD.EMPTY"),
                            PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                }else {
                    try {
                        keyMap = DiffieHellman.generateSourceKeyPair(inputPassWordText.getText().trim(),KeyPairUtility.DH_KEY_SIZE);
                    } catch (Exception e1) {
                        logger.error(e1.toString());
                    }

                    if(keyMap != null) {
                        tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.SUCCESS"));
                        byte[] publicKey = KeyPairUtility.getDHPublicKey(keyMap);
                        byte[] privateKey = KeyPairUtility.getDHPrivateKey(keyMap);
                        isKeyGenerated = true;
                        JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.SUCCESS"),
                                PropertiesLocale.getProperty("UI.MESSAGE.INFO"),JOptionPane.INFORMATION_MESSAGE);

                        keyTextArea.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PUBLIC_KEY") + ":\n"
                                + HexConver.byte2HexStr(publicKey, publicKey.length) + "\n\n"
                                + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PRIVATE_KEY") + ":\n"
                                + HexConver.byte2HexStr(privateKey, privateKey.length));

                        saveKeyComboBox.setEnabled(true);
                        saveButton.setEnabled(true);

                        if(accessDB.isSelected()) {
                            if(inputIdentityText.getText().trim().isEmpty()) {
                                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.IDENTITY.EMPTY"),
                                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                            }else {
                                String identity = inputIdentityText.getText().trim();

                                if(null != Data.get(identity.getBytes())) {
                                    Map<String,Object> output = new HashMap<>();
                                    DHPublicKey dhPublicKey = (DHPublicKey) keyMap.get(KeyPairUtility.DH_PUBLIC_KEY);
                                    output.put(KeyPairUtility.DH_PUBLIC_KEY, dhPublicKey);
                                    Data.put(identity.getBytes(), Serialize.map2Byte(output));

                                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.ACCESS.SUCCESS"),
                                            PropertiesLocale.getProperty("UI.MESSAGE.INFO"),JOptionPane.INFORMATION_MESSAGE);
                                }else {
                                    String[] option = {PropertiesLocale.getProperty("UI.MESSAGE.OPTION.OK"),
                                            PropertiesLocale.getProperty("UI.MESSAGE.OPTION.CANCEL")};
                                    int choice = JOptionPane.showOptionDialog(MainWindow.frame,
                                            PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.ACCESS.COVER"),
                                            PropertiesLocale.getProperty("UI.MESSAGE.OPTION"),JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,UIConstant.QUESTION, option, null);
                                    if(JOptionPane.OK_OPTION == choice) {
                                        Data.delete(identity.getBytes());

                                        Map<String,Object> output = new HashMap<>();
                                        DHPublicKey dhPublicKey = (DHPublicKey) keyMap.get(KeyPairUtility.DH_PUBLIC_KEY);
                                        output.put(KeyPairUtility.DH_PUBLIC_KEY, dhPublicKey);
                                        try {
                                            Data.put(identity.getBytes(SystemConstant.DATABASE_CODE_FORMAT), Serialize.map2Byte(output));
                                        } catch (UnsupportedEncodingException e1) {
                                            logger.error(e1.toString());
                                        }
                                    }else if(JOptionPane.CANCEL_OPTION == choice) {
                                        tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.ACCESS.WRONG"));
                                    }
                                }
                            }
                        }
                    }else {
                        tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.FAILURE"));
                        isKeyGenerated = false;
                        keyTextArea.setText("");
                        saveKeyComboBox.setEnabled(false);
                        saveButton.setEnabled(false);
                        JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.FAILURE"),
                                PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            saveButton.addActionListener(e -> {
                if(keyMap != null && !isKeyGenerated) {
                    Map<String,Object> output = new HashMap<>();

                    DHPublicKey dhPublicKey = (DHPublicKey) keyMap.get(KeyPairUtility.DH_PUBLIC_KEY);
                    DHPrivateKey dhPrivateKey = (DHPrivateKey) keyMap.get(KeyPairUtility.DH_PRIVATE_KEY);

                    switch (saveKeyComboBox.getSelectedIndex()){
                        case 0:
                            output.put(KeyPairUtility.DH_PUBLIC_KEY, dhPublicKey);
                            break;
                        case 1:
                            output.put(KeyPairUtility.DH_PRIVATE_KEY, dhPrivateKey);
                            break;
                        case 2:
                            output.put(KeyPairUtility.DH_PUBLIC_KEY, dhPublicKey);
                            output.put(KeyPairUtility.DH_PRIVATE_KEY, dhPrivateKey);
                            break;
                    }

                    KeyPairUtility.saveSecretKeyFile(output);
                }
            });
        }

        public void refreshData() {
            PaddingFrame.setupAutoComplete(inputIdentityText, Output.getKeyList());
        }
}

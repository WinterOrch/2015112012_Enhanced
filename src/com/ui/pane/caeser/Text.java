package com.ui.pane.caeser;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.tool.coding.HexConver;
import com.tool.security.KeyPairUtility;
import com.tool.security.caeser.Caeser;
import com.ui.MainWindow;
import com.ui.component.ExecuteButton;
import com.ui.constant.UIConstant;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class Text extends JPanel {
    private JLabel inputPassWordLabel = new JLabel();
    private JTextField inputPassWordText = new JTextField();
    private JButton inputRandomButton = new JButton();

    private JTextArea clearTextArea = new JTextArea(30,21);
    private JTextArea cipherTextArea = new JTextArea(30,21);

    private ExecuteButton encryptButton;
    private ExecuteButton decryptButton;
    
    public Text(){
        initialize();
        addComponents();
        addListener();
    }

    private void initialize(){
        this.setBackground(UIConstant.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
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
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 10));
        JPanel secondPanelGridSetting = new JPanel();
        secondPanelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
        secondPanelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 7));

        inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PASSWORD"));
        inputRandomButton.setText(PropertiesLocale.getProperty("UI.RANDOM"));
        
        // Set Size
        inputPassWordLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputPassWordText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputRandomButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        clearTextArea.setLineWrap(true);
        clearTextArea.setWrapStyleWord(true);
        clearTextArea.setBackground(UIConstant.MAIN_BACK_COLOR);
        clearTextArea.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        clearTextArea.setOpaque(true);

        cipherTextArea.setLineWrap(true);
        cipherTextArea.setWrapStyleWord(true);
        cipherTextArea.setBackground(UIConstant.MAIN_BACK_COLOR);
        cipherTextArea.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        cipherTextArea.setOpaque(true);

        JScrollPane scrollPaneClear = new JScrollPane(clearTextArea);
        scrollPaneClear.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIConstant.LIST_BACK_COLOR),
                PropertiesLocale.getProperty("UI,CAESER.CLEAR_TEXT"),
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_JUSTIFICATION,
                UIConstant.FONT_NORMAL));
        scrollPaneClear.setBackground(UIConstant.MAIN_BACK_COLOR);
        scrollPaneClear.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        scrollPaneClear.setOpaque(true);

        JScrollPane scrollPaneCipher = new JScrollPane(cipherTextArea);
        scrollPaneCipher.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIConstant.LIST_BACK_COLOR),
                PropertiesLocale.getProperty("UI,CAESER.CIPHER_TEXT"),
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_JUSTIFICATION,
                UIConstant.FONT_NORMAL));
        scrollPaneCipher.setBackground(UIConstant.MAIN_BACK_COLOR);
        scrollPaneCipher.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        scrollPaneCipher.setOpaque(true);

        // Set Font
        inputPassWordLabel.setFont(UIConstant.FONT_NORMAL);
        inputPassWordText.setFont(UIConstant.FONT_NORMAL);
        inputRandomButton.setFont(UIConstant.FONT_BUTTON);

        clearTextArea.setFont(UIConstant.FONT_TEXT_AREA);
        cipherTextArea.setFont(UIConstant.FONT_TEXT_AREA);

        // Add Components
        panelGridSetting.add(scrollPaneClear);

        panelGridSetting.add(inputPassWordLabel);
        panelGridSetting.add(inputPassWordText);
        panelGridSetting.add(inputRandomButton);

        secondPanelGridSetting.add(scrollPaneCipher);

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

        JPanel eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));

        encryptButton = new ExecuteButton(PropertiesLocale.getProperty("UI.CAESER.ENCRYPT"),2F);
        encryptButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        decryptButton = new ExecuteButton(PropertiesLocale.getProperty("UI.CAESER.DECRYPT"),2F);
        decryptButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        eastPanel.add(encryptButton);
        eastPanel.add(decryptButton);

        southPanel.add(westPanel);
        southPanel.add(eastPanel);
        return southPanel;
    }

    @SuppressWarnings("Duplicates")
    private void addListener() {
        inputRandomButton.addActionListener(e ->
                inputPassWordText.setText(com.tool.coding.Character.getRandomString(KeyPairUtility.RANDOM_KEY_SIZE)));

        encryptButton.addActionListener(e -> {
            if(clearTextArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.CAESER.ENCRYPT.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else if(inputPassWordText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.CAESER.PASSWORD.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                try {
                    byte[] result = Caeser.doFinal(
                            Caeser.getRealKey(inputPassWordText.getText().trim().getBytes(SystemConstant.DATABASE_CODE_FORMAT)),
                            clearTextArea.getText().trim().getBytes(SystemConstant.DATABASE_CODE_FORMAT));
                    cipherTextArea.setText(HexConver.byte2HexStr(result, result.length));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });

        decryptButton.addActionListener(e -> {
            if(cipherTextArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.CAESER.DECRYPT.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else if(!HexConver.checkHexStr(cipherTextArea.getText().trim())) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.CAESER.DECRYPT.WRONG"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else if(inputPassWordText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.CAESER.PASSWORD.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                try {
                    byte[] result = Caeser.doFinal(
                            Caeser.getRealKey(inputPassWordText.getText().trim().getBytes(SystemConstant.DATABASE_CODE_FORMAT)),
                            HexConver.hexStr2Bytes(cipherTextArea.getText().trim()));
                    clearTextArea.setText(new String(result,SystemConstant.DATABASE_CODE_FORMAT));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}

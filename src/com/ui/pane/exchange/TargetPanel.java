package com.ui.pane.exchange;

import com.system.PropertiesLocale;
import com.tool.coding.Character;
import com.tool.coding.HexConver;
import com.tool.security.KeyPairUtility;
import com.tool.security.dh.DiffieHellman;
import com.ui.MainWindow;
import com.ui.component.ExecuteButton;
import com.ui.constant.UIConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TargetPanel extends JPanel {
    private Logger logger;

    private JLabel inputPublicKeyLabel = new JLabel();
    private JTextField inputPublicKeyText = new JTextField();
    private JButton inputPublicKeyButton = new JButton();

    private JLabel inputPassWordLabel = new JLabel();
    private JTextField inputPassWordText = new JTextField();
    private JButton inputRandomButton = new JButton();

    private JLabel saveKeyLabel = new JLabel();
    private JComboBox<String> saveKeyComboBox = new JComboBox<>();

    private JTextArea keyTextArea = new JTextArea(30,21);

    private ExecuteButton activateButton;
    private ExecuteButton saveButton;

    private JLabel tip;

    // Flags
    private Map<String,Object> publicKeyMap;
    private boolean isPublicKeyLoaded;

    private Map<String,Object> generatedKeyMap;
    private boolean isKeyGenerated;

    public TargetPanel(){
        initialize();
        addComponents();
        addListener();
    }

    private void initialize(){
        logger = LoggerFactory.getLogger(TargetPanel.class);

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
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 7));
        JPanel secondPanelGridSetting = new JPanel();
        secondPanelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
        secondPanelGridSetting.setLayout(new FlowLayout(FlowLayout.CENTER, UIConstant.MAIN_H_GAP, 7));

        saveKeyComboBox.setEnabled(false);

        inputPublicKeyLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY"));
        inputPublicKeyButton.setText(PropertiesLocale.getProperty("UI.SCAN"));
        inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PASSWORD"));
        saveKeyLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE"));
        inputRandomButton.setText(PropertiesLocale.getProperty("UI.RANDOM"));

        saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PUBLIC_KEY"));
        saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PRIVATE_KEY"));
        saveKeyComboBox.addItem(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.SAVE")
                + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.BOTH"));

        inputPublicKeyText.setEditable(false);

        // Set Size
        inputPublicKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputPublicKeyText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputPublicKeyButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        saveKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        saveKeyComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        inputPassWordText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputPassWordLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputRandomButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

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
        inputPublicKeyLabel.setFont(UIConstant.FONT_NORMAL);
        inputPublicKeyText.setFont(UIConstant.FONT_NORMAL);
        inputPublicKeyButton.setFont(UIConstant.FONT_BUTTON);

        inputPassWordText.setFont(UIConstant.FONT_NORMAL);
        inputPassWordLabel.setFont(UIConstant.FONT_NORMAL);
        inputRandomButton.setFont(UIConstant.FONT_BUTTON);

        saveKeyLabel.setFont(UIConstant.FONT_NORMAL);
        saveKeyComboBox.setFont(UIConstant.FONT_NORMAL);

        keyTextArea.setFont(UIConstant.FONT_TEXT_AREA);

        // Add Components
        panelGridSetting.add(inputPublicKeyLabel);
        panelGridSetting.add(inputPublicKeyText);
        panelGridSetting.add(inputPublicKeyButton);

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

        tip = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.TARGET.TIP.OVERALL"));
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
        inputRandomButton.addActionListener(e ->
                inputPassWordText.setText(Character.getRandomString(KeyPairUtility.RANDOM_KEY_SIZE)));

        inputPublicKeyButton.addActionListener(e -> {
            publicKeyMap = KeyPairUtility.getKeyMap();

            if(publicKeyMap == null) {
                isPublicKeyLoaded = false;
                tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.LOAD.FAILURE"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.LOAD.FAILURE"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                if(publicKeyMap.containsKey(KeyPairUtility.DH_PUBLIC_KEY)) {
                    isPublicKeyLoaded = true;
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY.SUCCESS"));
                }else {
                    isPublicKeyLoaded = false;
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY.UNMATCHED"));
                }
            }
        });

        activateButton.addActionListener(e -> {
            if(inputPassWordText.getText().trim().isEmpty()) {
                tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PASSWORD.EMPTY"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PASSWORD.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else if(!isPublicKeyLoaded) {
                tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.TARGET.PUBLIC_KEY.EMPTY"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.TARGET.PUBLIC_KEY.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    generatedKeyMap = DiffieHellman.generateTargetKeyPair(inputPassWordText.getText().trim(),
                            KeyPairUtility.getDHPublicKey(publicKeyMap));
                } catch (Exception e1) {
                    logger.error(e1.toString());
                }

                if(generatedKeyMap != null) {
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.SUCCESS"));
                    byte[] publicKey = KeyPairUtility.getDHPublicKey(generatedKeyMap);
                    byte[] privateKey = KeyPairUtility.getDHPrivateKey(generatedKeyMap);
                    isKeyGenerated = true;
                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.GENERATE.SUCCESS"),
                            PropertiesLocale.getProperty("UI.MESSAGE.INFO"),JOptionPane.INFORMATION_MESSAGE);

                    keyTextArea.setText(PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PUBLIC_KEY") + ":\n"
                            + HexConver.byte2HexStr(publicKey, publicKey.length) + "\n\n"
                            + PropertiesLocale.getProperty("UI.EXCHANGE.SOURCE.PRIVATE_KEY") + ":\n"
                            + HexConver.byte2HexStr(privateKey, privateKey.length));

                    saveKeyComboBox.setEnabled(true);
                    saveButton.setEnabled(true);
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
            if(generatedKeyMap != null && !isKeyGenerated) {
                Map<String,Object> output = new HashMap<>();

                DHPublicKey dhPublicKey = (DHPublicKey) generatedKeyMap.get(KeyPairUtility.DH_PUBLIC_KEY);
                DHPrivateKey dhPrivateKey = (DHPrivateKey) generatedKeyMap.get(KeyPairUtility.DH_PRIVATE_KEY);

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
}

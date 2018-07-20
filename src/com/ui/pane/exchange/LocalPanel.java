package com.ui.pane.exchange;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.tool.coding.HexConver;
import com.tool.security.KeyPairUtility;
import com.tool.security.dh.DiffieHellman;
import com.ui.MainWindow;
import com.ui.component.ExecuteButton;
import com.ui.constant.UIConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("Duplicates")
public class LocalPanel extends JPanel {
    private Logger logger;

    private JLabel inputPublicKeyLabel = new JLabel();
    private JTextField inputPublicKeyText = new JTextField();
    private JButton inputPublicKeyButton = new JButton();

    private JLabel inputPrivateKeyLabel = new JLabel();
    private JTextField inputPrivateKeyText = new JTextField();
    private JButton inputPrivateKeyButton = new JButton();

    private JComboBox<String> algorithmComboBox = new JComboBox<>(SystemConstant.SYMMETRIC_ALGORITHM);
    private JLabel algorithmLabel = new JLabel();

    private JTextArea keyTextArea = new JTextArea(7,21);

    private ExecuteButton activateButton;
    private ExecuteButton saveButton;

    private JLabel tip;

    // Flags
    private Map<String,Object> publicKeyMap;
    private boolean isPublicKeyLoaded;

    private Map<String,Object> privateKeyMap;
    private boolean isPrivateKeyLoaded;

    private Map<String,Object> sessionKeyMap;
    private boolean isKeyGenerated;

    public LocalPanel(){
        initialize();
        addComponents();
        addListener();
    }

    private void initialize(){
        logger = LoggerFactory.getLogger(LocalPanel.class);

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

        inputPublicKeyLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY"));
        inputPublicKeyButton.setText(PropertiesLocale.getProperty("UI.SCAN"));
        algorithmLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.ALGORITHM"));
        inputPrivateKeyLabel.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PRIVATE_KEY"));
        inputPrivateKeyButton.setText(PropertiesLocale.getProperty("UI.SCAN"));

        inputPublicKeyText.setEditable(false);
        inputPrivateKeyText.setEditable(false);

        checkConfig();

        // Set Size
        inputPublicKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputPublicKeyText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputPublicKeyButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        algorithmLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        algorithmComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        inputPrivateKeyText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputPrivateKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputPrivateKeyButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);
        keyTextArea.setBackground(UIConstant.MAIN_BACK_COLOR);
        keyTextArea.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        keyTextArea.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(keyTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIConstant.LIST_BACK_COLOR),
                PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.SESSION_KEY"),
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_JUSTIFICATION,
                UIConstant.FONT_NORMAL));
        scrollPane.setBackground(UIConstant.MAIN_BACK_COLOR);
        scrollPane.setPreferredSize(UIConstant.TEXT_AREA_SIZE);
        scrollPane.setOpaque(true);

        // Set Font
        inputPublicKeyLabel.setFont(UIConstant.FONT_NORMAL);
        inputPublicKeyText.setFont(UIConstant.FONT_NORMAL);
        inputPublicKeyButton.setFont(UIConstant.FONT_BUTTON);

        algorithmComboBox.setFont(UIConstant.FONT_NORMAL);
        algorithmLabel.setFont(UIConstant.FONT_NORMAL);

        inputPrivateKeyText.setFont(UIConstant.FONT_NORMAL);
        inputPrivateKeyLabel.setFont(UIConstant.FONT_NORMAL);
        inputPrivateKeyButton.setFont(UIConstant.FONT_BUTTON);

        keyTextArea.setFont(UIConstant.FONT_TEXT_AREA);

        // Add Components
        panelGridSetting.add(inputPrivateKeyLabel);
        panelGridSetting.add(inputPrivateKeyText);
        panelGridSetting.add(inputPrivateKeyButton);

        panelGridSetting.add(inputPublicKeyLabel);
        panelGridSetting.add(inputPublicKeyText);
        panelGridSetting.add(inputPublicKeyButton);

        panelGridSetting.add(algorithmLabel);
        panelGridSetting.add(algorithmComboBox);

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

        tip = new JLabel(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.TIP.OVERALL"));
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

    public void checkConfig() {
        if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[0])) {
            algorithmComboBox.setSelectedIndex(0);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[1])) {
            algorithmComboBox.setSelectedIndex(1);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[2])) {
            algorithmComboBox.setSelectedIndex(2);
        }
    }

    private void addListener() {
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
                    inputPublicKeyText.setText(HexConver.byte2HexStr(KeyPairUtility.getDHPublicKey(publicKeyMap),
                            KeyPairUtility.getDHPublicKey(publicKeyMap).length));
                }else {
                    isPublicKeyLoaded = false;
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY.UNMATCHED"));
                }
            }
        });

        inputPrivateKeyButton.addActionListener(e -> {
            privateKeyMap = KeyPairUtility.getKeyMap();

            if(privateKeyMap == null) {
                isPrivateKeyLoaded = false;
                tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.LOAD.FAILURE"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.LOAD.FAILURE"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                if(privateKeyMap.containsKey(KeyPairUtility.DH_PRIVATE_KEY)) {
                    isPrivateKeyLoaded = true;
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PRIVATE_KEY.SUCCESS"));
                    inputPrivateKeyText.setText(HexConver.byte2HexStr(KeyPairUtility.getDHPrivateKey(privateKeyMap),
                            KeyPairUtility.getDHPrivateKey(privateKeyMap).length));
                }else {
                    isPrivateKeyLoaded = false;
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PRIVATE_KEY.UNMATCHED"));
                }
            }
        });

        // Choose Encryption Algorithm
        algorithmComboBox.addItemListener(e -> {
            if( e.getStateChange() == ItemEvent.SELECTED ){
                if(e.getSource() == algorithmComboBox){
                    if((algorithmComboBox.getSelectedIndex()==0)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[0]);
                    }
                    else if((algorithmComboBox.getSelectedIndex()==1)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[1]);
                    }
                    else if((algorithmComboBox.getSelectedIndex()==2)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[2]);
                    }
                }
            }
        });

        activateButton.addActionListener(e -> {
            if(!isPublicKeyLoaded) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PUBLIC_KEY.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else if(!isPrivateKeyLoaded) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.PRIVATE_KEY.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                byte[] public_key = KeyPairUtility.getDHPublicKey(publicKeyMap);
                byte[] private_key = KeyPairUtility.getDHPrivateKey(privateKeyMap);

                try {
                    sessionKeyMap = DiffieHellman.generateSessionKey(public_key,private_key,PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println(HexConver.byte2HexStr(public_key,public_key.length));
                System.out.println(HexConver.byte2HexStr(private_key,private_key.length));

                if(sessionKeyMap != null) {
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.SESSION_KEY.SUCCESS"));
                    byte[] core = KeyPairUtility.getSessionKey(sessionKeyMap,
                            PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).getEncoded();
                    keyTextArea.setText(HexConver.byte2HexStr(core, core.length));
                    isKeyGenerated = true;
                    saveButton.setEnabled(true);
                }else {
                    tip.setText(PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.SESSION_KEY.FAILURE"));
                    isKeyGenerated = false;
                    keyTextArea.setText("");
                    saveButton.setEnabled(false);
                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.EXCHANGE.LOCAL.SESSION_KEY.FAILURE"),
                            PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        saveButton.addActionListener(e -> {
            if(sessionKeyMap != null && isKeyGenerated) {
                KeyPairUtility.saveSecretKeyFile(sessionKeyMap);
            }
        });
    }
}

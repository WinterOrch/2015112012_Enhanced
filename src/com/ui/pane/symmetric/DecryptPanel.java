package com.ui.pane.symmetric;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.tool.coding.HexConver;
import com.tool.security.KeyPairUtility;
import com.tool.security.encrypt.SymEncrypt;
import com.ui.MainWindow;
import com.ui.component.ExecuteButton;
import com.ui.component.ProgressBar;
import com.ui.constant.UIConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("Duplicates")
public class DecryptPanel extends JPanel {
    private Logger logger;

    private JComboBox<String> secretKeyFormatComboBox = new JComboBox<>();
    private JLabel secretKeyLabel = new JLabel();

    private JLabel inputPassWordLabel = new JLabel();
    private JTextField inputPassWordText = new JTextField();
    private JButton inputPassWordButton = new JButton();

    private JComboBox<String> algorithmComboBox = new JComboBox<>(SystemConstant.SYMMETRIC_ALGORITHM);
    private JLabel algorithmLabel = new JLabel();

    private JComboBox<String> modelComboBox = new JComboBox<>(SystemConstant.SYMMETRIC_MODEL);
    private JLabel modelLabel = new JLabel();

    private JComboBox<String> paddingComboBox = new JComboBox<>(SystemConstant.SYMMETRIC_PADDING_METHOD);
    private JLabel paddingLabel = new JLabel();

    private JLabel inputFileLabel = new JLabel();
    private JTextField inputFileText = new JTextField();
    private JButton inputFileButton = new JButton();

    private JLabel tip;
    private ProgressBar progressBar;

    private ExecuteButton confirmButton;

    // Secret Key File
    private Map<String,Object> key;
    private boolean isKeyFileLoaded;

    // File For Encryption
    private File fileForEncrypt;
    private boolean isFileLoaded;

    private Key core;

    public DecryptPanel(){
        initialize();
        addComponents();
        addListener();
    }

    private void initialize(){
        logger = LoggerFactory.getLogger(DecryptPanel.class);

        this.setBackground(UIConstant.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());

        isKeyFileLoaded = false;
        isFileLoaded = false;
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
        secondPanelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 7));

        secretKeyLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK"));
        inputPassWordButton.setText(PropertiesLocale.getProperty("UI.SCAN"));
        algorithmLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.ALGORITHM"));
        modelLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.MODEL"));
        paddingLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PADDING"));
        inputFileLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.DECRYPT.FILE"));
        inputFileButton.setText(PropertiesLocale.getProperty("UI.SCAN"));

        secretKeyFormatComboBox.addItem(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.NEW"));
        secretKeyFormatComboBox.addItem(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT"));

        inputFileText.setEditable(false);

        progressBar = new ProgressBar(0,100);
        progressBar.setOrientation(JProgressBar.HORIZONTAL);

        checkConfig();

        // Set Size
        secretKeyLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        secretKeyFormatComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        inputPassWordLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputPassWordText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputPassWordButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        algorithmLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        algorithmComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        modelLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        modelComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        paddingLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        paddingComboBox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        inputFileText.setPreferredSize(UIConstant.REVERSED_TEXT_FIELD_SIZE_ITEM);
        inputFileLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        inputFileButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        progressBar.setPreferredSize(UIConstant.PROGRESS_BAR_SIZE);

        // Set Font
        secretKeyFormatComboBox.setFont(UIConstant.FONT_NORMAL);
        secretKeyLabel.setFont(UIConstant.FONT_NORMAL);
        inputPassWordButton.setFont(UIConstant.FONT_BUTTON);

        inputPassWordLabel.setFont(UIConstant.FONT_NORMAL);
        inputPassWordText.setFont(UIConstant.FONT_NORMAL);
        inputFileButton.setFont(UIConstant.FONT_BUTTON);


        algorithmComboBox.setFont(UIConstant.FONT_NORMAL);
        algorithmLabel.setFont(UIConstant.FONT_NORMAL);
        modelComboBox.setFont(UIConstant.FONT_NORMAL);
        modelLabel.setFont(UIConstant.FONT_NORMAL);
        paddingComboBox.setFont(UIConstant.FONT_NORMAL);

        paddingLabel.setFont(UIConstant.FONT_NORMAL);
        inputFileText.setFont(UIConstant.FONT_NORMAL);
        inputFileLabel.setFont(UIConstant.FONT_NORMAL);

        // Add Components
        panelGridSetting.add(secretKeyLabel);
        panelGridSetting.add(secretKeyFormatComboBox);

        panelGridSetting.add(inputPassWordLabel);
        panelGridSetting.add(inputPassWordText);
        panelGridSetting.add(inputPassWordButton);

        panelGridSetting.add(algorithmLabel);
        panelGridSetting.add(algorithmComboBox);

        panelGridSetting.add(modelLabel);
        panelGridSetting.add(modelComboBox);

        panelGridSetting.add(paddingLabel);
        panelGridSetting.add(paddingComboBox);

        secondPanelGridSetting.add(inputFileLabel);
        secondPanelGridSetting.add(inputFileText);
        secondPanelGridSetting.add(inputFileButton);

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

        tip = new JLabel(PropertiesLocale.getProperty("UI.SYMMETRIC.DECRYPT.TIP.OVERALL"));
        tip.setFont(UIConstant.FONT_NORMAL);
        westPanel.add(tip);

        JPanel eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));

        confirmButton = new ExecuteButton(PropertiesLocale.getProperty("UI.ACTIVATE"),2F);
        confirmButton.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);

        eastPanel.add(confirmButton);

        southPanel.add(westPanel);
        southPanel.add(eastPanel);
        return southPanel;
    }

    public void checkConfig() {
        if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.SK.SELECTION")).equals("New")) {
            secretKeyFormatComboBox.setSelectedIndex(0);
            inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PASSWORD"));
            inputPassWordText.setEditable(true);
            algorithmComboBox.setEnabled(true);
            inputPassWordButton.setEnabled(false);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.SK.SELECTION")).equals("Old")) {
            secretKeyFormatComboBox.setSelectedIndex(1);
            inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SKF"));
            inputPassWordText.setEditable(false);
            algorithmComboBox.setEnabled(false);
            inputPassWordButton.setEnabled(true);
        }

        if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[0])) {
            algorithmComboBox.setSelectedIndex(0);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[1])) {
            algorithmComboBox.setSelectedIndex(1);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM")).equals(SystemConstant.SYMMETRIC_ALGORITHM[2])) {
            algorithmComboBox.setSelectedIndex(2);
        }

        if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.MODEL")).equals(SystemConstant.SYMMETRIC_MODEL[0])) {
            modelComboBox.setSelectedIndex(0);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.MODEL")).equals(SystemConstant.SYMMETRIC_MODEL[1])) {
            modelComboBox.setSelectedIndex(1);
        }

        if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.PADDING")).equals(SystemConstant.SYMMETRIC_PADDING_METHOD[0])) {
            paddingComboBox.setSelectedIndex(0);
        }else if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.PADDING")).equals(SystemConstant.SYMMETRIC_PADDING_METHOD[1])) {
            paddingComboBox.setSelectedIndex(1);
        }
    }

    private void addListener() {
        inputPassWordButton.addActionListener(e -> {
            key = KeyPairUtility.getKeyMap();

            if(key == null) {
                isKeyFileLoaded = false;
                tip.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT.FAILURE"));
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT.FAILURE.WRONG"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                if(key.containsKey(SystemConstant.SYMMETRIC_ALGORITHM[0] + KeyPairUtility.SESSION_KEY)) {
                    algorithmComboBox.setSelectedIndex(0);
                    PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[0]);
                    isKeyFileLoaded = true;
                }else if(key.containsKey(SystemConstant.SYMMETRIC_ALGORITHM[1] + KeyPairUtility.SESSION_KEY)) {
                    algorithmComboBox.setSelectedIndex(1);
                    PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[1]);
                    isKeyFileLoaded = true;
                }else if(key.containsKey(SystemConstant.SYMMETRIC_ALGORITHM[2] + KeyPairUtility.SESSION_KEY)) {
                    algorithmComboBox.setSelectedIndex(2);
                    PropertiesLocale.changeAlgorithm("SYMMETRIC.ALGORITHM",SystemConstant.SYMMETRIC_ALGORITHM[2]);
                    isKeyFileLoaded = true;
                }else {
                    isKeyFileLoaded = false;
                    tip.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT.FAILURE"));
                    JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT.FAILURE.UNMATCHED"),
                            PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        inputFileButton.addActionListener(e -> {
            JFileChooser skFile = new JFileChooser();
            skFile.setDialogTitle(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT"));
            skFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int returnWatermarkVal = skFile.showOpenDialog(null);
            if (JFileChooser.APPROVE_OPTION == returnWatermarkVal) {
                File watermarkSpecificFile = skFile.getSelectedFile();
                inputFileText.setText(watermarkSpecificFile.getAbsolutePath());
                fileForEncrypt = watermarkSpecificFile;
                isFileLoaded = true;
                tip.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.FILE.SUCCESS"));
            }
        });

        // Choose Secret Key Format
        secretKeyFormatComboBox.addItemListener(e -> {
            if( e.getStateChange() == ItemEvent.SELECTED ){
                if(e.getSource() == secretKeyFormatComboBox){
                    if((secretKeyFormatComboBox.getSelectedIndex()==0)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.SK.SELECTION","New");
                        inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.PASSWORD"));
                        algorithmComboBox.setEnabled(true);
                        inputPassWordButton.setEnabled(false);
                        inputPassWordText.setEditable(true);
                    }
                    else if((secretKeyFormatComboBox.getSelectedIndex()==1)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.SK.SELECTION","Old");
                        inputPassWordLabel.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SKF"));
                        algorithmComboBox.setEnabled(false);
                        inputPassWordButton.setEnabled(true);
                        inputPassWordText.setEditable(false);
                    }
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

        // Choose Encryption Padding Method
        paddingComboBox.addItemListener(e -> {
            if( e.getStateChange() == ItemEvent.SELECTED ){
                if(e.getSource() == paddingComboBox){
                    if((paddingComboBox.getSelectedIndex()==0)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.PADDING",SystemConstant.SYMMETRIC_PADDING_METHOD[0]);
                    }
                    else if((paddingComboBox.getSelectedIndex()==1)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.PADDING",SystemConstant.SYMMETRIC_PADDING_METHOD[1]);
                    }
                }
            }
        });

        // Choose Encryption Model
        modelComboBox.addItemListener(e -> {
            if( e.getStateChange() == ItemEvent.SELECTED ){
                if(e.getSource() == modelComboBox){
                    if((modelComboBox.getSelectedIndex()==0)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.MODEL",SystemConstant.SYMMETRIC_MODEL[0]);
                    }
                    else if((modelComboBox.getSelectedIndex()==1)){
                        PropertiesLocale.changeAlgorithm("SYMMETRIC.MODEL",SystemConstant.SYMMETRIC_MODEL[1]);
                    }
                }
            }
        });

        // Activate Button
        confirmButton.addActionListener(e -> {
            if(!isFileLoaded) {
                JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.FILE.EMPTY"),
                        PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
            }else {
                if(Objects.requireNonNull(PropertiesLocale.getConfig("SYMMETRIC.SK.SELECTION")).equals("New")) {
                    if(inputPassWordText.getText().trim().isEmpty()) {
                        core = null;
                        JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.NEW.EMPTY"),
                                PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                    }else {
                        core = KeyPairUtility.generateSecretKey(inputPassWordText.getText().trim(),
                                PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"));
                    }
                }else {
                    if(!isKeyFileLoaded) {
                        core = null;
                        JOptionPane.showMessageDialog(MainWindow.frame,PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT.EMPTY"),
                                PropertiesLocale.getProperty("UI.MESSAGE.WARNING"),JOptionPane.WARNING_MESSAGE);
                    }else {
                        core = KeyPairUtility.getSessionKey(key, PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"));
                    }
                }
                if(null == core) {
                    logger.error("Session Key Not Generated Correctly");
                }else {

                    new Thread(()->{
                        progressBar.setValue(30);

                        File outputFile;
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter;
                        filter = new FileNameExtensionFilter(
                                "." + SystemConstant.SYMMETRIC_EXTENSION, SystemConstant.SYMMETRIC_EXTENSION);
                        chooser.setFileFilter(filter);

                        int option = chooser.showSaveDialog(null);
                        if(option==JFileChooser.APPROVE_OPTION){
                            outputFile = chooser.getSelectedFile();
                            String fileName = chooser.getName(outputFile);

                            if(!fileName.contains( "." + SystemConstant.SYMMETRIC_EXTENSION )){
                                outputFile = new File(chooser.getCurrentDirectory(),
                                        fileName+"."+SystemConstant.SYMMETRIC_EXTENSION);
                            }

                            progressBar.setValue(60);

                            try {
                                SymEncrypt.crypt(Cipher.DECRYPT_MODE,fileForEncrypt,outputFile,core,
                                        PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"),
                                        PropertiesLocale.getConfig("SYMMETRIC.MODEL"),
                                        PropertiesLocale.getConfig("SYMMETRIC.PADDING"));
                            } catch (IOException e1) {
                                logger.error("IOException:{}/{}/{}",
                                        PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"),
                                        PropertiesLocale.getConfig("SYMMETRIC.MODEL"),
                                        PropertiesLocale.getConfig("SYMMETRIC.PADDING"));
                            } catch (NoSuchPaddingException e1) {
                                logger.error("NoSuchPaddingException:{}/{}/{}",
                                        PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"),
                                        PropertiesLocale.getConfig("SYMMETRIC.MODEL"),
                                        PropertiesLocale.getConfig("SYMMETRIC.PADDING"));
                            } catch (NoSuchAlgorithmException e1) {
                                logger.error("NoSuchAlgorithmException:{}/{}/{}",
                                        PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"),
                                        PropertiesLocale.getConfig("SYMMETRIC.MODEL"),
                                        PropertiesLocale.getConfig("SYMMETRIC.PADDING"));
                            } catch (InvalidKeyException e1) {
                                logger.error("InvalidKeyException:{}/{}/{}, Key: {}",
                                        PropertiesLocale.getConfig("SYMMETRIC.ALGORITHM"),
                                        PropertiesLocale.getConfig("SYMMETRIC.MODEL"),
                                        PropertiesLocale.getConfig("SYMMETRIC.PADDING"),
                                        HexConver.byte2HexStr(core.getEncoded(),core.getEncoded().length));
                            } catch (NoSuchProviderException e1) {
                                logger.error("NoSuchProviderException: BC");
                            }

                            tip.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.OUTPUT.SUCCESS"));
                            progressBar.setValue(100);
                        }else {
                            tip.setText(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.OUTPUT.CANCEL"));
                            progressBar.setValue(0);
                        }
                    }).start();

                }
            }
        });
    }
}

package com.ui.pane.setting;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;
import com.ui.MainWindow;
import com.ui.constant.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class OptionPanel extends JPanel {
    private JComboBox<String> languageCombox = new JComboBox<>(SystemConstant.LANGUAGE_LIST);

    public OptionPanel(){
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
    }

    private JPanel getCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(UIConstant.MAIN_BACK_COLOR);
        centerPanel.setLayout(new GridLayout(2, 1));

        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(UIConstant.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, UIConstant.MAIN_H_GAP, 0));

        JLabel languageLabel = new JLabel(PropertiesLocale.getProperty("UI.SETTING.OPTION.LANGUAGE"));
        languageCombox.setEditable(false);
        if(PropertiesLocale.locale==1){
            languageCombox.setSelectedIndex(0);
        }
        else if(PropertiesLocale.locale==2){
            languageCombox.setSelectedIndex(1);
        }

        languageLabel.setPreferredSize(UIConstant.LABLE_SIZE_ITEM);
        languageCombox.setPreferredSize(UIConstant.TEXT_FIELD_SIZE_ITEM);

        languageLabel.setFont(UIConstant.FONT_NORMAL);
        languageCombox.setFont(UIConstant.FONT_NORMAL);

        panelGridSetting.add(languageLabel);
        panelGridSetting.add(languageCombox);

        centerPanel.add(panelGridSetting);
        return centerPanel;
    }

    private void addListener(){
        languageCombox.addItemListener(e -> {
            if( e.getStateChange() == ItemEvent.SELECTED ){
                if(e.getSource() == languageCombox){
                    if((languageCombox.getSelectedIndex()==0)&&(PropertiesLocale.locale!=1)){
                        PropertiesLocale.changeLanguage("CN");
                        PropertiesLocale.initialize();
                    }
                    else if((languageCombox.getSelectedIndex()==1)&&(PropertiesLocale.locale!=2)){
                        PropertiesLocale.changeLanguage("EN");
                        PropertiesLocale.initialize();
                    }
                    MainWindow.freshLocale();
                }
            }
        });
    }

}

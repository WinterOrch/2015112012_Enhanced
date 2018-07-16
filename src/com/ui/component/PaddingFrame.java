package com.ui.component;

import com.ui.constant.UIConstant;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;

public class PaddingFrame {

    private static boolean isAdjusting(JComboBox cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

    public static void setupAutoComplete(final JTextField txtInput, final ArrayList<String> series) {
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        final JComboBox<String> cbInput = new JComboBox<String>(model) {
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        cbInput.setFont(UIConstant.FONT_NORMAL);

        // Mark Component to Avoid Error
        cbInput.putClientProperty("is_adjusting", false);

        // Add Items
        for (String item : series) {
            model.addElement(item);
        }
        cbInput.setSelectedItem(null);
        cbInput.addActionListener(e -> {
            if (!isAdjusting(cbInput)) {
                if (cbInput.getSelectedItem() != null) {
                    txtInput.setText(cbInput.getSelectedItem().toString());
                }
            }
        });

        // Add Key Functions
        txtInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                cbInput.putClientProperty("is_adjusting", true);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (cbInput.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP ||
                        e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        txtInput.setText(Objects.requireNonNull(cbInput.getSelectedItem()).toString());
                        cbInput.setPopupVisible(false);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                }

                // End of Modifying, Remove Mark
                cbInput.putClientProperty("is_adjusting", false);
            }
        });

        txtInput.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateSeries();
            }
            public void removeUpdate(DocumentEvent e) {
                updateSeries();
            }
            public void changedUpdate(DocumentEvent e) {
                updateSeries();
            }
            private void updateSeries() {
                cbInput.putClientProperty("is_adjusting", true);
                model.removeAllElements();
                String input = txtInput.getText();
                if (!input.isEmpty()) {
                    for (String item : series) {
                        if (item.toLowerCase().startsWith(input.toLowerCase())) {
                            model.addElement(item);
                        }
                    }
                }
                cbInput.setPopupVisible(model.getSize() > 0);
                cbInput.putClientProperty("is_adjusting", false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.setTitle("Auto Completion Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 500, 400);

        ArrayList<String> series = new ArrayList<>();
        series.add("WZ111");
        series.add("WZ132");
		/*Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			String item = locales[i].getDisplayName();
			items.add(item);
		}*/
        JTextField txtInput = new JTextField();
        setupAutoComplete(txtInput, series);
        txtInput.setColumns(30);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(txtInput, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}

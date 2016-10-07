/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.protege.editor.core.ui.util.JOptionPaneEx;

/**
 *
 * @author ZONGO
 */
public class DialogFramework {

            public static int showDialog(JComponent parent, String title, JComponent component) {
                        return JOptionPaneEx.showConfirmDialog(parent,
                                    title,
                                    component,
                                    JOptionPane.PLAIN_MESSAGE,
                                    JOptionPane.OK_CANCEL_OPTION,
                                    null);
            }

            public static String showLanguageSelectorDialog(JComponent parent) {
                        JComboBox langComboBox = new JComboBox(new DefaultComboBoxModel(new String[]{"en", "fr"}));
                        JLabel label = new JLabel("Lang : ");
                        JPanel panel = new JPanel(new BorderLayout(10, 5));
                        panel.add(label, BorderLayout.WEST);
                        panel.add(langComboBox, BorderLayout.CENTER);
                        panel.setPreferredSize(new Dimension(400, 0));
                        int val = showDialog(parent, "Lang", panel);
                        if (val == JOptionPane.OK_OPTION && langComboBox.getSelectedItem() != null) {
                                    return langComboBox.getSelectedItem().toString();
                        } else {
                                    return null;
                        }
            }
}

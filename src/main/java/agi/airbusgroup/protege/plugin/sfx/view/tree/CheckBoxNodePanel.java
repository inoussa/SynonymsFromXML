/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.ImageIcon;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ZONGO
 */
public class CheckBoxNodePanel extends JPanel {

            public final JLabel label = new JLabel();
            public final JCheckBox check = new JCheckBox();
            private Boolean isRecommanded = null;

            public static final ImageIcon VALIDE_ICON = new ImageIcon("plugins/sfx-data/valide.png");
            public static final ImageIcon INVALIDE_ICON = new ImageIcon("plugins/sfx-data/invalide.png");

            public CheckBoxNodePanel() {
                        this.check.setMargin(new Insets(0, 0, 0, 5));
                        if (isRecommanded == null) {
                                    this.label.setIcon(null);
                        } else {
                                    if (isRecommanded.equals(true)) {
                                                this.label.setIcon(CheckBoxNodePanel.VALIDE_ICON);
                                    }
                                    if (isRecommanded.equals(false)) {
                                                this.label.setIcon(CheckBoxNodePanel.INVALIDE_ICON);
                                    }
                        }

                        check.setBackground(Color.WHITE);
                        setLayout(new BorderLayout());
                        add(check, BorderLayout.WEST);
                        add(label, BorderLayout.CENTER);
            }

            /**
             * @return the isRecommanded
             */
            public Boolean getIsRecommanded() {
                        return isRecommanded;
            }

            /**
             * @param isRecommanded the isRecommanded to set
             */
            public void setIsRecommanded(Boolean isRecommanded) {
                        this.isRecommanded = isRecommanded;
            }

}

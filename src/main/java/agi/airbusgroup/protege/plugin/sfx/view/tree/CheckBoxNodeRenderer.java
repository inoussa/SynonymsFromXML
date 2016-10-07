/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author ZONGO
 */
public class CheckBoxNodeRenderer implements TreeCellRenderer {

            private final CheckBoxNodePanel panel = new CheckBoxNodePanel();

            private final DefaultTreeCellRenderer defaultRenderer
                        = new DefaultTreeCellRenderer();

            private final Color selectionForeground, selectionBackground;
            private final Color textForeground, textBackground;

            protected CheckBoxNodePanel getPanel() {
                        return panel;
            }

            public CheckBoxNodeRenderer() {
                        final Font fontValue = UIManager.getFont("Tree.font");
                        if (fontValue != null) {
                                    panel.label.setFont(fontValue);
                        }

                        final Boolean focusPainted
                                    = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
                        panel.check.setFocusPainted(focusPainted != null && focusPainted);

                        selectionForeground = UIManager.getColor("Tree.selectionForeground");
                        selectionBackground = UIManager.getColor("Tree.selectionBackground");
                        textForeground = UIManager.getColor("Tree.textForeground");
                        textBackground = UIManager.getColor("Tree.textBackground");
            }

            // -- TreeCellRenderer methods --
            @Override
            public Component getTreeCellRendererComponent(final JTree tree,
                        final Object value, final boolean selected, final boolean expanded,
                        final boolean leaf, final int row, final boolean hasFocus) {
                        CheckBoxNodeData data = null;
                        if (value instanceof DefaultMutableTreeNode) {
                                    final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                                    final Object userObject = node.getUserObject();
                                    if (userObject instanceof CheckBoxNodeData) {
                                                data = (CheckBoxNodeData) userObject;
                                    }
                        }

                        final String stringValue
                                    = tree.convertValueToText(value, selected, expanded, leaf, row, false);
                        panel.label.setText(stringValue);
                        panel.check.setSelected(true);

                        panel.setEnabled(tree.isEnabled());

                        if (selected) {
                                    panel.setForeground(selectionForeground);
                                    panel.setBackground(selectionBackground);
                                    panel.label.setForeground(selectionForeground);
                                    panel.label.setBackground(selectionBackground);
                        } else {
                                    panel.setForeground(textForeground);
                                    panel.setBackground(textBackground);
                                    panel.label.setForeground(textForeground);
                                    panel.label.setBackground(textBackground);
                        }

                        if (data == null) {
                                    // not a check box node; return default cell renderer
                                    return defaultRenderer.getTreeCellRendererComponent(tree, value,
                                                selected, expanded, leaf, row, hasFocus);
                        } else {
                                    Boolean status = data.getIsRecommanded();
                                    if (status == null) {
                                                panel.label.setIcon(null);
                                                if (!leaf) {
                                                            if (expanded) {
                                                                        panel.label.setIcon(UIManager.getIcon("Tree.openIcon"));
                                                            } else {
                                                                        panel.label.setIcon(UIManager.getIcon("Tree.closedIcon"));
                                                            }
                                                }
                                    } else {
                                                if (status.equals(true)) {
                                                            panel.label.setIcon(CheckBoxNodePanel.VALIDE_ICON);
                                                }
                                                if (status.equals(false)) {
                                                            panel.label.setIcon(CheckBoxNodePanel.INVALIDE_ICON);
                                                }
                                    }

                                    panel.setIsRecommanded(status);
                        }

                        panel.label.setText(data.getText());
                        panel.check.setSelected(data.isChecked());
                        return panel;
            }

}

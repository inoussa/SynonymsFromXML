/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.tree;

/**
 *
 * @author ZONGO
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author ZONGO
 */
public class CheckBoxNodeEditor extends AbstractCellEditor implements
            TreeCellEditor {

            private final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
            static Object lastPathComponent = null;
            static JCheckBox lastCheckBox = null;

            private final JTree theTree;

            public CheckBoxNodeEditor(final JTree tree) {
                        theTree = tree;
            }

            @Override
            public Object getCellEditorValue() {
                        final CheckBoxNodePanel panel = renderer.getPanel();
                        final CheckBoxNodeData checkBoxNode
                                    = new CheckBoxNodeData(panel.label.getText(), panel.check.isSelected(), panel.getIsRecommanded());
                        return checkBoxNode;
            }

            @Override
            public boolean isCellEditable(final EventObject event) {
                        if (!(event instanceof MouseEvent)) {
                                    return false;
                        }
                        final MouseEvent mouseEvent = (MouseEvent) event;

                        final TreePath path
                                    = theTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                        if (path == null) {
                                    return false;
                        }

                        final Object node = path.getLastPathComponent();
                        if (!(node instanceof DefaultMutableTreeNode)) {
                                    return false;
                        }
                        final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;

                        final Object userObject = treeNode.getUserObject();
                        return userObject instanceof CheckBoxNodeData;
            }

            @Override
            public Component getTreeCellEditorComponent(final JTree tree,
                        final Object value, final boolean selected, final boolean expanded,
                        final boolean leaf, final int row) {

                        final Component editor
                                    = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
                                                row, true);

                        // editor always selected / focused
                        final ActionListener actionListener = new ActionListener() {

                                    private void setChildNodeStatus(DefaultMutableTreeNode treeNode, boolean status) {
                                                int numChild = treeNode.getChildCount();
                                                for (int i = 0; i < numChild; i++) {
                                                            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) treeNode.getChildAt(i);
                                                            CheckBoxNodeData data = (CheckBoxNodeData) childNode.getUserObject();
                                                            data.setChecked(status);
                                                            setChildNodeStatus(childNode, status);
                                                }

                                                /*else {

                                                 if (status == false) {
                                                 DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode.getParent();
                                                 CheckBoxNodeData data = (CheckBoxNodeData) parent.getUserObject();
                                                 data.setChecked(false);
                                                 }
                                                 }*/
                                    }

                                    private void setParentNodeStatus(DefaultMutableTreeNode treeNode, boolean status) {
                                                if (status == false) {
                                                            TreeNode nodes[] = treeNode.getPath();
                                                            for (int i = 0; i < nodes.length; i++) {
                                                                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) nodes[i];

                                                                        Object userObject = parent.getUserObject();
                                                                        if (userObject instanceof CheckBoxNodeData) {
                                                                                    CheckBoxNodeData data = (CheckBoxNodeData) userObject;
                                                                                    data.setChecked(false);
                                                                        }
                                                            }
                                                } /*else {
                                                 DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode.getParent();
                                                 if (parent != null) {
                                                 int numChild = parent.getChildCount();
                                                 boolean allChecked = true;
                                                 for (int i = 0; i < numChild; i++) {
                                                 DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) parent.getChildAt(i);
                                                 Object userObject = childNode.getUserObject();
                                                 if (userObject instanceof CheckBoxNodeData) {
                                                 CheckBoxNodeData data = (CheckBoxNodeData) userObject;
                                                 if (!data.isChecked()) {
                                                 allChecked = false;
                                                 }
                                                 }
                                                 }

                                                 if (allChecked) {
                                                 /*Object userObject = parent.getUserObject();
                                                 if (userObject instanceof CheckBoxNodeData) {
                                                 CheckBoxNodeData data = (CheckBoxNodeData) userObject;
                                                 data.setChecked(true);
                                                 }*/
                                                /*TreeNode nodes[] = treeNode.getPath();
                                                 for (int i = 0; i < nodes.length; i++) {
                                                 DefaultMutableTreeNode parent1 = (DefaultMutableTreeNode) nodes[i];

                                                 Object userObject = parent1.getUserObject();
                                                 if (userObject instanceof CheckBoxNodeData) {
                                                 CheckBoxNodeData data = (CheckBoxNodeData) userObject;
                                                 data.setChecked(true);
                                                 }
                                                 }*//*
                                                 }
                                                 }
                                                 }*/


                                    }

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                                if (stopCellEditing()) {
                                                            fireEditingStopped();
                                                }
                                                JCheckBox checkBox = (JCheckBox) e.getSource();
                                                boolean status = checkBox.isSelected();
                                                Object node = tree.getSelectionPath().getLastPathComponent();
                                                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;

                                                setChildNodeStatus(treeNode, status);
                                                setParentNodeStatus(treeNode, status);

                                                tree.repaint();
                                    }

                        };

                        if (editor instanceof CheckBoxNodePanel) {
                                    final CheckBoxNodePanel panel = (CheckBoxNodePanel) editor;

                                    //Avoid to add many listener to the checkbox
                                    if (panel.check.getActionListeners().length < 1) {
                                                panel.check.addActionListener(actionListener);
                                    }
                        }

                        return editor;
            }
}

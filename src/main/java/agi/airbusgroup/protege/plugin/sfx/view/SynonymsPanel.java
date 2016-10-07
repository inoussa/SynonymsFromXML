 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import agi.airbusgroup.protege.plugin.sfx.controller.SynonymController;
import agi.airbusgroup.protege.plugin.sfx.model.Synonym;
import agi.airbusgroup.protege.plugin.sfx.model.LangSynonyms;
import agi.airbusgroup.protege.plugin.sfx.model.exception.ClassMappingException;
import agi.airbusgroup.protege.plugin.sfx.model.SynonymsEntry;
import agi.airbusgroup.protege.plugin.sfx.view.tree.CheckBoxNodeData;
import agi.airbusgroup.protege.plugin.sfx.view.tree.CheckBoxNodeEditor;
import agi.airbusgroup.protege.plugin.sfx.view.tree.CheckBoxNodeRenderer;

/**
 *
 * @author ZONGO
 */
public class SynonymsPanel extends JPanel {

            private JTextField textField;
            private JSlider slider;
            private JLabel sliderValueLabel;
            private JButton searchButton;
            protected OWLEditorKit editorKit;
            JPanel topPanel;

            /**
             * Table that display list of synonyms
             */
            private JTree tree;
            DefaultMutableTreeNode rootNode;
            final Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

            /**
             * Create the pannel that wil contain synonyms
             *
             * @param oWLEditorKit
             */
            public SynonymsPanel(OWLEditorKit oWLEditorKit) {
                        super();
                        this.editorKit = oWLEditorKit;
                        setPreferredSize(new Dimension(500, 500));
                        setLayout(new BorderLayout(0, 10));
                        JLabel label = new JLabel("Terminology : ");
                        textField = new JTextField();
                        textField.setEditable(false);
                        textField.setFocusable(false);
                        topPanel = new JPanel();
                        BoxLayout boxLayout = new BoxLayout(topPanel, BoxLayout.Y_AXIS);
                        topPanel.setLayout(boxLayout);

                        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        JPanel panel1 = new JPanel(new BorderLayout());
                        panel1.add(label, BorderLayout.WEST);
                        panel1.add(textField);
                        topPanel.add(panel1);
                        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        JPanel sliderPanelContainer = new JPanel(new BorderLayout());

                        JPanel sliderPanel = new JPanel(new BorderLayout());
                        JLabel sliderLabel = new JLabel("Similarity rate : ");
                        sliderValueLabel = new JLabel();
                        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
                        slider.addChangeListener(new ChangeListener() {

                                    @Override
                                    public void stateChanged(ChangeEvent e) {
                                                sliderValueLabel.setText("  " + ((JSlider) e.getSource()).getValue() + "% ");
                                    }
                        });
                        sliderPanel.add(sliderLabel, BorderLayout.WEST);
                        sliderPanel.add(slider);
                        sliderPanel.add(sliderValueLabel, BorderLayout.EAST);
                        sliderValueLabel.setText(slider.getValue() + "% ");

                        searchButton = new JButton("Search");
                        searchButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                                textField.setText("");

                                                rootNode.removeAllChildren();
                                                ((DefaultTreeModel) tree.getModel()).reload();
                                                ArrayList<SynonymsEntry> synonymsEntries = search(editorKit);
                                                addSynonymToTree(synonymsEntries);

                                    }
                        });

                        sliderPanelContainer.add(sliderPanel);
                        sliderPanelContainer.add(searchButton, BorderLayout.EAST);

                        topPanel.add(sliderPanelContainer);
                        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        rootNode = new DefaultMutableTreeNode("Synonyms");
                        tree = new JTree(rootNode);
                        tree.setShowsRootHandles(true);

                        tree.setCellEditor(new CheckBoxNodeEditor(tree));
                        tree.setCellRenderer(new CheckBoxNodeRenderer());

                        tree.setEditable(true);
                        JPanel panel = new JPanel(new BorderLayout());
                        panel.add(new JScrollPane(tree));
                        topPanel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, BorderFactory.createTitledBorder("Search param")));
                        panel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, BorderFactory.createTitledBorder("Synonyms found")));
                        add(topPanel, BorderLayout.NORTH);
                        add(panel, BorderLayout.CENTER);

            }

            public void addSynonymToTree(ArrayList<SynonymsEntry> synonymsEntries) {
                        for (SynonymsEntry synonymsEntry : synonymsEntries) {
                                    CheckBoxNodeData synononymEntryNodeData = new CheckBoxNodeData(synonymsEntry.getKey(), true, null);
                                    DefaultMutableTreeNode synonymEntryNode = new DefaultMutableTreeNode(synononymEntryNodeData);
                                    rootNode.add(synonymEntryNode);
                                    for (LangSynonyms langSynonyms : synonymsEntry.getLangSynonyms()) {
                                                CheckBoxNodeData langSynonymsNodeData = new CheckBoxNodeData(langSynonyms.getLang(), true, null);
                                                DefaultMutableTreeNode langSynonymsNode = new DefaultMutableTreeNode(langSynonymsNodeData);
                                                synonymEntryNode.add(langSynonymsNode);
                                                for (Synonym synonym : langSynonyms.getSynonyms()) {
                                                            CheckBoxNodeData synonymNodeData = new CheckBoxNodeData(synonym.getValue(), true, synonym.getRecommended());
                                                            langSynonymsNode.add(new DefaultMutableTreeNode(synonymNodeData));
                                                }
                                    }
                        }

                        tree.repaint();

            }

            public void setTextField(ArrayList<String> terminologies) {
                        textField.setText("");
                        for (String terminology : terminologies) {
                                    textField.setText(textField.getText() + terminology+"    ");
                        }
            }

            /**
             * Show the synonym selection panel in a dialog
             *
             */
            public void showPanel() {
                        OWLNamedIndividual selectedIndividual = editorKit.getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual();
                        if (selectedIndividual == null) {
                                    return;
                        }
                        ArrayList<SynonymsEntry> synonymsEntries = search(editorKit);
                        addSynonymToTree(synonymsEntries);

                        int val = DialogFramework.showDialog(editorKit.getWorkspace(), selectedIndividual.getIRI().getFragment(), this);
                        if (val == JOptionPane.OK_OPTION) {
                                    addSynonymToOntology(editorKit);
                        }

            }

            public ArrayList<SynonymsEntry> search(OWLEditorKit editorKit) {
                        float minSimilarity = (float) slider.getValue() / 100;
                        OWLNamedIndividual selectedIndividual = editorKit.getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual();
                        ArrayList<SynonymsEntry> synonymsEntries = null;
                        try {
                                    SynonymController synonymController = new SynonymController(editorKit);
                                    synonymsEntries = synonymController.getSynonyms(selectedIndividual, minSimilarity);
                                    setTextField(synonymController.getTerminologies());
                        } catch (ClassMappingException e) {
                                    JPanel panel = new JPanel();
                                    panel.add(new JLabel(e.getMessage()));
                                    DialogFramework.showDialog(editorKit.getWorkspace(), "Message", panel);
                        }
                        return synonymsEntries;
            }

            /**
             * Check for synonyms and add them to the current ontology
             *
             * @param editorKit
             * @param lang
             */
            private void addSynonymToOntology(OWLEditorKit editorKit) {
                        OWLNamedIndividual selectedIndividual = editorKit.getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual();
                        OWLOntology ontology = editorKit.getModelManager().getActiveOntology();
                        OWLDataFactory factory = editorKit.getModelManager().getOWLDataFactory();

                        IRI iri = ontology.getOntologyID().getOntologyIRI().get();
                        OWLDataProperty dataProperty = factory.getOWLDataProperty(IRI.create(iri + "#Label"));

                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
                        Enumeration en = root.preorderEnumeration();

                        int nbSelectedSynonym = 0;
                        while (en.hasMoreElements()) {
                                    Object nextElement = en.nextElement();
                                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) nextElement;
                                    String lang = "";

                                    if (node.isLeaf() && !node.isRoot()) {
                                                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                                                CheckBoxNodeData parentData = (CheckBoxNodeData) parentNode.getUserObject();
                                                lang = parentData.getText();
                                                CheckBoxNodeData data = (CheckBoxNodeData) node.getUserObject();
                                                if (data.isChecked()) {
                                                            String labelDataPropertyValue = data.getText().toUpperCase();
                                                            OWLDataPropertyAssertionAxiom axiom = factory.getOWLDataPropertyAssertionAxiom(dataProperty, selectedIndividual, factory.getOWLLiteral(labelDataPropertyValue, lang));

                                                            AddAxiom addAxiom = new AddAxiom(ontology, axiom);
                                                            editorKit.getModelManager().applyChange(addAxiom);
                                                            nbSelectedSynonym++;
                                                }

                                    }

                        }
                        if (nbSelectedSynonym > 0) {
                                    Logger.getLogger(SynonymsPanel.class).info(nbSelectedSynonym + " synonyms added to the ontology");
                        }
            }
}

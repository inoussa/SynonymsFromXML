/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.protegeview;

import agi.airbusgroup.protege.plugin.sfx.view.SynonymsPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import org.protege.editor.core.ui.list.MListButton;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 *
 * @author ZONGO
 */
public class OWLDataPropertyAssertionAxiomFrameSection extends org.protege.editor.owl.ui.frame.individual.OWLDataPropertyAssertionAxiomFrameSection {

            private OWLEditorKit editorKit;

            public OWLDataPropertyAssertionAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<? extends OWLIndividual> frame) {
                        super(editorKit, frame);
                        this.editorKit = editorKit;
            }

            @Override
            public List<MListButton> getAdditionalButtons() {
                        MListAddButton mListAddButton;
                        ActionListener actionListener = new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                                OWLNamedIndividual selectedIndividual = editorKit.getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual();
                                                if (selectedIndividual != null) {
                                                            //OWLDataPropertyRelationshipEditor editor = OWLDataPropertyRelationshipEditor.getInstance(OWLDataPropertyAssertionAxiomFrameSection.this.editorKit);
                                                            //editor.show();
                                                            //SynonymsPanel synonymsPanel = SynonymsPanel.getInstance();
                                                            SynonymsPanel synonymsPanel = new SynonymsPanel(editorKit);
                                                            synonymsPanel.showPanel();
                                                }
                                    }
                        };

                        mListAddButton = new MListAddButton("Add data properties from XML file", Color.GREEN.darker(), actionListener);
                        List<MListButton> mListButtons = new ArrayList<MListButton>();
                        mListButtons.add(mListAddButton);
                        return mListButtons;
            }
}

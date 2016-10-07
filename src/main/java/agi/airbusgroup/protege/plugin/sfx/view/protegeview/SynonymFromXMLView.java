/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.protegeview;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.protege.editor.owl.ui.framelist.OWLFrameListRenderer;
import org.protege.editor.owl.ui.view.individual.AbstractOWLIndividualViewComponent;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 *
 * @author ZONGO
 */
public class SynonymFromXMLView extends AbstractOWLIndividualViewComponent {

            private static final long serialVersionUID = -1228370750437540626L;
            private OWLFrameList<OWLIndividual> list;

            @Override
            public void initialiseIndividualsView() throws Exception {
                        list = new OWLFrameList<OWLIndividual>(getOWLEditorKit(),
                                    new OWLIndividualPropertyAssertionsFrame(getOWLEditorKit()));
                        setLayout(new BorderLayout());
                        add(new JScrollPane(list));
                        OWLFrameListRenderer renderer = new OWLFrameListRenderer(getOWLEditorKit());
                        renderer.setHighlightKeywords(false);
                        list.setCellRenderer(renderer);
            }

            @Override
            public void disposeView() {
                        list.dispose();
            }

            @Override
            public OWLNamedIndividual updateView(OWLNamedIndividual selelectedIndividual) {
                        list.setRootObject(selelectedIndividual);
                        return selelectedIndividual;
            }
}

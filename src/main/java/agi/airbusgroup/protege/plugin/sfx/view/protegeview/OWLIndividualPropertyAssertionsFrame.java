/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.protegeview;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.frame.AbstractOWLFrame;
import org.protege.editor.owl.ui.frame.individual.OWLNegativeDataPropertyAssertionFrameSection;
import org.protege.editor.owl.ui.frame.individual.OWLNegativeObjectPropertyAssertionFrameSection;
import org.protege.editor.owl.ui.frame.individual.OWLObjectPropertyAssertionAxiomFrameSection;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 *
 * @author ZONGO
 * @param <O>
 */
public class OWLIndividualPropertyAssertionsFrame<O extends OWLIndividual> extends AbstractOWLFrame<O> {

            public OWLIndividualPropertyAssertionsFrame(OWLEditorKit owlEditorKit) {
                        super(owlEditorKit.getModelManager().getOWLOntologyManager());
                        addSection(new OWLObjectPropertyAssertionAxiomFrameSection(owlEditorKit, this));
                        addSection(new OWLDataPropertyAssertionAxiomFrameSection(owlEditorKit, this));
                        addSection(new OWLNegativeObjectPropertyAssertionFrameSection(owlEditorKit, this));
                        addSection(new OWLNegativeDataPropertyAssertionFrameSection(owlEditorKit, this));
            }
}

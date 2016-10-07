/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.search.EntitySearcher;
import agi.airbusgroup.protege.plugin.sfx.model.exception.ClassMappingException;
import agi.airbusgroup.protege.plugin.sfx.model.SynonymsEntry;
import agi.airbusgroup.protege.plugin.sfx.model.SynonymsFinder;

/**
 *
 * @author ZONGO
 */
public class SynonymController {

            private OWLEditorKit editorKit;
            private ArrayList<String> terminologies;

            public SynonymController(OWLEditorKit editorKit) {
                        this.editorKit = editorKit;
            }

            public ArrayList<SynonymsEntry> getSynonyms(OWLNamedIndividual selectedIndividual, float similarity) throws ClassMappingException {
                        SynonymsFinder synonymsFinder = new SynonymsFinder();
                        String individualName = selectedIndividual.getIRI().getFragment();

                        Collection<OWLClassExpression> classExpressions = EntitySearcher.getTypes(selectedIndividual, editorKit.getModelManager().getActiveOntology());
                        Iterator<OWLClassExpression> it = classExpressions.iterator();
                        OWLClass individualClass = it.next().asOWLClass();

                        String individualTopClassName = getTopClass(individualClass).getIRI().getFragment();
                        ArrayList<SynonymsEntry> synonymsEntries = synonymsFinder.getSynonyms(individualName, individualTopClassName, similarity);
                        terminologies = synonymsFinder.getTerminologies();
                        return synonymsEntries;
            }

            public ArrayList<String> getTerminologies() {
                        return terminologies;
            }

            private OWLClass getTopClass(OWLClass individualClass) {
                        Collection<OWLClassExpression> classExpressions = EntitySearcher.getSuperClasses(individualClass, editorKit.getModelManager().getActiveOntology());
                        Iterator<OWLClassExpression> it = classExpressions.iterator();
                        if (!it.hasNext()) {
                                    return individualClass;
                        }
                        OWLClass superClass = it.next().asOWLClass();
                        return getTopClass(superClass);
            }

}

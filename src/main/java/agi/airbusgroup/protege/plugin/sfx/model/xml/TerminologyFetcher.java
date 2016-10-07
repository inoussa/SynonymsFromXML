/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import agi.airbusgroup.protege.plugin.sfx.model.Synonym;
import agi.airbusgroup.protege.plugin.sfx.model.SynonymsFinder;
import org.apache.commons.lang3.StringUtils;
import agi.airbusgroup.protege.plugin.sfx.model.SynonymsEntry;
import agi.airbusgroup.protege.plugin.sfx.model.LangSynonyms;

/**
 *
 * @author ZONGO
 */
public class TerminologyFetcher {

            private File file;
            private String value;
            private Float minSimilarity;

            final String termEntryElementName = "termEntry";
            final String langSetElementName = "langSet";
            final String termElementName = "term";
            final String langElementName = "lang";
            final String descripElementName = "descrip";
            final String typeAttributeName = "type";
            //final String 

            public TerminologyFetcher(File file, String value, Float minSimilarity) {
                        this.file = file;
                        this.value = "";
                        if (value.contains("_")) {
                                    String[] value1 = value.split("_");
                                    for (int i = 1; i < value1.length; i++) {
                                                this.value += value1[i];
                                                if (i != value1.length - 1) {
                                                            this.value += " ";
                                                }
                                    }
                        } else {
                                    this.value = value;
                        }
                        this.minSimilarity = minSimilarity;
            }

            public ArrayList<SynonymsEntry> findSynonym() {
                        SAXBuilder jdomBuilder = new SAXBuilder();
                        ArrayList<SynonymsEntry> synonyms = new ArrayList<SynonymsEntry>();
                        try {
                                    Document jdomDocument = jdomBuilder.build(file);
                                    Element root = jdomDocument.getRootElement();
                                    Filter<Element> termEntryFilter = Filters.element(termEntryElementName, root.getNamespace());
                                    Filter<Element> langFilter = Filters.element(langSetElementName, root.getNamespace());
                                    Filter<Element> termFilter = Filters.element(termElementName, root.getNamespace());
                                    Filter<Element> descripFilter = Filters.element(descripElementName, root.getNamespace());

                                    IteratorIterable<Element> termEntries = root.getDescendants(termEntryFilter);
                                    while (termEntries.hasNext()) {
                                                Element termEntry = termEntries.next();
                                                IteratorIterable<Element> terms = termEntry.getDescendants(termFilter);
                                                while (terms.hasNext()) {
                                                            Element term = terms.next();
                                                            int nbChange = StringUtils.getLevenshteinDistance(term.getText().toLowerCase(), value.toLowerCase());
                                                            int longest = (value.length() > term.getText().length()) ? value.length() : term.getText().length();
                                                            float similarity = 1.0f - ((float) nbChange / (float) longest);
                                                            if (similarity >= minSimilarity) {
                                                                        String synonymFound = term.getText();
                                                                        IteratorIterable<Element> langSets = termEntry.getDescendants(langFilter);
                                                                        //

                                                                        SynonymsEntry individualSynonyms = new SynonymsEntry(synonymFound);
                                                                        while (langSets.hasNext()) {
                                                                                    Element langSet = langSets.next();
                                                                                    SynonymsFinder.Lang lang = null;
                                                                                    String langValue = langSet.getAttributeValue(langElementName, Namespace.XML_NAMESPACE).toLowerCase();
                                                                                    for (SynonymsFinder.Lang currentLang : SynonymsFinder.Lang.values()) {
                                                                                                if (langValue.contains(currentLang.toString())) {
                                                                                                            lang = currentLang;
                                                                                                }
                                                                                    }
                                                                                    if (lang == null) {
                                                                                                break;
                                                                                    }

                                                                                    LangSynonyms langSynonyms = new LangSynonyms(lang.toString());

                                                                                    IteratorIterable<Element> langSetTerms = langSet.getDescendants(termFilter);

                                                                                    while (langSetTerms.hasNext()) {
                                                                                                Element langSetTerm = langSetTerms.next();
                                                                                                Synonym synonym = new Synonym(langSetTerm.getText());
                                                                                                langSynonyms.add(synonym);
                                                                                                IteratorIterable<Element> descripElements = langSetTerm.getParentElement().getDescendants(descripFilter);
                                                                                                while (descripElements.hasNext()) {
                                                                                                            Element descripElement = descripElements.next();
                                                                                                            if (descripElement.getAttributeValue(typeAttributeName).equals("Approval Status") && descripElement.getText().equals("Not recommended")) {
                                                                                                                        synonym.setRecommended(Boolean.FALSE);
                                                                                                            }
                                                                                                            if (descripElement.getAttributeValue(typeAttributeName).equals("Approval Status") && descripElement.getText().equals("Approved")) {
                                                                                                                        synonym.setRecommended(Boolean.TRUE);
                                                                                                            }

                                                                                                }

                                                                                    }
                                                                                    individualSynonyms.add(langSynonyms);
                                                                        }
                                                                        synonyms.add(individualSynonyms);

                                                                        break;
                                                            }

                                                }
                                    }

                        } catch (JDOMException e) {

                        } catch (IOException e) {

                        }

                        return synonyms;
            }

}

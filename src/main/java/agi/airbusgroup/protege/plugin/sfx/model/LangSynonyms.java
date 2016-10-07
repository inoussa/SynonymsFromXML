/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ZONGO
 */
public class LangSynonyms {

            private ArrayList<Synonym> synonyms;
            private String lang;

            public LangSynonyms(String lang) {
                        this.lang = lang;
                        synonyms = new ArrayList<Synonym>();
            }

            public void addSynonym(Synonym synonym) {
                        getSynonyms().add(synonym);
            }

            public void removeSynonym(Synonym synonym) {
                        getSynonyms().remove(synonym);
            }

            public Synonym getSynonym(int index) {
                        return getSynonyms().get(index);
            }

            public Iterator<Synonym> iterator() {
                        return getSynonyms().iterator();
            }

            /**
             * @return the synonyms
             */
            public ArrayList<Synonym> getSynonyms() {
                        return synonyms;
            }

            public void add(Synonym synonym) {
                        synonyms.add(synonym);
            }

            /**
             * @param synonyms the synonyms to set
             */
            public void setSynonyms(ArrayList<Synonym> synonyms) {
                        this.synonyms = synonyms;
            }

            /**
             * @return the lang
             */
            public String getLang() {
                        return lang;
            }

            /**
             * @param lang the lang to set
             */
            public void setLang(String lang) {
                        this.lang = lang;
            }

}

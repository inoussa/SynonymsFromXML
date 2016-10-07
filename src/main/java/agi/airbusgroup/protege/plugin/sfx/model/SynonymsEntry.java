/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model;

import java.util.ArrayList;

/**
 *
 * @author ZONGO
 */
public class SynonymsEntry {

            private ArrayList<LangSynonyms> langSynonyms;
            private String key;

            public SynonymsEntry(String key) {
                        langSynonyms = new ArrayList<LangSynonyms>();
                        this.key = key;
            }

            public void add(LangSynonyms langSynonyms) {
                        this.getLangSynonyms().add(langSynonyms);
            }

            /**
             * @return the langSynonyms
             */
            public ArrayList<LangSynonyms> getLangSynonyms() {
                        return langSynonyms;
            }

            /**
             * @param langSynonyms the langSynonyms to set
             */
            public void setLangSynonyms(ArrayList<LangSynonyms> langSynonyms) {
                        this.langSynonyms = langSynonyms;
            }

            /**
             * @return the key
             */
            public String getKey() {
                        return key;
            }

            /**
             * @param key the key to set
             */
            public void setKey(String key) {
                        this.key = key;
            }

}

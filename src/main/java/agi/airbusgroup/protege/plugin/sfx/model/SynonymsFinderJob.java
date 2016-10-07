/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import agi.airbusgroup.protege.plugin.sfx.model.exception.ClassMappingException;
import agi.airbusgroup.protege.plugin.sfx.model.xml.TerminologyFetcher;

/**
 *
 * @author ZONGO
 */
public class SynonymsFinderJob implements Callable<ArrayList<SynonymsEntry>> {

            private String terminology;
            private String individual;
            private Float minSimilarity;

            public SynonymsFinderJob(String terminolgy, String individual, Float minSimilarity) {
                        this.terminology = terminolgy;
                        this.individual = individual;
                        this.minSimilarity = minSimilarity;
            }

            @Override
            public ArrayList<SynonymsEntry> call() throws ClassMappingException {
                        try {
                                    Logger.getLogger(SynonymsFinderJob.class).info("Searching synonyms of " + individual + " in " + terminology + " with a similarity rate of " + minSimilarity);
                                    ArrayList<SynonymsEntry> synonymsEntries;
                                    File terminologyFile = new File("plugins/sfx-data/" + terminology);
                                    if (!terminologyFile.isFile() || !terminologyFile.exists()) {
                                                throw new ClassMappingException("Le fichier " + terminology + " est introuvable");
                                    }
                                    TerminologyFetcher terminologyFetcher = new TerminologyFetcher(terminologyFile, individual, minSimilarity);
                                    synonymsEntries = terminologyFetcher.findSynonym();
                                    return synonymsEntries;

                        } catch (ClassMappingException e) {
                                    throw new ClassMappingException(e.getMessage());
                        }
            }

}

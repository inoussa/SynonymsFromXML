/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import agi.airbusgroup.protege.plugin.sfx.model.exception.ClassMappingException;
import agi.airbusgroup.protege.plugin.sfx.model.xml.ConfFileReader;

/**
 *
 * @author ZONGO
 */
public class SynonymsFinder {

            public enum Lang {

                        en, fr, de, pt, es
            };
            private ArrayList<String> terminologies;

            public ArrayList<SynonymsEntry> getSynonyms(String individual, String individualTopClass, float minSimilarity) throws ClassMappingException {
                        ConfFileReader confFileReader = new ConfFileReader("plugins/sfx-data/config.xml");
                        ArrayList<SynonymsEntry> synonymsEntrys = new ArrayList<SynonymsEntry>();
                        try {
                                    terminologies = confFileReader.read(individualTopClass);
                                    int nbJob = terminologies.size();
                                    ExecutorService executor = Executors.newFixedThreadPool(nbJob);

                                    ArrayList<Future<ArrayList<SynonymsEntry>>> results = new ArrayList<Future<ArrayList<SynonymsEntry>>>();
                                    for (String terminologie : terminologies) {
                                                SynonymsFinderJob task = new SynonymsFinderJob(terminologie, individual, minSimilarity);
                                                Future<ArrayList<SynonymsEntry>> result = executor.submit(task);
                                                results.add(result);
                                    }
                                    executor.shutdown();

                                    for (int i = 0; i < nbJob; i++) {
                                                Future<ArrayList<SynonymsEntry>> expectedResult = results.get(i);
                                                synonymsEntrys.addAll(expectedResult.get());
                                    }

                        } catch (ClassMappingException e) {
                                    throw new ClassMappingException(e.getMessage());
                        } catch (InterruptedException e) {
                        } catch (ExecutionException e) {
                        }
                        return synonymsEntrys;
            }

            public ArrayList<String> getTerminologies() {
                        return terminologies;
            }

}

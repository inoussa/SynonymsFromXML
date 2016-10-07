/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import agi.airbusgroup.protege.plugin.sfx.model.exception.ClassMappingException;

/**
 *
 * @author ZONGO
 */
public class ConfFileReader {

            private String confFilePath;

            public ConfFileReader(String confFilePath) {
                        this.confFilePath = confFilePath;
            }

            public ArrayList<String> read(String owlClassName) throws ClassMappingException {
                        ArrayList<String> terminologies = new ArrayList<String>();
                        File confFile = new File(confFilePath);
                        //We replace the underline charactere with space charactere because in the otology 
                        // individuals name and classes name that contains spaces are replaced by  underline charactere.
                        String safeOWLClassName = owlClassName.replace("_", " ");
                        if (!confFile.isFile()) {
                                    throw new ClassMappingException("Le fichier de correpondance classe-terminology est introuvable");
                        }
                        try {
                                    SAXBuilder jdomBuilder = new SAXBuilder();
                                    Document jdomDocument = jdomBuilder.build(confFile);
                                    Element root = jdomDocument.getRootElement();
                                    List<Element> classEntries = root.getChildren();

                                    Iterator<Element> iterator = classEntries.iterator();
                                    while (iterator.hasNext()) {
                                                Element element = iterator.next();
                                                Element nameElement = element.getChild("name");
                                                if (nameElement != null && nameElement.getText().equals(safeOWLClassName)) {
                                                            for (Element element1 : element.getChildren("terminology")) {
                                                                        terminologies.add(element1.getText());
                                                            }
                                                            break;
                                                }

                                    }
                        } catch (JDOMException e) {
                                    throw new ClassMappingException("Erreur de lecture du fichier config.xml<br>Le fichier n'est pas bien formé");
                        } catch (IOException e) {
                                    throw new ClassMappingException("Le fichier de correpondance classe-terminology es introuvable");
                        }

                        if (terminologies.isEmpty()) {
                                    throw new ClassMappingException("Aucune entrée du fichier de configuration ne concerne la classe " + safeOWLClassName);
                        }
                        return terminologies;
            }
}

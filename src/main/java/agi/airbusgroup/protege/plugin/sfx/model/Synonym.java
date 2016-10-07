/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.model;

/**
 * Synonym model object
 * <p>
 * This class describe a synonym
 *
 * @author ZONGO
 */
public class Synonym {

            /**
             * The string corresponding to the synonym.
             */
            private String value;
            /**
             * Indicate the synonym recommendation status<br>
             * true if the synonym is recommended<br>
             * false if the synonym is not recommende <br>
             * nul if the synonym recommendation status is unknown
             */
            private Boolean recommended;

            /**
             * Synonym constructor
             *
             * @param value
             */
            public Synonym(String value) {
                        this.value = value;
            }

            /**
             * @return the value
             */
            public String getValue() {
                        return value;
            }

            /**
             * @param value the value to set
             */
            public void setValue(String value) {
                        this.value = value;
            }

            /**
             * @return the recommended
             */
            public Boolean getRecommended() {
                        return recommended;
            }

            /**
             * @param recommended the recommended to set
             */
            public void setRecommended(Boolean recommended) {
                        this.recommended = recommended;
            }

            /**
             * @return the isRecommended
             */
}

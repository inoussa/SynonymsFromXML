/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.tree;

/**
 *
 * @author ZONGO
 */
public class CheckBoxNodeData {

            private String text;
            private boolean checked;
            private Boolean isRecommanded;

            public CheckBoxNodeData(final String text, final boolean checked, Boolean isRecommanded) {
                        this.text = text;
                        this.checked = checked;
                        this.isRecommanded = isRecommanded;
            }

            public boolean isChecked() {
                        return checked;
            }

            public void setChecked(final boolean checked) {
                        this.checked = checked;
            }

            public String getText() {
                        return text;
            }

            public void setText(final String text) {
                        this.text = text;
            }

            @Override
            public String toString() {
                        return getClass().getName() + "[" + text + "/" + checked + "]";
            }

            /**
             * @return the isRecommanded
             */
            public Boolean getIsRecommanded() {
                        return isRecommanded;
            }

            /**
             * @param isRecommanded the isRecommanded to set
             */
            public void setIsRecommanded(Boolean isRecommanded) {
                        this.isRecommanded = isRecommanded;
            }

}

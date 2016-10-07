/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agi.airbusgroup.protege.plugin.sfx.view.protegeview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import org.protege.editor.core.ui.list.MListButton;

/**
 *
 * @author ZONGO
 */
public class MListAddButton extends MListButton {

            public MListAddButton(String name, Color rollOverColor, ActionListener listener) {
                        super(name, rollOverColor, listener);
            }

            @Override
            public void paintButtonContent(Graphics2D g) {
                        int size = getBounds().height;
                        int thickness = (Math.round(size / 8.0f) / 2) * 2;

                        int x = getBounds().x;
                        int y = getBounds().y;

                        int insetX = size / 4;
                        int insetY = size / 4;
                        int insetHeight = size / 2;
                        int insetWidth = size / 2;
                        g.fillRect(x + size / 2 - thickness / 2, y + insetY, thickness, insetHeight);
                        g.fillRect(x + insetX, y + size / 2 - thickness / 2, insetWidth, thickness);
            }

            @Override
            public Color getBackground() {
                        return Color.RED;
            }

            @Override
            protected int getSizeMultiple() {
                        return 4;
            }

}

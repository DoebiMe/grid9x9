package be.doubbel.sudo.gui;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Gird9x9 extends VerticalLayout {
    public Gird9x9() {
        addClassName("gird9x9");
        for (int row = 1;row < 10;row++) {
            GridRow gridRow = new GridRow(row);
            add(gridRow);
        }
    }
}

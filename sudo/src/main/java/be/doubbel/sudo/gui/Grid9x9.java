package be.doubbel.sudo.gui;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Grid9x9 extends VerticalLayout {
    public Grid9x9() {
        removeAll();
        addClassName("gird9x9");
        for (int row = 1;row < 10;row++) {
            GridRow gridRow = new GridRow(row);
            add(gridRow);
        }
    }
}

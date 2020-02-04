package be.doubbel.sudo.gui;

import be.doubbel.sudo.gui.GridCell;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class GridRow extends FlexLayout {
    public GridRow(Integer row) {
        addClassName("gridRow");
        for (int col = 1;col<10;col++) {
            GridCell gridCell = new GridCell(row,col);
            add(gridCell);
        }
    }
}

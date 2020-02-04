package be.doubbel.sudo.gui;

import be.doubbel.sudo.gui.CandidateRow;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class GridCell extends VerticalLayout {
    public GridCell(Integer row,Integer col) {
        addClassName("gridCell");
        ButtonGridCell buttonGridCell = new ButtonGridCell(row,col);
        CandidateRow candidateRow = new CandidateRow(row,col);
        add(candidateRow,buttonGridCell);
    }
}

package be.doubbel.sudo.gui;

import be.doubbel.sudo.gui.Candidate;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CandidateRow extends FlexLayout {
    public CandidateRow(Integer row,Integer col) {
        addClassName("candidateRow");
        for (Integer candidateNumber =1; candidateNumber<10;candidateNumber++) {
            Candidate candidate =
                    new Candidate(row,col,candidateNumber);
            add(candidate);
        }
    }
}

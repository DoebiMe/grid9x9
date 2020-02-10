package be.doubbel.sudo.gui;

import be.doubbel.sudo.gui.Candidate;
import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class CandidateRow extends FlexLayout {
    public CandidateRow(Integer row,Integer col) {
        addClassName("candidateRow");
        SudoService sudoService = SudoService.getInstance();
        List<Integer> listCandidates = sudoService.getCellCandidatesValues(row-1,col-1);
        for (Integer candidateNumber =1; candidateNumber<10;candidateNumber++) {
            Candidate candidate =
                    new Candidate(row,col,
                        listCandidates.contains(candidateNumber)
                        ? candidateNumber
                        : 0);
            add(candidate);
        }
    }
}

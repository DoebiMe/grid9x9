package be.doubbel.sudo.gui;

import com.vaadin.flow.component.html.Label;

public class Candidate extends Label {
    public Candidate(Integer row,Integer col,Integer candidateNr) {
        addClassName("candidate");
        this.setText(  Integer.toString(candidateNr));
    }
}

package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.FileService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Grid9x9 extends VerticalLayout {

    private static Grid9x9 instance;

    private Grid9x9() {}

    public static Grid9x9 getInstance() {
        // zie https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
        if (instance == null) {
            synchronized (Grid9x9.class) {
                if (instance == null) {
                    instance = new Grid9x9();
                }
            }
        }
        return instance;
    }

    public void update9x9(){
        removeAll();
        addClassName("gird9x9");
        for (int row = 1;row < 10;row++) {
            GridRow gridRow = new GridRow(row);
            add(gridRow);
        }
    }
}

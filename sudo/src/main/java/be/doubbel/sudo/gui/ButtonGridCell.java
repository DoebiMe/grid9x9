package be.doubbel.sudo.gui;

import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ButtonGridCell extends FlexLayout {
    public ButtonGridCell(Integer row,Integer col) {
        NativeButton button = new NativeButton("5");
        button.addClassName("buttonGridCell");
        addClassName("buttonGridCell");
        add(button);
    }
}

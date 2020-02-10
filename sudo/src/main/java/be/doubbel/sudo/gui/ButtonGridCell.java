package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ButtonGridCell extends FlexLayout {
    public ButtonGridCell(Integer row,Integer col) {
        NativeButton button = new NativeButton("5");
        button.addClassName("buttonGridCell");
        addClassName("buttonGridCell");
        SudoService sudoService = SudoService.getInstance();
        Integer value = sudoService.getCellValue(row-1, col-1);
        if (value==null) value = 0;
        button.setText(value.toString());
        add(button);
    }
}

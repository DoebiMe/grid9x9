package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ButtonGridCell extends FlexLayout {
    private Integer buttonRow;
    private Integer buttonCol;
    public ButtonGridCell(Integer row,Integer col) {
        buttonCol=col;
        buttonRow=row;
        NativeButton button = new NativeButton("5");
        button.addClassName("buttonGridCell");
        addClassName("buttonGridCell");
        SudoService sudoService = SudoService.getInstance();
        Integer value = sudoService.getCellValue(row-1, col-1);
        if (value==null) value = 0;
        button.setText(value.toString());
        button.addClickListener(event -> buttonClicked());
        add(button);
    }
    public void buttonClicked() {
        Notification.show("Clicked on row " + buttonRow + " col "+ buttonCol);
    }
}

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

        if (buttonCol.equals(SudoService.getInstance().getUserSelectedCol()) &&
            buttonRow.equals(SudoService.getInstance().getUserSelectedRow())) {
            addClassName("selectedCell");
            button.addClassName("selectedCell");
            System.out.println("In de sudoservice row = " + SudoService.getInstance().getOldUserSelectedRow());
        }
        else
        {
            addClassName("notSelectedCell");
            button.addClassName("notSelectedCell");
        }

        SudoService sudoService = SudoService.getInstance();
        Integer value = sudoService.getCellValue(row-1, col-1);
        if (value==null) value = 0;
        button.setText(value.toString());
        button.addClickListener(event -> buttonClicked());
        add(button);
    }
    public void buttonClicked() {
        SudoService.getInstance().setUserSelectedRowAndCol(buttonRow,buttonCol);
        Grid9x9.getInstance().update9x9();
    }
}

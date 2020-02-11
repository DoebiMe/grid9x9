package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class SelectionBar extends VerticalLayout {

		HorizontalLayout topOfSelectionBar = new HorizontalLayout();
		HorizontalLayout bottomOfSelectionBar = new HorizontalLayout();

		public SelectionBar() {
				addClassName("selectionBar");
				for (int loop = 0; loop < 9; loop++) {
						NativeButton button = new NativeButton(Integer.toString(loop + 1));
						button.addClassName("selectionBarNumber");
						topOfSelectionBar.add(button);
						int value = loop +1;
						button.addClickListener(event -> {
								SudoService.getInstance()
										.setUserSelectedCellValueAffectCanditates(value);

								Grid9x9.getInstance().update9x9();
						});
				}
				NativeButton buttonErase = new NativeButton("Erase");
				NativeButton buttonReset = new NativeButton("Reset");
				NativeButton buttonSolve = new NativeButton("Solve");
				buttonErase.addClassName("selectionBarButton");
				buttonReset.addClassName("selectionBarButton");
				buttonSolve.addClassName("selectionBarButton");
				bottomOfSelectionBar.add(buttonErase, buttonReset, buttonSolve);
				add(topOfSelectionBar, bottomOfSelectionBar);
		}
}

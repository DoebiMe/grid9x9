package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class SelectionBar extends VerticalLayout {

		HorizontalLayout topOfSelectionBar = new HorizontalLayout();
		HorizontalLayout bottomOfSelectionBar = new HorizontalLayout();
		SudoService sudoService;
		Grid9x9 grid9x9;

		public SelectionBar() {
				sudoService = SudoService.getInstance();
				grid9x9 = Grid9x9.getInstance();
				addClassName("selectionBar");
				for (int loop = 0; loop < 9; loop++) {
						NativeButton button = new NativeButton(Integer.toString(loop + 1));
						button.addClassName("selectionBarNumber");
						topOfSelectionBar.add(button);
						int value = loop + 1;
						button.addClickListener(event -> {
								sudoService.setUserSelectedCellValueAffectCanditates(value);
								grid9x9.update9x9();
						});
				}
				NativeButton buttonErase = new NativeButton("Erase");
				NativeButton buttonReset = new NativeButton("Reset");
				NativeButton buttonSolve = new NativeButton("Solve");

				buttonReset.addClickListener(event -> {
						sudoService.resetToOriginalValues();
						sudoService.setAllCandidatesInCellWithoutValue();
						grid9x9.update9x9();
				});

				buttonErase.addClassName("selectionBarButton");
				buttonReset.addClassName("selectionBarButton");
				buttonSolve.addClassName("selectionBarButton");
				bottomOfSelectionBar.add(buttonErase, buttonReset, buttonSolve);
				add(topOfSelectionBar, bottomOfSelectionBar);
		}
}

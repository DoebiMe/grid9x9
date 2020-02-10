package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.FileService;
import be.doubbel.sudo.service.GreetService;
import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route

@PWA(name = "Doubbel Sudo", shortName = "Doebi Sudo", description = "This is the Doubbel Sudo application.", enableInstallPrompt = true)

@CssImport("./styles/shared-styles.css")
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

		/**
		 * Construct a new Vaadin view.
		 * <p>
		 * Build the initial UI state for the user accessing the application.
		 *
		 * @param service The message service. Automatically injected Spring managed bean.
		 */
		public Grid9x9 grid9x9 = new Grid9x9();
		public ComboBox<String> fileListBox = new ComboBox<>();
		public String currentResourceName = "s01a.txt";

		public MainView()  {
				addClassName("mainForm");
				HorizontalLayout horizontalLayout = new HorizontalLayout();
				VerticalLayout verticalLayout = new VerticalLayout();
				verticalLayout.add(grid9x9, createLoadButton());
				horizontalLayout.add(verticalLayout, createFileDropDown());
				add(horizontalLayout);
		}

		public ComboBox<String> createFileDropDown() {
		    fileListBox.addClassName("fileDropDown");
				FileService fileService = FileService.getInstance();
				fileListBox.setLabel("Select resource");
				fileListBox.setItems(fileService.getResourceFolderFiles());
        fileListBox.addValueChangeListener(recourse -> {
            currentResourceName = fileListBox.getValue();
            SudoService sudoService = SudoService.getInstance();
            sudoService.loadOriginalValues(currentResourceName);
            refreshGrid9x9();
        });
				return fileListBox;
		}

		public void refreshGrid9x9() {
				grid9x9.update9x9();
		}

		public Button createLoadButton() {
				Button loadSudoButton = new Button("Load Sudo");
				loadSudoButton.addClickListener(buttonClickEvent -> {
						SudoService sudoService = SudoService.getInstance();
						sudoService.loadOriginalValues(currentResourceName);
						refreshGrid9x9();
				});
				loadSudoButton.setHeight("5px");
				return loadSudoButton;
		}
}

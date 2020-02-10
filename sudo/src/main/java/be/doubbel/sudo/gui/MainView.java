package be.doubbel.sudo.gui;

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

@PWA(name = "Doubbel Sudo",
        shortName = "Doebi Sudo",
        description = "This is the Doubbel Sudo application.",
        enableInstallPrompt = true)

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
    public Grid9x9 grid9x9;
    public ComboBox<String> fileListBox;
    public MainView(@Autowired GreetService service) {
        addClassName("mainForm");
        grid9x9 = new Grid9x9();
        fileListBox = new ComboBox<>();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(grid9x9,createLoadButton());
        horizontalLayout.add(verticalLayout,createFileDropDown());
        add(horizontalLayout);

    }
    public ComboBox<String> createFileDropDown() {
        fileListBox.setItems("Option one", "Option two");
        fileListBox.setLabel("Label");

        ComboBox<String> placeHolderComboBox = new ComboBox<>();
        placeHolderComboBox.setItems("Option one", "Option two");
        placeHolderComboBox.setPlaceholder("Placeholder");

        ComboBox<String> valueComboBox = new ComboBox<>();
        valueComboBox.setItems("Value", "Option one", "Option two");
        valueComboBox.setValue("Value");
        return fileListBox;
    }
    public void refreshGrid9x9() {
        grid9x9.update9x9();
        System.out.println("Do refresh");
        Notification.show("Do refresh");
    }

    public Button createLoadButton() {
        Button loadSudoButton = new Button("Load Sudo");
        loadSudoButton.addClickListener(buttonClickEvent -> {
            SudoService sudoService = SudoService.getInstance();
            sudoService.loadOriginalValues("s01a.txt");
            refreshGrid9x9();
        });
        loadSudoButton.setHeight("5px");
        return loadSudoButton;
    }

}

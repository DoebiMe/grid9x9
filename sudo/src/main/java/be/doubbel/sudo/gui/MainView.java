package be.doubbel.sudo.gui;

import be.doubbel.sudo.service.GreetService;
import be.doubbel.sudo.service.SudoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
    public MainView(@Autowired GreetService service) {
        addClassName("mainForm");
        grid9x9 = new Grid9x9();
        add(grid9x9,createLoadButton());
    }
    public void refreshGrid9x9() {
        grid9x9 = new Grid9x9();
    }

    public Button createLoadButton() {
        Button loadSudoButton = new Button("Load Sudo");
        loadSudoButton.addClickListener(buttonClickEvent -> {
            SudoService.loadOriginalValues("s01a.txt");
            refreshGrid9x9();
        });
        loadSudoButton.setHeight("5px");
        return loadSudoButton;
    }

}

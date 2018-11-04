package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.security.LogoutEvent;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ExitRegisterEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowLoginEvent;
import seedu.address.commons.events.ui.ShowRegisterEvent;
import seedu.address.commons.events.ui.SuccessfulRegisterEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.security.Security;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Security security;

    // Independent Ui parts residing in this Ui container
    private TimeTablePanel timetablePanel;
    private MePanel mePanel;
    private OtherListPanel otherListPanel;
    private FriendListPanel friendListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private LoginWindow loginWindow;
    private RegistrationWindow registrationWindow;

    @FXML
    private Text meText;

    @FXML
    private Text friendText;

    @FXML
    private Text personText;

    @FXML
    private StackPane timetablePlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mePanelPlaceholder;

    @FXML
    private StackPane friendListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Security security) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.security = security;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
        loginWindow = new LoginWindow(security);
        registrationWindow = new RegistrationWindow(security);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Enables Security CLI
     */
    public void fillSecurityCommandBox() {
        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        SecurityResultDisplay securityResultDisplay = new SecurityResultDisplay();
        resultDisplayPlaceholder.getChildren().clear();
        resultDisplayPlaceholder.getChildren().add(securityResultDisplay.getRoot());
    }

    /**
     * Clear displays when user is logging out
     */
    private void removeInnerParts() {
        commandBoxPlaceholder.getChildren().clear();
        resultDisplayPlaceholder.getChildren().clear();
        mePanelPlaceholder.getChildren().clear();
        timetablePlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().clear();
        friendListPanelPlaceholder.getChildren().clear();
        statusbarPlaceholder.getChildren().clear();
        friendText.setText("");
        personText.setText("");
        meText.setText("");
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {

        meText.setText("Me");
        meText.setFill(Color.LIGHTGOLDENRODYELLOW);
        meText.setStyle("-fx-font-size: 20px;");
        friendText.setText("Friends");
        friendText.setFill(Color.LIGHTGOLDENRODYELLOW);
        friendText.setStyle("-fx-font-size: 20px;");
        personText.setText("Others");
        personText.setFill(Color.LIGHTGOLDENRODYELLOW);
        personText.setStyle("-fx-font-size: 20px;");

        timetablePanel = new TimeTablePanel();
        timetablePlaceholder.getChildren().add(timetablePanel.getRoot());

        mePanel = new MePanel(FXCollections.observableArrayList(security.getUser()));
        mePanelPlaceholder.getChildren().add(mePanel.getRoot());

        friendListPanel = new FriendListPanel(logic.getFriendList(security.getUser()));
        friendListPanelPlaceholder.getChildren().add(friendListPanel.getRoot());

        otherListPanel = new OtherListPanel(logic.getOtherList(security.getUser()));
        personListPanelPlaceholder.getChildren().add(otherListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().clear();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the Login Window.
     */
    @FXML
    public void handleLogin() {
        if (!loginWindow.isShowing()) {
            loginWindow.show();
        } else {
            loginWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Opens the Registration Window.
     */
    public void handleRegister() {
        loginWindow.hide();
        registrationWindow.show();
    }

    /***
     * Handles successful registration
     */
    public void handleSuccessRegister() {
        registrationWindow.hide();
        raise(new SuccessfulLoginEvent()); //Calls method fill in data
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /***
     * Logs out of the application
     */
    @FXML
    public void handleLogout() {
        security.logout();
        removeInnerParts();
        fillSecurityCommandBox();
    }

    public OtherListPanel getOtherListPanel() {
        return otherListPanel;
    }

    void releaseResources() {
        //timetablePanel.freeResources(); TODO: do the equivalent of releasing resources of browserPanel webview?
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleShowRegisterEvent(ShowRegisterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleRegister();
    }

    @Subscribe
    private void handleExitRegisterEvent(ExitRegisterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLogin();
    }

    @Subscribe
    private void handleShowLoginEvent(ShowLoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLogin();
    }

    @Subscribe
    private void handleSuccessfulRegisterEvent(SuccessfulRegisterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSuccessRegister();
    }

    @Subscribe
    public void handleLogoutEvent(LogoutEvent logout) {
        security.logout();
        removeInnerParts();
        fillSecurityCommandBox();
    }
}

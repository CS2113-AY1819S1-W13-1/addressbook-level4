package seedu.address.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;


public class SecurityManagerTest {

    private static SecurityManager securityManager;
    private static Model model;
    private static Logic logic;
    private static UserPrefs userPrefs;

    private static boolean succcessfulLoginEventCalled;
    private static boolean unsuccessfulLoginEventCalled;

    public SecurityManagerTest() {
        registerAsAnEventHandler(this);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    //Set up subscribe to test whether raise events are successfully raised and subscribed
    @Subscribe
    public void handleSuccessfulLoginEvent(SuccessfulLoginEvent loginSuccess) {
        this.succcessfulLoginEventCalled = true;
    }

    @Subscribe
    public void handleUnsuccessfulLoginEvent(UnsuccessfulLoginEvent loginFailure) {
        this.unsuccessfulLoginEventCalled = true;
    }

    @BeforeClass
    public static void testFixtureSetup() {
        succcessfulLoginEventCalled = false;
        unsuccessfulLoginEventCalled = false;
        userPrefs = new UserPrefs();
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();

        model = new ModelManager(addressBook, userPrefs);
        logic = new LogicManager(model);

        securityManager = new SecurityManager(false, model, logic);
    }

    @Before
    public void resetFixture() {
        succcessfulLoginEventCalled = false;
        unsuccessfulLoginEventCalled = false;
        securityManager.logout();
    }

    @Test
    public void login_correctCredentials_isAuthenticatedToTrue() {
        securityManager.login("test", "test");
        assertTrue(securityManager.getAuthentication());
    }

    @Test
    public void login_incorrectCredentials_isAuthenticatedRemainsFalse() {
        securityManager.login("test", "test1");
        assertFalse(securityManager.getAuthentication());
    }

    @Test
    public void login_correctCredentials_raisedSuccessfulLoginEvent() {
        securityManager.login("test", "test");
        assertTrue(this.succcessfulLoginEventCalled);
        assertFalse(this.unsuccessfulLoginEventCalled);
    }

    @Test
    public void login_incorrectCredentials_raisedUnsuccessfulLoginEvent() {
        securityManager.login("test", "test1");
        assertFalse(this.succcessfulLoginEventCalled);
        assertTrue(this.unsuccessfulLoginEventCalled);
    }

    @Test
    public void logout_isAuthenticatedToFalse() {
        securityManager.login("test", "test");
        securityManager.logout();
        assertFalse(securityManager.getAuthentication());
    }
}

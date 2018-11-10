package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.User;
import seedu.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the friends of the person */
    ObservableList<Person> getFriendList();

    /** Returns an unmodifiable view of the non-friends of the person */
    ObservableList<Person> getOtherList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /**
     * Instantiates the user with a Person in database
     * @param name
     */
    void matchUserToPerson(String name);

    /**
     * Clears the user instance when logging out
     */
    void clearUser();

    /**
     *
     * @return the current authenticated User
     */
    User getUser();
}

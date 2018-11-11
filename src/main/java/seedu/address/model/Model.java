package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the TimeTable */
    TimeTable getTimeTable();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same name alone exists in the address book.
     */
    boolean hasPersonToRegister(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the timetable shown with a new timetable
     * @param timeTable Timetable to replace
     */
    void updateTimeTable(TimeTable timeTable);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Update the friend list to show the current
     */
    void updateFriendList(Predicate<Person> predicate);

    /**
     * Updates the others list to show current
     */
    void updateOtherList(Predicate<Person> predicate);

    /**
     * Return the friends list of the current user
     */
    ObservableList<Person> getFriendList();

    /**
     * Return the current friend list with the predicates still set
     */
    ObservableList<Person> getCurrentFriendList();

    /**
     * Return the current other list with the predicate still set
     */
    ObservableList<Person> getCurrentOtherList();

    /**
     * Return the non-friends list of the current user
     */
    ObservableList<Person> getOtherList();

    /**
     * Returns a list with just the current user
     */
    ObservableList<Person> getMeList();

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

    /**
     * For CLI Logout to call to raise event
     */
    void commandLogout();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}

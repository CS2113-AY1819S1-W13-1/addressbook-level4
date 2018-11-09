package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Person;

/**
 * Allows user to befriend a user from the others list, add a user to their friend list
 */
public class FriendCommand extends Command {
    public static final String COMMAND_WORD = "friend";
    public static final String COMMAND_WORD_ALIAS = "af";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the person selected with the index to the friends list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_FRIEND_SUCCESS = "Person added to the friend list!";

    private final Index targetIndex;

    public FriendCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> otherList = model.getOtherList(model.getUser());

        if (targetIndex.getZeroBased() >= otherList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = otherList.get(targetIndex.getZeroBased());
        Person editedPerson = personToEdit;
        Person editedUser = model.getUser();
        addFriendToPerson(editedPerson, model.getUser());
        addFriendToPerson(editedUser, personToEdit);
        editedPerson.getFriends().add(new Friend(model.getUser().getName()));
        editedUser.getFriends().add(new Friend(personToEdit.getName()));

        model.updatePerson(personToEdit, editedPerson);
        model.updatePerson(model.getUser(), editedUser);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_ADD_FRIEND_SUCCESS);
    }

    private void addFriendToPerson(Person editedPerson, Person person) {
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.User;
import seedu.address.security.SecurityAuthenticationException;


/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws SecurityAuthenticationException {
        requireNonNull(model);

        if (model.getUser() == null) {
            throw new SecurityAuthenticationException();
        }

        User user = model.getUser();

        user.getTimeTable().clear();

        model.updateTimeTable(user.getTimeTable());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

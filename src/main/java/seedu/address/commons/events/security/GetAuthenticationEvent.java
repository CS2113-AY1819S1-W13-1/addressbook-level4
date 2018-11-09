package seedu.address.commons.events.security;

import seedu.address.commons.events.BaseEvent;

/***
 * Indicates a successful login attempt
 */
public class GetAuthenticationEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Login Success";
    }
}

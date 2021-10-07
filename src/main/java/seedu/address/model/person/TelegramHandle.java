package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TelegramHandle {

    public static final String MESSAGE_CONSTRAINTS =
        "Telegram handles should only contain alphanumeric characters or underscores, with minimum 5 characters.";

    /*
     * At least 5 characters of a-z or A-Z or 0-9 or _.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}_]{5,}";

    /**
     * The telegram username of a person.
     */
    public final String handle;

    /**
     * The link to the telegram account of the handle.
     */
    public final String link;

    /**
     * Constructs a {@code TelegramHandle}.
     *
     * @param handle A valid Telegram handle.
     */
    public TelegramHandle(String handle) {
        requireNonNull(handle);
        checkArgument(isValidHandle(handle), MESSAGE_CONSTRAINTS);
        this.handle = handle;
        link = "https://t.me/" + handle;
    }

    /**
     * Returns true if a given string is a valid Telegram handle.
     */
    public static boolean isValidHandle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Gets the link to the person's telegram chat.
     */
    public String getTelegramLink() {
        return link;
    }

    @Override
    public String toString() {
        return handle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TelegramHandle // instanceof handles nulls
            && handle.equals(((TelegramHandle) other).handle)); // state check
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }
}

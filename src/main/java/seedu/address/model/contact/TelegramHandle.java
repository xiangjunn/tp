package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

import seedu.address.commons.util.StringUtil;

public class TelegramHandle {

    public static final String MESSAGE_CONSTRAINTS =
        "Telegram handles should only contain alphanumeric characters or underscores, with minimum 5 characters.";

    /*
     * At least 5 characters of a-z or A-Z or 0-9 or _.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}_]{5,}";

    /**
     * The telegram username of a contact.
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
     * Gets the link to the contact's telegram chat.
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

    /**
     * Checks if this {@code handle} contains any keywords in {@code strings}
     */
    public boolean containsString(List<String> strings) {
        requireNonNull(strings);
        assert handle != null : "the handle should not be null";
        return strings.stream().anyMatch(string ->
                StringUtil.containsWordIgnoreCase(handle, string));
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }
}

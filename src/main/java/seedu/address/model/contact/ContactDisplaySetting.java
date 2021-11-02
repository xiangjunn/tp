package seedu.address.model.contact;

import java.util.Objects;

/**
 * Contains all the display settings for contacts in the model manager.
 */
public class ContactDisplaySetting {
    public static final ContactDisplaySetting DEFAULT_SETTING = new ContactDisplaySetting(false);
    private final boolean willDisplayPhone;
    private final boolean willDisplayEmail;
    private final boolean willDisplayTelegramHandle;
    private final boolean willDisplayAddress;
    private final boolean willDisplayZoomLink;
    private final boolean willDisplayTags;
    private final boolean isViewingFull;

    /**
     * Creates a new {@code ContactDisplaySetting} with the given settings.
     * This is usually used in {@code CListCommand}, where the {@code isViewingFull} is always false.
     */
    public ContactDisplaySetting(
        boolean willDisplayPhone, boolean willDisplayEmail, boolean willDisplayTelegramHandle,
        boolean willDisplayAddress,
        boolean willDisplayZoomLink, boolean willDisplayTags) {
        this.willDisplayPhone = willDisplayPhone;
        this.willDisplayEmail = willDisplayEmail;
        this.willDisplayTelegramHandle = willDisplayTelegramHandle;
        this.willDisplayAddress = willDisplayAddress;
        this.willDisplayZoomLink = willDisplayZoomLink;
        this.willDisplayTags = willDisplayTags;
        this.isViewingFull = false;
    }

    /**
     * Creates a new {@code ContactDisplaySetting} with the given settings.
     * By default, all willDisplayXXX fields will be true.
     */
    public ContactDisplaySetting(boolean isViewingFull) {
        willDisplayPhone = true;
        willDisplayEmail = true;
        willDisplayTelegramHandle = true;
        willDisplayAddress = true;
        willDisplayZoomLink = true;
        willDisplayTags = true;
        this.isViewingFull = isViewingFull;
    }

    public boolean willDisplayAddress() {
        return willDisplayAddress;
    }

    public boolean willDisplayZoomLink() {
        return willDisplayZoomLink;
    }

    public boolean willDisplayTags() {
        return willDisplayTags;
    }

    public boolean isViewingFull() {
        return isViewingFull;
    }

    public boolean willDisplayPhone() {
        return willDisplayPhone;
    }

    public boolean willDisplayEmail() {
        return willDisplayEmail;
    }

    public boolean willDisplayTelegramHandle() {
        return willDisplayTelegramHandle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactDisplaySetting)) {
            return false;
        }
        ContactDisplaySetting that = (ContactDisplaySetting) o;
        return willDisplayPhone() == that.willDisplayPhone() && willDisplayEmail() == that.willDisplayEmail()
            && willDisplayTelegramHandle() == that.willDisplayTelegramHandle()
            && willDisplayAddress() == that.willDisplayAddress()
            && willDisplayZoomLink() == that.willDisplayZoomLink()
            && willDisplayTags() == that.willDisplayTags()
            && isViewingFull() == that.isViewingFull();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            willDisplayPhone(), willDisplayEmail(), willDisplayTelegramHandle(),
            willDisplayAddress(), willDisplayZoomLink(), willDisplayTags(), isViewingFull());
    }

    /** For debugging/logging purposes. */
    @Override
    public String toString() {
        return "ContactDisplaySetting{"
            + "willDisplayPhone=" + willDisplayPhone
            + ", willDisplayEmail=" + willDisplayEmail
            + ", willDisplayTelegramHandle=" + willDisplayTelegramHandle
            + ", willDisplayAddress=" + willDisplayAddress
            + ", willDisplayZoomLink=" + willDisplayZoomLink
            + ", willDisplayTags=" + willDisplayTags
            + ", isViewingFull=" + isViewingFull
            + '}';
    }
}

package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TELEGRAM = new Prefix("th/");
    public static final Prefix PREFIX_ZOOM = new Prefix("z/");
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("dt/");
    public static final Prefix PREFIX_START_TIME = new Prefix("at/");
    public static final Prefix PREFIX_END_TIME = new Prefix("end/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
}

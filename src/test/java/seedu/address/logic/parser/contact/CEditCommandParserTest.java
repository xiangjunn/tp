package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_ZOOM_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_DELETEALL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_DELETEFRIEND;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_DELETEHUSBAND;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CEditCommand;
import seedu.address.logic.commands.contact.CEditCommand.EditContactDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class CEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CEditCommand.MESSAGE_USAGE);

    private CEditCommandParser parser = new CEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", CEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TELEGRAM_DESC,
            TelegramHandle.MESSAGE_CONSTRAINTS); // invalid telegram handle
        assertParseFailure(parser, "1" + INVALID_ZOOM_DESC, ZoomLink.MESSAGE_CONSTRAINTS); // invalid zoom link
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_TAG} alone will result in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        CEditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        CEditCommand.EditContactDescriptor descriptor =
                new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditContactDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        CEditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        CEditCommand.EditContactDescriptor descriptor =
                new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new CEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DELETE_TAG + "*";

        CEditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
            .withDeleteAllTags(true).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTagsThenAddTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DELETEALL + TAG_DESC_HUSBAND;
        String altUserInput = targetIndex.getOneBased() + TAG_DESC_HUSBAND + TAG_DESC_DELETEALL;

        CEditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
            .withDeleteAllTags(true).withTags(VALID_TAG_HUSBAND).build();
        CEditCommand expectedCommand = new CEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        assertParseSuccess(parser, altUserInput, expectedCommand);
    }

    @Test
    public void parse_deleteTags_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DELETEFRIEND;
        String altInput = targetIndex.getOneBased() + TAG_DESC_DELETEFRIEND + TAG_DESC_DELETEHUSBAND;

        CEditCommand.EditContactDescriptor descriptor1 = new EditContactDescriptorBuilder()
            .withTagsToDelete(VALID_TAG_FRIEND).build();
        CEditCommand expectedCommand1 = new CEditCommand(targetIndex, descriptor1);

        assertParseSuccess(parser, userInput, expectedCommand1);

        CEditCommand.EditContactDescriptor descriptor2 = new EditContactDescriptorBuilder()
            .withTagsToDelete(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        CEditCommand expectedCommand2 = new CEditCommand(targetIndex, descriptor2);

        assertParseSuccess(parser, altInput, expectedCommand2);
    }
}

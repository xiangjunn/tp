package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_EXAMS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void isAnyFieldEdited_noFieldEdited() {
        EditEventDescriptor emptyEditEventDescriptor = new EditEventDescriptor();
        assertFalse(emptyEditEventDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_TUTORIAL);
        assertTrue(DESC_TUTORIAL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TUTORIAL.equals(DESC_TUTORIAL));

        // null -> returns false
        assertFalse(DESC_TUTORIAL.equals(null));

        // different types -> returns false
        assertFalse(DESC_TUTORIAL.equals(5));

        // different values -> returns false
        assertFalse(DESC_TUTORIAL.equals(DESC_EXAM));

        // different name -> returns false
        EditEventDescriptor editedTutorial = new EditEventDescriptorBuilder(DESC_TUTORIAL).withName(VALID_NAME_EXAM)
                .build();
        assertFalse(DESC_TUTORIAL.equals(editedTutorial));

        // different start time -> returns false
        editedTutorial = new EditEventDescriptorBuilder(DESC_TUTORIAL).withStartDateTime(VALID_START_DATE_TIME_EXAM)
                .build();
        assertFalse(DESC_TUTORIAL.equals(editedTutorial));

        // different end time -> returns false
        editedTutorial = new EditEventDescriptorBuilder(DESC_TUTORIAL).withEndDateTime(VALID_END_DATE_TIME_EXAM)
                .build();
        assertFalse(DESC_TUTORIAL.equals(editedTutorial));

        // different address -> returns false
        editedTutorial = new EditEventDescriptorBuilder(DESC_TUTORIAL).withAddress(VALID_ADDRESS_EXAM).build();
        assertFalse(DESC_TUTORIAL.equals(editedTutorial));

        // different tags -> returns false
        editedTutorial = new EditEventDescriptorBuilder(DESC_TUTORIAL).withTags(VALID_TAG_EXAMS).build();
        assertFalse(DESC_TUTORIAL.equals(editedTutorial));
    }
}

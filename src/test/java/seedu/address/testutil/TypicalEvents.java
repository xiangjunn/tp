package seedu.address.testutil;

import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ZOOM_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ZOOM_TUTORIAL;
import static seedu.address.testutil.TypicalContacts.ALICE_UUID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.event.Event;

public class TypicalEvents {

    public static final UUID CS2103_MIDTERM_MARKED_UUID = UUID.fromString("17e996a5-1f13-45fe-b9d2-f9517dff067f");
    public static final Event CS2103_MIDTERM_MARKED = new EventBuilder().withName("CS2103 Midterms")
        .withAddress("Zoom").withZoomLink("nus-sg.edu/123%a").withDescription("I'm very unprepared")
        .withStartDateAndTime("20-10-2021 09:00").withEndDateAndTime("20-10-2021 11:00")
        .withTags("exams").withMarked(true).build();

    public static final UUID CS2100_CONSULTATION_UUID = UUID.fromString("123e4567-e89b-12d3-a456-556642440002");
    public static final Event CS2100_CONSULTATION = new EventBuilder().withName("CS2100 Consultation")
        .withAddress("02-11 COM2").withDescription("Topics: Assembly Language")
        .withStartDateAndTime("17-10-2021 15:00").withEndDateAndTime("17-10-2021 16:00").withTags("School")
        .withUuid(CS2100_CONSULTATION_UUID).withLinkedContacts(ALICE_UUID)
        .withMarked(false).build();

    public static final Event CS2101_MEETING = new EventBuilder().withName("CS2101 Meeting")
        .withAddress("Zoom").withZoomLink("nus-sg.edu/pwdffha%a").withDescription("Need to prepare for OP2")
        .withStartDateAndTime("15-10-2021 21:00").withEndDateAndTime("15-10-2021 22:00")
        .withTags("meeting").withMarked(false).withRandomUuid().build();

    public static final Event FOOTBALL_PRACTICE = new EventBuilder().withName("Football Practice")
        .withAddress("USC").withStartDateAndTime("30-10-2021 09:00").withEndDateAndTime("30-10-2021 11:00")
        .withMarked(false).withRandomUuid().withTags().build();

    public static final Event TEAM_MEETING = new EventBuilder().withName("Team Meeting")
        .withAddress("Zoom").withZoomLink("nus-edu.sg/123link")
        .withStartDateAndTime("20-10-2021 19:00").withEndDateAndTime("20-10-2021 20:00")
        .withTags("meeting").withMarked(false).withRandomUuid().build();

    public static final Event BIRTHDAY_PARTY = new EventBuilder().withName("Birthday Party")
        .withAddress("#10-07 Baker Street").withDescription("Dress code: Pink!")
        .withStartDateAndTime("23-10-2021 20:00").withEndDateAndTime("24-10-2021 01:00")
        .withTags("friends").withMarked(false).withRandomUuid().build();

    // Manually added
    public static final Event INTERVIEW = new EventBuilder().withName("Interview")
        .withAddress("Zoom").withZoomLink("123kadsf-link-23.zoom.nus.edu")
        .withStartDateAndTime("28-10-2021 10:00").withEndDateAndTime("28-10-2021 11:00").withTags("interview")
        .build();
    public static final Event TUTORIAL = new EventBuilder().withName(VALID_NAME_TUTORIAL)
        .withStartDateAndTime(VALID_START_DATE_TIME_TUTORIAL).withEndDateAndTime(VALID_END_DATE_TIME_TUTORIAL)
        .withDescription(VALID_DESCRIPTION_TUTORIAL).withAddress(VALID_ADDRESS_TUTORIAL)
        .withZoomLink(VALID_ZOOM_TUTORIAL).withTags(VALID_TAG_COOL).build();
    public static final Event EXAM = new EventBuilder().withName(VALID_NAME_EXAM)
        .withStartDateAndTime(VALID_START_DATE_TIME_EXAM).withEndDateAndTime(VALID_END_DATE_TIME_EXAM)
        .withDescription(VALID_DESCRIPTION_EXAM).withAddress(VALID_ADDRESS_EXAM)
        .withZoomLink(VALID_ZOOM_EXAM).withTags(VALID_TAG_EXAMS, VALID_TAG_COOL).build();

    static {
        Event.addToMap(CS2103_MIDTERM_MARKED);
        Event.addToMap(CS2100_CONSULTATION);
        Event.addToMap(CS2101_MEETING);
        Event.addToMap(FOOTBALL_PRACTICE);
        Event.addToMap(TEAM_MEETING);
        Event.addToMap(BIRTHDAY_PARTY);
        Event.addToMap(TUTORIAL);
        Event.addToMap(EXAM);
    }

    private TypicalEvents() {
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(
            Arrays.asList(CS2103_MIDTERM_MARKED, CS2100_CONSULTATION, CS2101_MEETING, FOOTBALL_PRACTICE,
                TEAM_MEETING, BIRTHDAY_PARTY));
    }

}

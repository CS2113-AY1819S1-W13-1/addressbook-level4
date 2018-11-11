package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalTimeSlots;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = "  ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TIMESLOT = "something";
    private static final String INVALID_DAY = "someday";
    private static final String INVALID_TIME = "24:00";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DAY_FULL = "monday";
    private static final String VALID_DAY_SHORT = "mon";
    private static final String VALID_TIME_0800HRS_FULL = "08:00";
    private static final String VALID_TIME_0800HRS_SHORT_1 = "8:00";
    private static final String VALID_TIME_0800HRS_SHORT_2 = "0800";
    private static final String VALID_TIME_0800HRS_SHORT_3 = "800";
    private static final String VALID_TIME_0800HRS_SHORT_4 = "8";
    private static final String VALID_TIME_1000HRS_FULL = "10:00";
    private static final String VALID_TIME_1000HRS_SHORT = "10";
    private static final String VALID_TIME_0830HRS_FULL = "08:30";
    private static final String VALID_TIME_0830HRS_SHORT_1 = "8:30";
    private static final String VALID_TIME_0830HRS_SHORT_2 = "830";
    private static final String VALID_TIME_1030HRS_FULL = "10:30";
    private static final String VALID_TIME_1030HRS_SHORT = "1030";

    private static final String WHITESPACE = " \t\r\n";
    private static final String DASH = "-";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTimeSlot_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTimeSlot(null);
    }

    @Test
    public void parseTimeSlot_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(INVALID_TIMESLOT);
    }

    @Test
    public void parseTimeSlot_invalidDay_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(INVALID_DAY + WHITESPACE + VALID_TIME_0800HRS_FULL + DASH + VALID_TIME_1000HRS_FULL);
    }

    @Test
    public void parseTimeSlot_invalidStartTime_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(VALID_DAY_FULL + WHITESPACE + INVALID_TIME + DASH + VALID_TIME_1000HRS_FULL);
    }

    @Test
    public void parseTimeSlot_invalidEndTime_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(VALID_DAY_FULL + WHITESPACE + VALID_TIME_0800HRS_FULL + DASH + INVALID_TIME);
    }

    @Test
    public void parseTimeSlot_invalidRange_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_1000HRS_FULL + DASH + VALID_TIME_0800HRS_FULL);
    }

    @Test
    public void parseTimeSlot_invalidRangeSameTime_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_1000HRS_FULL + DASH + VALID_TIME_1000HRS_FULL);
    }

    @Test
    public void parseTimeSlot_validValuesZeroMinute_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_8_TO_10;
        TimeSlot actual = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_FULL + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTimeSlot_validValuesNonZeroMinute_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_830_TO_1030;
        TimeSlot actual = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0830HRS_FULL + DASH + VALID_TIME_1030HRS_FULL);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTimeSlot_validValuesShortDay_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_8_TO_10;
        TimeSlot actual = ParserUtil.parseTimeSlot(VALID_DAY_SHORT
                + WHITESPACE + VALID_TIME_0800HRS_FULL + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTimeSlot_validValuesShortTimeZeroMinute_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_8_TO_10;

        TimeSlot actual1 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_SHORT_1 + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual1);

        TimeSlot actual2 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_SHORT_2 + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual2);

        TimeSlot actual3 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_SHORT_3 + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual3);

        TimeSlot actual4 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_SHORT_4 + DASH + VALID_TIME_1000HRS_FULL);
        assertEquals(expected, actual4);

        TimeSlot actual5 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_FULL + DASH + VALID_TIME_1000HRS_SHORT);
        assertEquals(expected, actual5);
    }

    @Test
    public void parseTimeSlot_validValuesShortTimeNoNZeroMinute_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_830_TO_1030;

        TimeSlot actual1 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0830HRS_SHORT_1 + DASH + VALID_TIME_1030HRS_FULL);
        assertEquals(expected, actual1);

        TimeSlot actual2 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0830HRS_SHORT_2 + DASH + VALID_TIME_1030HRS_FULL);
        assertEquals(expected, actual2);

        TimeSlot actual3 = ParserUtil.parseTimeSlot(VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0830HRS_FULL + DASH + VALID_TIME_1030HRS_SHORT);
        assertEquals(expected, actual3);
    }

    @Test
    public void parseTimeSlot_validValuesWithWhitespace_returnsTimeSlot() throws Exception {
        TimeSlot expected = TypicalTimeSlots.MON_8_TO_10;
        TimeSlot actual = ParserUtil.parseTimeSlot(WHITESPACE + VALID_DAY_FULL
                + WHITESPACE + VALID_TIME_0800HRS_FULL + WHITESPACE + DASH
                + WHITESPACE + VALID_TIME_1000HRS_FULL + WHITESPACE);
        assertEquals(expected, actual);
    }
}

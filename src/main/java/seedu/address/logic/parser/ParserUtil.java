package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final HashMap<String, DayOfWeek> DAY_OF_WEEK_MAP = new HashMap<>();

    static {
        DAY_OF_WEEK_MAP.put("MON", DayOfWeek.MONDAY);
        DAY_OF_WEEK_MAP.put("TUE", DayOfWeek.TUESDAY);
        DAY_OF_WEEK_MAP.put("WED", DayOfWeek.WEDNESDAY);
        DAY_OF_WEEK_MAP.put("THU", DayOfWeek.THURSDAY);
        DAY_OF_WEEK_MAP.put("FRI", DayOfWeek.FRIDAY);
        DAY_OF_WEEK_MAP.put("SAT", DayOfWeek.SATURDAY);
        DAY_OF_WEEK_MAP.put("SUN", DayOfWeek.SUNDAY);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String password} into a {@code password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static String parsePassword(String pw) throws ParseException {
        requireNonNull(pw);
        String trimmedName = pw.trim();
        if (pw.length() < 8) {
            throw new ParseException("Password has to be of at least 8 characters");
        }
        return new String(pw);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     *
     * Parses a {@code String timeslot} into a {@code timeslot}
     *
     */
    public static TimeSlot parseTimeSlot (String timeslot) throws ParseException {
        requireNonNull(timeslot);
        String trimmedTimeSlot = timeslot.trim();

        if (!TimeSlot.isValidTimeSlot(trimmedTimeSlot)) {
            throw new ParseException(TimeSlot.MESSAGE_GENERAL_CONSTRAINTS);
        }

        String dayString = trimmedTimeSlot.split("\\s+", 2)[0];
        DayOfWeek day;

        try {
            day = parseDay(dayString);
        } catch (IllegalArgumentException e) {
            throw new ParseException((TimeSlot.MESSAGE_CANNOT_PARSE_DAY));
        }

        String timeRangeString = trimmedTimeSlot.split("\\s+", 2)[1];
        String startString = timeRangeString.split("-", 2)[0].trim();
        String endString = timeRangeString.split("-", 2)[1].trim();

        LocalTime startTime;
        LocalTime endTime;

        try {
            startTime = parseTime(startString);
            endTime = parseTime(endString);
        } catch (DateTimeParseException e) {
            throw new ParseException((TimeSlot.MESSAGE_CANNOT_PARSE_TIME));
        }

        try {
            TimeSlot toReturn = new TimeSlot(day, startTime, endTime);
            return toReturn;
        } catch (IllegalArgumentException e) {
            throw new ParseException(TimeSlot.MESSAGE_INVALID_TIME_SLOT);
        }
    }

    /**
     * Parses a string and returns a DayOfWeek
     * @param dayString String to be parsed
     * @return DayOfWeek of String
     * @throws IllegalArgumentException if string cannot be parsed
     */
    public static DayOfWeek parseDay(String dayString) throws IllegalArgumentException {
        if (DAY_OF_WEEK_MAP.containsKey(dayString.toUpperCase())) {
            return DAY_OF_WEEK_MAP.get(dayString.toUpperCase());
        } else {
            try {
                return DayOfWeek.valueOf(dayString.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
    }

    /**
     * Parses {@code timeString} according to various defined formats into a {@code LocalTime} object
     * @param timeString {@code String} to be parsed
     * @return {@code LocalTime} object representing {@code timeString}
     * @throws DateTimeParseException if {@code timeString} cannot be parsed
     */
    public static LocalTime parseTime(String timeString) throws DateTimeParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

        if (timeString.contains(":") && timeString.length() == 4) {
            format = DateTimeFormatter.ofPattern("H:mm");
        } else if (timeString.length() == 4) {
            format = DateTimeFormatter.ofPattern("HHmm");
        } else if (timeString.length() == 3) {
            format = DateTimeFormatter.ofPattern("Hmm");
        } else if (timeString.length() == 2) {
            format = DateTimeFormatter.ofPattern("HH");
        } else if (timeString.length() == 1) {
            format = DateTimeFormatter.ofPattern("H");
        }

        try {
            return LocalTime.parse(timeString, format);
        } catch (DateTimeParseException e) {
            throw e;
        }
    }

    /**
     * Parses a {@code String filePath} into a {@code Path}
     */
    public static Path parseImportFileLocation (String fileLocation) throws ParseException {
        requireNonNull(fileLocation);
        String trimmedFileLocation = fileLocation.trim();

        //default import path.
        if (trimmedFileLocation.length() == 0) {
            Path path;
            path = Paths.get("import_export" , "import.ics");
            return path;
        }

        //ensure the filePath ends in .ics
        return parseImportExportFileLocation(trimmedFileLocation);
    }

    /**
     * Parses a {@code String filePath} into a {@code Path}
     */
    public static Path parseExportFileLocation (String fileLocation) throws ParseException {
        requireNonNull(fileLocation);
        String trimmedFileLocation = fileLocation.trim();

        //default export path.
        if (trimmedFileLocation.length() == 0) {
            Path path;
            path = Paths.get("import_export" , "export.ics");
            return path;
        }
        return parseImportExportFileLocation(trimmedFileLocation);
    }

    /**
     * Parses a (non-empty) {@code String fileLocation} into a {@code Path}
     */
    private static Path parseImportExportFileLocation(String trimmedFileLocation) throws ParseException {
        //ensure the filePath ends in .ics
        String pattern = "^.+\\.(?:(?:[iI][cC][sS]))$"; //ending in .ics
        if (!Pattern.matches(pattern, trimmedFileLocation)) {
            throw new ParseException("The path must end in .ics");
        }

        //create the Path
        Path path;
        try {
            path = Paths.get(trimmedFileLocation);
        } catch (InvalidPathException e) {
            throw new ParseException("The path you entered is invalid.");
        }
        return path;
    }
}

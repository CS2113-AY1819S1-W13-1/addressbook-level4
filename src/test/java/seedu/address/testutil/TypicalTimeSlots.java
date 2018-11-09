package seedu.address.testutil;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * A utility class containing a list of {@code TimeSlot} objects to be used in tests.
 */
public class TypicalTimeSlots {
    // In typical timetable
    public static final TimeSlot MON_8_TO_10 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"));

    public static final TimeSlot MON_10_TO_12 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    public static final TimeSlot TUE_10_TO_12 = new TimeSlot(DayOfWeek.TUESDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    // Not in typical timetable, will overlap
    public static final TimeSlot MON_9_TO_11 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("09:00"),
            LocalTime.parse("11:00"));

    // Not in typical timetable, will not overlap
    public static final TimeSlot TUE_12_TO_14 = new TimeSlot(DayOfWeek.TUESDAY,
            LocalTime.parse("12:00"),
            LocalTime.parse("14:00"));

    // Not in typical timetable, will not overlap
    public static final TimeSlot WED_10_TO_12 = new TimeSlot(DayOfWeek.WEDNESDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    // Not in typical timetable, for merge testing
    public static final TimeSlot MON_8_TO_12 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("08:00"),
            LocalTime.parse("12:00"));

    // Not in typical timetable, for merge testing
    public static final TimeSlot MON_8_TO_11 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("08:00"),
            LocalTime.parse("11:00"));

    // Not in typical timetable, for non-zero minute testing
    public static final TimeSlot MON_830_TO_1030 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("08:30"),
            LocalTime.parse("10:30"));

    public static TimeTable getTypicalTimeTable() {
        TimeTable t = new TimeTable();
        for (TimeSlot timeSlot : getTypicalTimeSlots()) {
            t.addTimeSlot(timeSlot);
        }
        return t;
    }

    public static Collection<TimeSlot> getTypicalTimeSlots() {
        return new HashSet<>(Arrays.asList(MON_8_TO_10, MON_10_TO_12, TUE_10_TO_12));
    }
}

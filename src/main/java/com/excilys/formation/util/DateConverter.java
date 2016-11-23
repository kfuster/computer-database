package com.excilys.formation.util;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Class containing methods to convert dates.
 * @author kfuster
 *
 */
public class DateConverter {
    /**
     * Converts a Timestamp to a LocalDate.
     * @param pTimestamp the timestamp to convert
     * @return a LocalDate or null
     */
    public static LocalDate fromTimestampToLocalDate(Timestamp pTimestamp) {
        if (pTimestamp != null) {
            return pTimestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
    /**
     * Converts a LocalDate to a Timestamp.
     * @param pLocalDate the LocalDate to convert
     * @return a Timestamp or null
     */
    public static Timestamp fromLocalDateToTimestamp(LocalDate pLocalDate) {
        if (pLocalDate != null) {
            return Timestamp.valueOf(pLocalDate.atStartOfDay());
        }
        return null;
    }
}
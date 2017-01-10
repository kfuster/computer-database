package com.excilys.formation.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Utility class that allows to converts a LocalDate to a Timestamp and vice versa.
 * @author kfuster
 */
@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

    /**
     * Converts a LocalDate to a Timestamp.
     * If the given localDate is null it will return null.
     * @param localDate The localDate that we want to convert.
     * @return Timestamp A timestamp corresponding to the localDate given in parameter.
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate localDate) {
        if (localDate != null) {
            return Timestamp.valueOf(localDate.atStartOfDay());
        }
        return null;
    }

    /**
     * Converts a Timestamp to a LocalDate.
     * If the given timestamp is null it will return null.
     * @param timestamp The timestamp that we want to convert.
     * @return LocalDate A localDate corresponding to the timestamp given in parameter.
     */
    @Override
    public LocalDate convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
}

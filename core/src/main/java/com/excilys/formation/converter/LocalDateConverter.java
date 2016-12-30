package com.excilys.formation.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

    /**
     * Converts a LocalDate to a Timestamp.
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate pLocalDate) {
        if (pLocalDate != null) {
            return Timestamp.valueOf(pLocalDate.atStartOfDay());
        }
        return null;
    }

    /**
     * Converts a Timestamp to a LocalDate.
     */
    @Override
    public LocalDate convertToEntityAttribute(Timestamp pTimestamp) {
        if (pTimestamp != null) {
            return pTimestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
}

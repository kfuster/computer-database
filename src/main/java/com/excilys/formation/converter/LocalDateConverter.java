package com.excilys.formation.converter;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

    /**
     * Convert Color object to a String with format red|green|blue|alpha
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate pLocalDate) {
        if (pLocalDate != null) {
            return Timestamp.valueOf(pLocalDate.atStartOfDay());
        }
        return null;
    }

    /**
     * Convert a String with format red|green|blue|alpha to a Color object
     */
    @Override
    public LocalDate convertToEntityAttribute(Timestamp pTimestamp) {
        if (pTimestamp != null) {
            return pTimestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
}

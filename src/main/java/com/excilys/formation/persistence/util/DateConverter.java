package com.excilys.formation.persistence.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DateConverter {

    public static LocalDate fromTimestampToLocalDate(Timestamp pTimestamp) {
        if(pTimestamp != null) {
            return pTimestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
    
    public static Timestamp fromlocalDateToTimestamp(LocalDate pLocalDate) {
        if(pLocalDate != null) {
            return Timestamp.valueOf(pLocalDate.atStartOfDay());
        }
        return null;
    }
}

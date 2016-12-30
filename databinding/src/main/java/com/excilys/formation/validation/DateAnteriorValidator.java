package com.excilys.formation.validation;

import com.excilys.formation.dto.ComputerDto;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAnteriorValidator implements ConstraintValidator<DateAnterior, ComputerDto> {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void initialize(DateAnterior constraintAnnotation) {
    }

    @Override
    public boolean isValid(ComputerDto computerDto, ConstraintValidatorContext context) {
        String format = messageSource.getMessage("util.date.format", null, LocaleContextHolder.getLocale());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String introducedDate = computerDto.getIntroduced();
        String discontinuedDate = computerDto.getDiscontinued();
        String errorDateMessage = messageSource.getMessage("form.error.date", null, LocaleContextHolder.getLocale());
        String errorDateBeforeMessage = messageSource.getMessage("form.error.dateBefore", null, LocaleContextHolder.getLocale());
        boolean isValid = true;
        context.disableDefaultConstraintViolation();
        if (introducedDate != null && discontinuedDate != null && !"".equals(introducedDate)
                && !"".equals(discontinuedDate)) {
            if (!GenericValidator.isDate(introducedDate, format, true)) {
                context.buildConstraintViolationWithTemplate(errorDateMessage)
                        .addPropertyNode("introduced").addConstraintViolation();
                isValid = false;
            }
            if (!GenericValidator.isDate(discontinuedDate, format, true)) {
                context.buildConstraintViolationWithTemplate(errorDateMessage)
                        .addPropertyNode("discontinued").addConstraintViolation();
                isValid = false;
            }
            if (isValid) {
                LocalDate dateIntro = LocalDate.parse(introducedDate, formatter);
                LocalDate dateDisco = LocalDate.parse(discontinuedDate, formatter);
                if (!dateIntro.isBefore(dateDisco)) {
                    context.buildConstraintViolationWithTemplate(errorDateBeforeMessage)
                            .addPropertyNode("discontinued").addConstraintViolation();
                    isValid = false;
                }
            }
        }
        return isValid;
    }
}
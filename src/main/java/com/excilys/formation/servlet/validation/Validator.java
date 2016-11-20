package com.excilys.formation.servlet.validation;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.excilys.formation.dto.ComputerDto;

public class Validator {
    public static Map<String, String> validateComputerDto(ComputerDto computerDto, Map<String, String> pErrors) {
        String name = computerDto.name;
        if (!isNameValid(name)) {
            pErrors.put("name", "Le nom n'est pas valide");
        }
        String introducedError = isDateValid(computerDto.introduced, null);
        if (introducedError != null) {
            pErrors.put("introduced", introducedError);
        }
        String discontinuedError = isDateValid(computerDto.discontinued, null);
        if (discontinuedError != null) {
            pErrors.put("discontinued", discontinuedError);
        }
        return pErrors;
    }
    private static boolean isNameValid(String pName) {
        Pattern pattern = Pattern.compile("\\w{3,}[ ]{0,1}\\w*");
        Matcher matcher = pattern.matcher(pName);
        return matcher.matches();
    }
    private static String isDateValid(String pDate, String pBeforeDate) {
        if (pDate == null) {
            return null;
        } else {
            Pattern pattern = Pattern.compile("^(\\d{4})-([1-9]|0[1-9]|1[0-2])-(\\d{2})$");
            Matcher matcher = pattern.matcher(pDate);
            if (matcher.matches()) {
                matcher.reset();
                if (matcher.find()) {
                    int year = Integer.parseInt(matcher.group(1));
                    int month = Integer.parseInt(matcher.group(2));
                    int day = Integer.parseInt(matcher.group(3));
                    if (day > 31 || month > 12) {
                        return "La date n'est pas valide";
                    } else if (day == 31 && month == 4 || month == 6 || month == 9 || month == 11) {
                        // 4,6,9,11 are 30 days months
                        return "La date n'est pas valide";
                    } else if (month == 2) {
                        // leap year
                        if (year % 4 == 0) {
                            if (day == 30 || day == 31) {
                                return "La date n'est pas valide";
                            } else {
                                return null;
                            }
                        } else {
                            if (day == 29 || day == 30 || day == 31) {
                                return "La date n'est pas valide";
                            } else {
                                return null;
                            }
                        }
                    } else if (LocalDate.parse(pDate).isBefore(LocalDate.parse("1970-01-01"))) {
                        return "La date doit être après 1970-01-01";
                    } else {
                        if (pBeforeDate != null) {
                            if (LocalDate.parse(pDate).isBefore(LocalDate.parse(pBeforeDate))) {
                                return "La date doit être situé après la date d'introduction";
                            }
                        }
                        return null;
                    }
                } else {
                    return "Problème lors de la vérification de la date";
                }
            } else {
                return "La date est au mauvais format";
            }
        }
    }
}

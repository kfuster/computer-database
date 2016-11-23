package com.excilys.formation.servlet.validation;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.excilys.formation.dto.ComputerDto;

/**
 * Validation class for servlets.
 * Used to validate datas extracted from request.
 * @author kfuster
 *
 */
public class Validator {
    /**
     * Validates the datas of a ComputerDto and set the errors in a Map.
     * @param computerDto the ComputerDto containing the datas to validate
     * @param pErrors the Map of errors
     * @return pErrors with added errors if founds
     */
    public static Map<String, String> validateComputerDto(ComputerDto computerDto, Map<String, String> pErrors) {
        String name = computerDto.name;
        if (name == null || !isNameValid(name)) {
            pErrors.put("name", "Le nom n'est pas valide");
        }
        String introducedError = isDateValid(computerDto.introduced, null);
        if (introducedError != null) {
            pErrors.put("introduced", introducedError);
        }
        String discontinuedError = isDateValid(computerDto.discontinued, computerDto.introduced);
        if (discontinuedError != null) {
            pErrors.put("discontinued", discontinuedError);
        }
        return pErrors;
    }
    /**
     * Checks if a name is valid (contains at least 3 characters.
     * @param pName the name to check
     * @return a boolean
     */
    private static boolean isNameValid(String pName) {
        Pattern pattern = Pattern.compile("\\w{3,}[ ]{0,1}\\w*");
        Matcher matcher = pattern.matcher(pName);
        return matcher.matches();
    }
    /**
     * Checks if a date if valid :
     * <ul>
     *  <li>- pDate is in the good format (yyyy-mm-dd)</li>
     *  <li>- pDate's day <= 31 and month <= 12</li>
     *  <li>- pDate's day isn't 31 on a 30 days month</li>
     *  <li>- pDate's day isn't >= 30 on the february of a leap year or isn't >= 29 on a february</li>
     *  <li>- pDate's date isn't before 01 January 1970</li>
     *  <li>- If pBeforeDate isn't null, pDate must be after</li>
     * </ul>
     * @param pDate the date to check in string format
     * @param pBeforeDate the date used to check pDate is after, or null
     * @return a String indicating the error
     */
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
                    } else if (day == 31 && ( month == 4 || month == 6 || month == 9 || month == 11)) {
                        // 4,6,9,11 are 30 days months
                        return "La date n'est pas valide";
                    } else if (month == 2 && ((year % 4 == 0 && day >= 30) || day >= 29)) {
                        // leap year
                        return "La date n'est pas valide";
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

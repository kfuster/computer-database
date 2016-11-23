package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.util.DateConverter;

/**
 * Mapper class for JDBC ResultSets.
 * @author kfuster
 *
 */
public class JdbcMapper {
    /**
     * Loop on a ResultSet to map it to a computer list.
     * @param pResultSet The ResultSet to map
     * @return a computer List
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    public static List<Computer> mapResultsToComputerList(ResultSet pResultSet) throws SQLException {
        List<Computer> computerList = null;
        if (pResultSet != null) {
            computerList = new ArrayList<Computer>();
            while (pResultSet.next()) {
                Computer computer = extractComputer(pResultSet);
                computerList.add(computer);
            }
        }
        return computerList;
    }
    /**
     * Loop on a ResultSet to map it to a company list.
     * @param pResultSet The ResultSet to map
     * @return a company List
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    public static List<Company> mapResultsToCompanyList(ResultSet pResultSet) throws SQLException {
        List<Company> companyList = null;
        if (pResultSet != null) {
            companyList = new ArrayList<Company>();
            while (pResultSet.next()) {
                Company company = extractCompany(pResultSet);
                companyList.add(company);
            }
        }
        return companyList;
    }
    /**
     * Map a ResultSet to a Computer.
     * @param pResultSet the ResultSet to map
     * @return a Computer
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    public static Computer mapResultToComputer(ResultSet pResultSet) throws SQLException {
        Computer computer = null;
        if (pResultSet != null) {
            if (pResultSet.next()) {
                computer = extractComputer(pResultSet);
            }
        }
        return computer;
    }
    /**
     * Map a ResultSet to a Company.
     * @param pResultSet the ResultSet to map
     * @return a Company
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    public static Company mapResultToCompany(ResultSet pResultSet) throws SQLException {
        Company company = null;
        if (pResultSet != null) {
            if (pResultSet.next()) {
                company = extractCompany(pResultSet);
            }
        }
        return company;
    }
    /**
     * Extract a Computer from a ResultSet.
     * @param pResultSet the ResultSet on a specific row
     * @return a Computer
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    private static Computer extractComputer(ResultSet pResultSet) throws SQLException   {
        // Get the company from the row
        Company company = new Company.CompanyBuilder(pResultSet.getString("companyName"))
                .setId(pResultSet.getInt("companyId")).build();
        // Get the computer from the row
        Computer computer = new Computer.ComputerBuilder(pResultSet.getString("name")).setCompany(company)
                .setId(pResultSet.getInt("id"))
                .setDateDisc(DateConverter.fromTimestampToLocalDate(pResultSet.getTimestamp("discontinued")))
                .setDateIntro(DateConverter.fromTimestampToLocalDate(pResultSet.getTimestamp("introduced"))).build();
        return computer;
    }
    /**
     * Extract a Company from a ResultSet.
     * @param pResultSet the ResultSet on a specific row
     * @return a Company
     * @throws SQLException in case of problems with operating on the ResultSet
     */
    private static Company extractCompany(ResultSet pResultSet) throws SQLException {
        // Get the company from the row
        Company company = new Company.CompanyBuilder(pResultSet.getString("name")).setId(pResultSet.getInt("id"))
                .build();
        return company;
    }
}
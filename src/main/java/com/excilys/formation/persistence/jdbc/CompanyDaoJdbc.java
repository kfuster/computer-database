package com.excilys.formation.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.HikariConnectionProvider;
import com.excilys.formation.persistence.mapper.JdbcMapper;
import ch.qos.logback.classic.Logger;

/**
 * DAO class for companies.
 * @author kfuster
 *
 */
public class CompanyDaoJdbc implements CompanyDao {
    final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(CompanyDaoJdbc.class);
    private HikariConnectionProvider connectionProvider;
    private static CompanyDaoJdbc companyDaoImpl = null;
    private static final String SELECT_ALL = "SELECT * FROM company ORDER BY company.name";
    private static final String SELECT_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company ";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";
    /**
     * CompanyDaoJdbc constructor.
     * Initialize the connectionProvider.
     */
    private CompanyDaoJdbc() {
        connectionProvider = HikariConnectionProvider.getInstance();
    }
    /**
     * Getter for the instance of CompanyDaoJdbc.
     * If the instance is null, initializes it.
     * @return the instance of CompanyDaoJdbc
     */
    public static CompanyDaoJdbc getInstance() {
        if (companyDaoImpl == null) {
            companyDaoImpl = new CompanyDaoJdbc();
        }
        return companyDaoImpl;
    }
    @Override
    public Company getById(int pId) throws PersistenceException {
        Company company = null;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = JdbcMapper.mapResultToCompany(resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException("Problème lors de la récupération de la compagnie");
        }
        return company;
    }
    @Override
    public Page<Company> getPage(Page<Company> pPage, String pFilter) throws PersistenceException {
        List<Company> allCompanies = new ArrayList<>();
        String queryComputers = SELECT_PAGE;
        if (pFilter != null && !pFilter.isEmpty()) {
            queryComputers += pFilter;
        }
        queryComputers += " LIMIT ? OFFSET ?";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputers);
            preparedStatement.setInt(1, pPage.elemByPage);
            preparedStatement.setInt(2, (pPage.page - 1) * pPage.elemByPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            allCompanies = JdbcMapper.mapResultsToCompanyList(resultSet);
            pPage.elems = (allCompanies);
            pPage.setTotalElement(count(pFilter));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies");
        }
        return pPage;
    }
    @Override
    public List<Company> getAll() throws PersistenceException {
        List<Company> allCompanies = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            allCompanies = JdbcMapper.mapResultsToCompanyList(resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies");
        }
        return allCompanies;
    }
    /**
     * Count the total number of companies.
     * @param pFilter an optional filter string
     * @return the number of companies in the DB
     */
    private int count(String pFilter) {
        String query = COUNT_ALL;
        if (pFilter != null && !pFilter.isEmpty()) {
            query += pFilter;
        }
        int total = 0;
        try (Connection connection = connectionProvider.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return total;
    }
}
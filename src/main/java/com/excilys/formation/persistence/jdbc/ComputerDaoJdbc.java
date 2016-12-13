package com.excilys.formation.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.JdbcMapper;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.util.DateConverter;
import com.zaxxer.hikari.HikariDataSource;
import ch.qos.logback.classic.Logger;

/**
 * DAO class for computers.
 * @author kfuster
 *
 */
@Component
public class ComputerDaoJdbc implements ComputerDao {
    @Autowired
    private HikariDataSource dataSource;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerDaoJdbc.class);
    private static final String INSERT_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";
    private static final String DELETE_COMPUTER_LIST = "DELETE FROM computer WHERE id IN";
    private static final String DELETE_COMPUTER_BY_COMPANY = "DELETE FROM computer WHERE company_id=?";
    private static final String SELECT_JOIN = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer LEFT JOIN company ON computer.company_id=company.id ";

    public ComputerDaoJdbc(){}
    
    public void setDataSource(HikariDataSource pDataSource) {
        dataSource = pDataSource;
    }
    
    @Override
    public Computer create(Computer pComputer) throws PersistenceException {
        String queryComputer = INSERT_COMPUTER;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputer,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setTimestamp(2, DateConverter.fromLocalDateToTimestamp(pComputer.getIntroduced()));
            preparedStatement.setTimestamp(3, DateConverter.fromLocalDateToTimestamp(pComputer.getDiscontinued()));
            preparedStatement.setLong(4, pComputer.getCompany().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                long computerId = resultSet.getLong(1);
                pComputer.setId(computerId);
                resultSet.close();
                return pComputer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : create : ", e);
            throw new PersistenceException("Problème lors de la création de l'ordinateur");
        }
    }

    @Override
    public void update(Computer pComputer) throws PersistenceException {
        String queryComputer = UPDATE_COMPUTER;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputer)) {
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setTimestamp(2, DateConverter.fromLocalDateToTimestamp(pComputer.getIntroduced()));
            preparedStatement.setTimestamp(3, DateConverter.fromLocalDateToTimestamp(pComputer.getDiscontinued()));
            preparedStatement.setLong(4, pComputer.getCompany().getId());
            preparedStatement.setLong(5, pComputer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : update : ", e);
            throw new PersistenceException("Problème lors de l'update de l'ordinateur");
        }
    }

    @Override
    public void delete(long pID) throws PersistenceException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPUTER)) {
            preparedStatement.setLong(1, pID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : delete : ", e);
            throw new PersistenceException("Problème lors de la suppression de l'ordinateur");
        }
    }

    @Override
    public void deleteList(String idList) throws PersistenceException {
        String query = DELETE_COMPUTER_LIST + " (" + idList + ");";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : delete : ", e);
            throw new PersistenceException("Problème lors de la suppression des ordinateurs");
        }
    }

    @Override
    public void deleteByCompany(Connection pConnection, long pID) throws PersistenceException {
        try (PreparedStatement preparedStatement = pConnection.prepareStatement(DELETE_COMPUTER_BY_COMPANY)) {
            preparedStatement.setLong(1, pID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : deleteByCompany : ", e);
            throw new PersistenceException("Problème lors de la suppression des ordinateur");
        }
    }

    @Override
    public Computer getById(long pId) throws PersistenceException {
        String queryComputer = SELECT_JOIN + " WHERE computer.id=?";
        Computer computer = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputer)) {
            preparedStatement.setLong(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : getById : ", e);
            throw new PersistenceException("Problème lors de la récupération de l'ordinateur");
        }
        return computer;
    }

    @Override
    public Computer getByName(String pName) throws PersistenceException {
        String queryComputer = SELECT_JOIN + " WHERE computer.name=?";
        Computer computer = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputer)) {
            preparedStatement.setString(1, pName);
            ResultSet resultSet = preparedStatement.executeQuery();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : getByName : ", e);
            throw new PersistenceException("Problème lors de la récupération de l'ordinateur");
        }
        return computer;
    }

    @Override
    public Page<Computer> getPage(PageFilter pPageFilter) throws PersistenceException {
        List<Computer> computers = new ArrayList<>();
        String queryComputers = SELECT_JOIN;
        String conditions = "";
        if (pPageFilter.getConditions() != null && !pPageFilter.getConditions().isEmpty()) {
            conditions += mapConditions(pPageFilter.getConditions());
        }
        queryComputers += conditions + " LIMIT ? OFFSET ?";
        Page<Computer> pPage = new Page<>(pPageFilter.getElementsByPage());
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputers)) {
            preparedStatement.setInt(1, pPageFilter.getElementsByPage());
            preparedStatement.setInt(2, (pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = JdbcMapper.mapResultsToComputerList(resultSet);
            pPage.setPage(pPageFilter.getPageNum());
            pPage.setElements(computers);
            pPage.setTotalElements(count(connection, conditions));
            pPageFilter.setNbPage(pPage.getTotalPages());
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : getPage : ", e);
            throw new PersistenceException("Problème lors de la récupération de la page d'ordinateurs");
        }
        return pPage;
    }

    /**
     * Obtains the conditions from a Map as a String.
     * @param pConditions the Map of conditions
     * @return a String in sql format
     */
    private String mapConditions(Map<String, String> pConditions) {
        String conditions = "";
        if (pConditions != null && !pConditions.isEmpty()) {
            if (pConditions.containsKey("computerName") && pConditions.containsKey("companyName")) {
                conditions = " WHERE computer.name like '%" + pConditions.get("computerName")
                        + "%' OR  company.name like '%" + pConditions.get("companyName") + "%'";
            }
            if (pConditions.containsKey("column")) {
                conditions += " ORDER BY " + pConditions.get("column");
            }
            if (pConditions.containsKey("order")) {
                conditions += " " + pConditions.get("order");
            }
        }
        return conditions;
    }

    /**
     * Count the number of Computers in the DB.
     * @param pConnection the Connection to use
     * @param pFilter an optional filter string
     * @return the number of computers in the DB
     */
    private int count(Connection pConnection, String pFilter) {
        String query = COUNT_ALL;
        if (pFilter != null && !pFilter.isEmpty()) {
            query += pFilter;
        }
        int total = 0;
        try (Statement statement = pConnection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            total = resultSet.getInt("total");
        } catch (SQLException e) {
            LOGGER.error("Error in ComputerDao : count : ", e);
        }
        return total;
    }
}
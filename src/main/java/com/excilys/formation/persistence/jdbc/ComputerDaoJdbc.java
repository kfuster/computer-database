package com.excilys.formation.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.HikariConnectionProvider;
import com.excilys.formation.persistence.mapper.JdbcMapper;

/**
 * DAO class for computers.
 * @author kfuster
 *
 */
public class ComputerDaoJdbc implements ComputerDao {
    private HikariConnectionProvider connectionProvider;
    private static ComputerDaoJdbc computerDaoImpl = null;
    private static final String INSERT_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";
    private static final String SELECT_JOIN = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer";
    /**
     * Constructor for ComputerDaoJdbc.
     * Initializes the connectionProvider.
     */
    private ComputerDaoJdbc() {
        connectionProvider = HikariConnectionProvider.getInstance();
    }
    /**
     * Getter for the ComputerDaoJdbc instance.
     * Initializes it if null.
     * @return the instance of ComputerDaoJdbc
     */
    public static ComputerDaoJdbc getInstance() {
        if (computerDaoImpl == null) {
            computerDaoImpl = new ComputerDaoJdbc();
        }
        return computerDaoImpl;
    }
    @Override
    public Computer create(Computer pComputer) throws PersistenceException {
        String queryComputer = INSERT_COMPUTER;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputer,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setObject(2, pComputer.getIntroduced());
            preparedStatement.setObject(3, pComputer.getDiscontinued());
            preparedStatement.setInt(4, pComputer.getCompany().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int computerId = resultSet.getInt(1);
                pComputer.setId(computerId);
                connection.close();
                return pComputer;
            }
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la création de l'ordinateur");
        }
        return null;
    }
    @Override
    public void update(Computer pComputer) throws PersistenceException {
        String queryComputer = UPDATE_COMPUTER;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputer);
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setObject(2, pComputer.getIntroduced());
            preparedStatement.setObject(3, pComputer.getDiscontinued());
            preparedStatement.setInt(4, pComputer.getCompany().getId());
            preparedStatement.setInt(5, pComputer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de l'update de l'ordinateur");
        }
    }
    @Override
    public void delete(int pID) throws PersistenceException {
        String queryComputer = DELETE_COMPUTER;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputer);
            preparedStatement.setInt(1, pID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la suppression de l'ordinateur");
        }
    }
    @Override
    public Computer getById(int pId) throws PersistenceException {
        String queryComputer = SELECT_JOIN + " WHERE computer.id=?";
        Computer computer = null;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(queryComputer);
            ps.setInt(1, pId);
            ResultSet resultSet = ps.executeQuery();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException("Problème lors de la récupération de l'ordinateur");
        }
        return computer;
    }
    @Override
    public Computer getByName(String pName) throws PersistenceException {
        String queryComputer = SELECT_JOIN + " WHERE computer.name=?";
        Computer computer = null;
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputer);
            preparedStatement.setString(1, pName);
            ResultSet resultSet = preparedStatement.executeQuery();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la récupération de l'ordinateur");
        }
        return computer;
    }
    @Override
    public Page<Computer> getPage(Page<Computer> pPage) throws PersistenceException {
        List<Computer> computers = new ArrayList<>();
        String queryComputers = SELECT_JOIN + " LIMIT ? OFFSET ?";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryComputers);
            preparedStatement.setInt(1, pPage.elemByPage);
            preparedStatement.setInt(2, (pPage.page - 1) * pPage.elemByPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = JdbcMapper.mapResultsToComputerList(resultSet);
            pPage.elems = computers;
            pPage.setTotalElement(count());
        } catch (SQLException e) {
            throw new PersistenceException("Problème lors de la récupération de la page d'ordinateurs");
        }
        return pPage;
    }
    /**
     * Count the number of Computers in the DB.
     * @return the number of computers in the DB
     */
    private int count() {
        int total = 0;
        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            resultSet.next();
            total = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
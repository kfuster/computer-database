package com.excilys.formation.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.mapper.JdbcMapper;

/**
 * DAO class for computers
 * 
 * @author kfuster
 *
 */
public class ComputerDaoJdbc implements ComputerDao {
    private ConnectionProvider connectionProvider;
    private static ComputerDaoJdbc computerDaoImpl = null;
    private static final String INSERT_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String DELETE_COMPUTER = "DELETE * FROM computer WHERE id=?";
    private static final String SELECT_JOIN = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer";

    private ComputerDaoJdbc() {
        connectionProvider = ConnectionProvider.getInstance();
    }

    public static ComputerDaoJdbc getInstance() {
        if (computerDaoImpl == null) {
            computerDaoImpl = new ComputerDaoJdbc();
        }
        return computerDaoImpl;
    }

    @Override
    public Computer create(Computer pComputer) {
        connectionProvider.openConnection();
        String queryComputer = INSERT_COMPUTER;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(queryComputer);
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setObject(2, pComputer.getIntroduced());
            preparedStatement.setObject(3, pComputer.getDiscontinued());
            preparedStatement.setInt(4, pComputer.getCompany().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            connectionProvider.closeConnection();
            if(resultSet.next()) {
                int computerId = resultSet.getInt(1);
                pComputer.setId(computerId);
                return pComputer;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Computer pComputer) {
        connectionProvider.openConnection();
        String queryComputer = UPDATE_COMPUTER;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(queryComputer);
            preparedStatement.setString(1, pComputer.getName());
            preparedStatement.setObject(2, pComputer.getIntroduced());
            preparedStatement.setObject(3, pComputer.getDiscontinued());
            preparedStatement.setInt(4, pComputer.getCompany().getId());
            preparedStatement.setInt(5, pComputer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
    }

    @Override
    public void delete(int pID) {
        connectionProvider.openConnection();
        String queryComputer = DELETE_COMPUTER;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(queryComputer);
            preparedStatement.setInt(1, pID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
    }

    @Override
    public Computer getById(int pId){
        connectionProvider.openConnection();
        String queryComputer = SELECT_JOIN + " AND id=?";
        Computer computer = null;
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(queryComputer);
            ps.setInt(1, pId);
            ResultSet resultSet = ps.executeQuery();
            connectionProvider.closeConnection();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
        return computer;        
    }
    
    /**
     * Get a computer from the DB by it's name
     * 
     * @param pName the computer's name
     * @return a computer
     */
    @Override
    public Computer getByName(String pName) {
        connectionProvider.openConnection();
        String queryComputer = SELECT_JOIN + " AND WHERE computer.name=?";
        Computer computer = null;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(queryComputer);
            preparedStatement.setString(1, pName);
            ResultSet resultSet = preparedStatement.executeQuery();
            computer = JdbcMapper.mapResultToComputer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
        return computer;
    }

    @Override
    public void getPage(Page<Computer> pPage) {
        connectionProvider.openConnection();
        List<Computer> computers = new ArrayList<>();
        String queryComputers = SELECT_JOIN + " LIMIT ? OFFSET ?";

        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(queryComputers);
            preparedStatement.setInt(1, pPage.elemByPage);
            preparedStatement.setInt(2, (pPage.page - 1) * pPage.elemByPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = JdbcMapper.mapResultsToComputerList(resultSet);
            pPage.elems = computers;
            pPage.setTotalElem(count());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
    }

    /**
     * Count the number of Computers in the DB
     */
    private int count() {
        int total = 0;
        try {
            Statement statement = connectionProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            resultSet.next();
            total = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}

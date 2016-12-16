package com.excilys.formation.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.mapper.JdbcMapper;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.util.DateConverter;

import com.zaxxer.hikari.HikariDataSource;

/**
 * DAO class for computers.
 * @author kfuster
 *
 */
@Repository
public class ComputerDaoJdbc implements ComputerDao {
    private JdbcTemplate jdbcTemplate;
    private static final String INSERT_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";
    private static final String DELETE_COMPUTER_LIST = "DELETE FROM computer WHERE id IN";
    private static final String DELETE_COMPUTER_BY_COMPANY = "DELETE FROM computer WHERE company_id=?";
    private static final String SELECT_JOIN = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer LEFT JOIN company ON computer.company_id=company.id ";

    @Autowired
    public void setDataSource(HikariDataSource pDataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(pDataSource);
    }

    @Override
    public Computer create(Computer pComputer) {
        if (pComputer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPUTER,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, pComputer.getName());
                preparedStatement.setObject(2, pComputer.getIntroduced());
                preparedStatement.setObject(3, pComputer.getDiscontinued());
                preparedStatement.setLong(4, pComputer.getCompany().getId());
                return preparedStatement;
            }
        }, holder);
        pComputer.setId(holder.getKey().longValue());
        return pComputer;
    }

    @Override
    public void update(Computer pComputer) {
        if (pComputer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        this.jdbcTemplate.update(UPDATE_COMPUTER,
                new Object[] {pComputer.getName(), DateConverter.fromLocalDateToTimestamp(pComputer.getIntroduced()),
                        DateConverter.fromLocalDateToTimestamp(pComputer.getDiscontinued()),
                        pComputer.getCompany().getId(), pComputer.getId()});
    }

    @Override
    public void delete(long pID) {
        this.jdbcTemplate.update(DELETE_COMPUTER, new Object[] {pID});
    }

    @Override
    public void deleteList(String idList) {
        String query = DELETE_COMPUTER_LIST + " (" + idList + ");";
        this.jdbcTemplate.update(query);
    }

    @Override
    public void deleteByCompany(long pID) {
        this.jdbcTemplate.update(DELETE_COMPUTER_BY_COMPANY, new Object[] {pID});
    }

    @Override
    public Computer getById(long pId) {
        String queryComputer = SELECT_JOIN + " WHERE computer.id=?";
        return this.jdbcTemplate.query(queryComputer, new Object[] {pId}, new ResultSetExtractor<Computer>() {
            @Override
            public Computer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return resultSet.next() ? JdbcMapper.extractComputer(resultSet) : null;
            }
        });
    }

    @Override
    public Computer getByName(String pName) {
        if (pName == null) {
            throw new IllegalArgumentException("A name is needed");
        }
        String queryComputer = SELECT_JOIN + " WHERE computer.name=?";
        return this.jdbcTemplate.query(queryComputer, new Object[] {pName}, new ResultSetExtractor<Computer>() {
            @Override
            public Computer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return resultSet.next() ? JdbcMapper.extractComputer(resultSet) : null;
            }
        });
    }

    @Override
    public Page<Computer> getPage(PageFilter pPageFilter) {
        if (pPageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Computer> computers = new ArrayList<>();
        String queryComputers = SELECT_JOIN;
        String conditions = "";
        if (pPageFilter.getConditions() != null && !pPageFilter.getConditions().isEmpty()) {
            conditions += mapConditions(pPageFilter.getConditions());
        }
        queryComputers += conditions + " LIMIT ? OFFSET ?";
        Page<Computer> pPage = new Page<>(pPageFilter.getElementsByPage());
        computers = this.jdbcTemplate
                .query(queryComputers,
                        new Object[] {pPageFilter.getElementsByPage(),
                                (pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage()},
                        new RowMapper<Computer>() {
                            public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return JdbcMapper.extractComputer(resultSet);
                            }
                        });
        pPage.setPage(pPageFilter.getPageNum());
        pPage.setElements(computers);
        pPage.setTotalElements(count(conditions));
        pPageFilter.setNbPage(pPage.getTotalPages());
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
     * @param pFilter an optional filter string
     * @return the number of computers in the DB
     */
    private int count(String pFilter) {
        String query = COUNT_ALL;
        if (pFilter != null && !pFilter.isEmpty()) {
            query += pFilter;
        }
        return this.jdbcTemplate.queryForObject(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getInt("total");
            }
        });
    }
}
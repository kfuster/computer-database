package com.excilys.formation.persistence.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.formation.mapper.JdbcMapper;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DAO class for companies.
 * @author kfuster
 *
 */
@Repository
public class CompanyDaoJdbc implements CompanyDao {
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL = "SELECT * FROM company ORDER BY company.name";
    private static final String SELECT_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company ";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";

    @Autowired
    public void setDataSource(HikariDataSource pDataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(pDataSource);
    }

    @Override
    public Company getById(long pId) {
        Company company = null;
        company = this.jdbcTemplate.queryForObject(SELECT_BY_ID,
                new Object[] {pId},
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        return JdbcMapper.extractCompany(resultSet);
                    }
                });
        return company;
    }

    @Override
    public void delete(long pID) {
        this.jdbcTemplate.update(DELETE_COMPANY, new Object[] {pID});
    }

    @Override
    public Page<Company> getPage(PageFilter pPageFilter) {
        if (pPageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Company> allCompanies = new ArrayList<>();
        String queryCompanies = SELECT_PAGE;
        queryCompanies += " LIMIT ? OFFSET ?";
        Page<Company> pPage = null;
        allCompanies = this.jdbcTemplate.query(queryCompanies,
            new Object[] {pPageFilter.getElementsByPage(),
                    (pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage()},
            new RowMapper<Company>() {
                public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return JdbcMapper.extractCompany(resultSet);
                }
            });
        pPage = new Page<>(pPageFilter.getElementsByPage());
        pPage.setPage(pPageFilter.getPageNum());
        pPage.setElements((allCompanies));
        pPage.setTotalElements(count(""));
        pPageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }

    @Override
    public List<Company> getAll() {
        List<Company> allCompanies = new ArrayList<>();
        allCompanies = this.jdbcTemplate.query(SELECT_ALL,
            new RowMapper<Company>() {
                public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return JdbcMapper.extractCompany(resultSet);
                }
            });
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
        return this.jdbcTemplate.queryForObject(query,
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getInt("total");
                }
            });
    }
}
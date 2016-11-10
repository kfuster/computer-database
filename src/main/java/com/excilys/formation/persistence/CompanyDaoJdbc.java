package com.excilys.formation.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.mapper.JdbcMapper;

/**
 * DAO class for companies
 * 
 * @author kfuster
 *
 */
public class CompanyDaoJdbc implements CompanyDao {
    private ConnectionProvider connectionProvider;
    private static CompanyDaoJdbc companyDaoImpl = null;
    private static final String SELECT_BY_NAME = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";

    private CompanyDaoJdbc() {
        connectionProvider = ConnectionProvider.getInstance();
    }

    public static CompanyDaoJdbc getInstance() {
        if (companyDaoImpl == null) {
            companyDaoImpl = new CompanyDaoJdbc();
        }
        return companyDaoImpl;
    }


    /**
     * Get a company from the DB by its ID
     * @param pID the ID of the company
     * @return a company or null
     */
    @Override
    public Company getById(int pId){
        connectionProvider.openConnection();
        Company company = null;
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(SELECT_BY_NAME);
            preparedStatement.setInt(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = JdbcMapper.mapResultToCompany(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
        return company;     
    }
    
    /**
     * Get a page of companies
     * 
     * @param pPage the page to get
     * @param pLimit the number of elements by page
     * @return the list of elements found
     */
    @Override
    public void getPage(Page<Company> pPage) {
        connectionProvider.openConnection();
        List<Company> allCompanies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(SELECT_PAGE);
            preparedStatement.setInt(1, pPage.elemByPage);
            preparedStatement.setInt(2, (pPage.page - 1) * pPage.elemByPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            allCompanies = JdbcMapper.mapResultsToCompanyList(resultSet);
            pPage.elems = (allCompanies);
            pPage.setTotalElem(count());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection();
    }

    /**
     * Count the total number of companies
     */
    private int count() {
        int total = 0;
        try {
            Statement statement = connectionProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            if(resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }


}

package com.excilys.formation.cd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cd.entities.Company;

/**
 * DAO class for companies
 * @author kfuster
 *
 */
public class CompanyDAO implements IDAO<Company>{
	private JDBCUtil jdbcUtil;
	private static CompanyDAO _instance = null;
	
	private CompanyDAO(){
		jdbcUtil = new JDBCUtil();
	}
	
	synchronized public static CompanyDAO getInstance(){
		if(_instance == null){
			_instance = new CompanyDAO();
		}
		return _instance;
	}

	public void openConnection(){
		jdbcUtil.openConnection();
	}
	
	public void closeConnection(){
		jdbcUtil.closeConnection();
	}
	
	/**
	 * Saves a company in the DB
	 * @param pCompany the company to save
	 */
	@Override
	public void create(Company pCompany){
		String queryCompany = "INSERT INTO company(name) values(?)";
		PreparedStatement ps;
		try {
			ps = jdbcUtil.getConnection().prepareStatement(queryCompany);
			ps.setString(1, pCompany.getName());
			ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a company in the DB
	 * @param pCompany the company to update
	 */
	@Override
	public void update(Company pCompany){
		String queryCompany = "UPDATE company set name=? WHERE id=?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryCompany);
			ps.setString(1, pCompany.getName());
			ps.setInt(2, pCompany.getId());
			ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a company in the DB
	 */
	@Override
	public void delete(int pID){
		String queryCompany = "DELETE FROM company where id=?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryCompany);
			ps.setInt(1, pID);
			ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a company from the DB by it's ID
	 * @param pID the ID of the company*
	 * @return a company
	 */
	@Override
	public Company getByID(int pID){
		String queryCompany = "SELECT * FROM company where id=?";
		Company company = null;
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryCompany);
			ps.setInt(1, pID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				company = new Company(rs.getString("name"));
				company.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return company;		
	}
	
	/**
	 * Get all companies from DB
	 * @return a list of companies
	 */
	@Override
	public List<Company> getAll() {
		List<Company> allCompanies = new ArrayList<>();		
		String queryComputers = "SELECT * FROM company";
		
		try {
			Statement stmt = jdbcUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(queryComputers);
			while(rs.next()){
				Company company = new Company(rs.getString("name"));
				company.setId(rs.getInt("id"));
				allCompanies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return allCompanies;
	}

	
	@Override
	public Company getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

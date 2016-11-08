package com.excilys.formation.cd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cd.entities.Computer;

/**
 * DAO class for computers
 * @author kfuster
 *
 */
public class ComputerDAO {
	private JDBCUtil jdbcUtil;
	private static ComputerDAO _instance = null;
	
	private ComputerDAO(){
		jdbcUtil = new JDBCUtil();
	}
	
	synchronized public static ComputerDAO getInstance(){
		if(_instance == null){
			_instance = new ComputerDAO();
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
	 * Saves a computer in the DB
	 * @param pComputer the computer to save
	 */
	public void createComputer(Computer pComputer){
		String queryComputer = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputer);
			ps.setString(1, pComputer.getName());
			ps.setDate(2, pComputer.getIntroduced());
			ps.setDate(3, pComputer.getDiscontinued());
			ps.setInt(4, pComputer.getCompanyId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a computer in the DB
	 * @param pComputer the computer to update
	 */
	public void updateComputer(Computer pComputer){
		String queryComputer = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputer);
			ps.setString(1, pComputer.getName());
			ps.setDate(2, pComputer.getIntroduced());
			ps.setDate(3, pComputer.getDiscontinued());
			ps.setInt(4, pComputer.getCompanyId());
			ps.setInt(5, pComputer.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a computer in the DB
	 */
	public void deleteComputer(int pID){
		String queryComputer = "DELETE FROM computer WHERE id=?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputer);
			ps.setInt(1, pID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a computer from the DB by it's ID
	 * @param pID the ID of the computer
	 * @return a computer
	 */
	public Computer getComputerByID(int pID){
		String queryComputer = "SELECT * FROM computer WHERE id=?";
		Computer computer = null;
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputer);
			ps.setInt(1, pID);
			ResultSet rs = ps.executeQuery();
			List<Computer> returnedComp = parseResults(rs);
			if(!returnedComp.isEmpty())
				computer = returnedComp.get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return computer;		
	}
	
	/**
	 * Get a computer from the DB by it's name
	 * @param pName the computer's name
	 * @return a computer
	 */
	public Computer getComputerByName(String pName){
		String queryComputer = "SELECT * FROM computer where name=?";
		Computer computer = null;
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputer);
			ps.setString(1, pName);
			ResultSet rs = ps.executeQuery();
			List<Computer> returnedComp = parseResults(rs);
			if(!returnedComp.isEmpty())
				computer = returnedComp.get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return computer;		
	}

	/**
	 * Get all computers from DB
	 * @return a list of computers
	 */
	public List<Computer> getAllComputers() {
		List<Computer> allComputers = new ArrayList<>();
		String queryComputers = "SELECT * FROM computer";
		
		try {
			Statement stmt = jdbcUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(queryComputers);
			allComputers = parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allComputers;
	}
	
	/**
	 * Get all computers in DB from a company
	 * @param pCompanyID the ID of the company we want the computers of
	 * @return a list of computers
	 */
	public List<Computer> getAllComputersFromCompany(int pCompanyID) {
		List<Computer> allComputers = new ArrayList<>();
		String queryComputers = "SELECT * FROM computer WHERE company_id=?";
		
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(queryComputers);
			ps.setInt(1, pCompanyID);
			ResultSet rs = ps.executeQuery();
			allComputers = parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allComputers;
	}

	/**
	 * Parse a ResultSet to get computers
	 * @param pResultSet the result set to parse
	 * @return a list of computers
	 */
	private List<Computer> parseResults(ResultSet pResultSet){
		List<Computer> resultComputers = new ArrayList<>();
		try {
			while(pResultSet.next()){
				Computer computer = new Computer(pResultSet.getString("name"));	
				computer.setId(pResultSet.getInt("id"));
				computer.setIntroduced(pResultSet.getDate("introduced"));
				computer.setDiscontinued(pResultSet.getDate("discontinued"));
				computer.setCompanyId(pResultSet.getInt("company_id"));
				resultComputers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultComputers;		
	}
	
}

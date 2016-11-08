package com.excilys.formation.cd.dao;

import java.util.List;

/**
 * Interface for DAO classes
 * @author kfuster
 *
 * @param <T> The Selected entity
 */
public interface IDAO<T> {
	/**
	 * Method to save an object in the DB
	 * @param objectToCreate the object to save
	 */
	public void create(T objectToCreate);
	
	/**
	 * Method to update an object in the DB
	 * @param objectToupdate the object to update
	 */
	public void update(T objectToupdate);
	
	/**
	 * Method to delete an object in the DB
	 * @param idToDelete ID of the object to delete
	 */
	public void delete(int idToDelete);
	
	/**
	 * Method to get an object in the DB by it's ID
	 * @param idToGet the id of the object to get
	 * @return the found object
	 */
	public T getByID(int idToGet);
	
	/**
	 * Method to get an object in the DB by it's name
	 * @param name the name of the object to get
	 * @return the found object
	 */
	public T getByName(String name);
	
	/**
	 * Method to get the list of all elements
	 * @return the list of all elements
	 */
	public List<T> getAll();
	
}

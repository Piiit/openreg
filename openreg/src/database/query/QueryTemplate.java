package database.query;

import java.util.ArrayList;

import database.Row;

public abstract class QueryTemplate {
	
	/**
	 * Selecting tuples, that are needed to choose from, when inserting a new student (e.g. in drop-down menus)
	 */
	public abstract ArrayList<Row> getDataset() throws Exception;
	
	/**
	 * Selecting specific tuples by id, returning an ArrayList
	 * Note: Please keep they return value as it is, to standardize our queries classes. 
	 */
	public abstract ArrayList<Row> getDataset(Object id) throws Exception;
	
	/**
	 * Returns all tuples needed to list them inside gui-modules.
	 */
	public abstract ArrayList<Row> getFullDataset() throws Exception;
	
	/**
	 * Inserts a new tuple.
	 * @return ID of the new tuple formerly inserted.
	 */
	public abstract Long insert(Row row) throws Exception;
	
	/**
	 * Update a certain tuple.
	 */
	public abstract void update(Object id, Row row) throws Exception;
	
	/**
	 * Delete a certain tuple.
	 */
	public abstract void delete(Object id) throws Exception;
}

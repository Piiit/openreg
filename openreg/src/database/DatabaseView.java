package database;

import java.util.ArrayList;

public abstract class DatabaseView {
	public abstract ArrayList<Row> getDataset() throws Exception;
	public abstract ArrayList<Row> getDataset(Object id) throws Exception;
	public abstract ArrayList<Row> getFullDataset() throws Exception;
	public abstract Long insert(Row row) throws Exception;
	public abstract void update(Row row) throws Exception;
	public abstract void delete(Object id) throws Exception;
}

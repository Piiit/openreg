package database;

import java.util.ArrayList;

public interface DatabaseView {
	public abstract ArrayList<Row> getDataset() throws Exception;
	public abstract ArrayList<Row> getDataset(Object id) throws Exception;
	public abstract ArrayList<Row> getFullDataset() throws Exception;
	public abstract void insert(ArrayList<Row> rows) throws Exception;
	public abstract void update(ArrayList<Row> rows) throws Exception;
	public abstract void delete(Object id) throws Exception;
}

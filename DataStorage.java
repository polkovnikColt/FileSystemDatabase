package DataAPI;

import java.util.Map;

public interface DataStorage {

	public boolean createNewTable(String data, String tableName);

	public boolean addData(String tableName, String data);

	public boolean deleteTable(String name);

	public String readInfo(String tableName);

	public String avalableTables();

	void nameTable(String name);

	public void saveInfo(String s, String tableName);

	boolean exist(String s);

}

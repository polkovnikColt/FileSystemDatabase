package DataAPI;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataStorageImpl implements DataStorage {

	public static int k;
	public static ArrayList<Object> topics;
	
	public DataStorageImpl(){
		createPrimaryFile();
	}

	@Override
	public boolean createNewTable(String data, String tableName) {
		BufferedWriter read = null;
		if (!exist(tableName)) {
			try {
				read = new BufferedWriter(new FileWriter(tableName + ".txt"));
			} catch (IOException e) {
				return false;
			}
			try {
				read.write(data);
				read.close();
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean deleteTable(String name) {
		if (name.equals("tableNames")) {
			return false;
		}
		String s = "";
		try {
			BufferedReader read = new BufferedReader(new FileReader("tableNames.txt"));
			s = read.readLine();
			read.close();
			String[] names = s.split(",");
			String[] arr = new String[names.length];
			File file = new File(name + ".txt");
			file.delete();
			String temp = "";
			for (int i = 0; i < names.length; i++) {
				if (names[i].equals(name))
					continue;
				arr[i] = names[i];
				temp += arr[i] + ",";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter("tableNames.txt"));
			if (names.length == 0) {
				writer.write(",");
				writer.close();
				return true;
			}
			writer.write(temp);
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public String readInfo(String tableName) {
		if (tableName.equals("tableName")) {
			return null;
		}
		try {
			BufferedReader read = new BufferedReader(new FileReader(tableName + ".txt"));
			String res = read.readLine();
			read.close();
			return res;
		} catch (IOException e) {
			System.out.println("Таблицы " + tableName + " не существует!!!");
			return null;
		}
	}

	@Override
	public String avalableTables() {
		String s = "";
		try {
			BufferedReader read = new BufferedReader(new FileReader("tableNames.txt"));
			s = read.readLine();
			s = s.substring(1, s.length());
			read.close();

		} catch (IOException e) {
			return null;
		}
		return s;
	}

	public void createPrimaryFile() {
		File f = new File("tableNames.txt");
		try {
			if (!f.exists() && !f.isDirectory()) {
				BufferedWriter writer = new BufferedWriter(new FileWriter("tableNames.txt"));
				writer.write(",");
				writer.close();
			}

		} catch (IOException e) {
			System.out.println("Не существует такой таблицы!!!");
		}

	}

	@Override
	public void nameTable(String name) {
		String temp = "";
		BufferedReader read;
		try {
			read = new BufferedReader(new FileReader("tableNames.txt"));
			temp = read.readLine();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("tableNames.txt"));
			writer.write(temp + name + ",");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveInfo(String data, String tableName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tableName + ".txt"));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Map makeMap(String str) {
		return Json.toMap(str);
	}

	@Override
	public boolean exist(String ss) {
		File f = new File(ss);
		if (f.exists()) {
			String s = avalableTables();
			s = s.substring(1, s.length() - 1);
			if (!(avalableTables() == null)) {
				String[] names = s.split(",");
				for (int i = 0; i < names.length; i++) {
					if (names[i].equals(ss))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean addData(String tName, String data) {
		File f = new File(tName + ".txt");
		if (f.exists()) {
			String res = readInfo(tName);
			res = res.substring(0, res.length() - 1) + ",";
			res += data;
			saveInfo(res, tName);
			return true;

		}
		else {
			return false;
		}
	}
	
}

package Tester;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.crypto.Data;

import com.google.gson.ExclusionStrategy;
import com.google.gson.annotations.JsonAdapter;

import DataAPI.*;

public class TestAPI {
	private static String tableName;
	private static ArrayList<Object> topic;

	public static void main(String[] s) {
		int a = 0;
		DataStorage d = new DataStorageImpl();
		System.out.println("Приветствуем в нашей программе \"Эмулятор базы данных\"");
		System.out.println("Выберете необходимую манипуляцию: ");
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Доступные функции: " + "\n1 - Создать таблицу" + "\n2 - Доступные таблицы"
					+ "\n3 - Удалить таблицу" + "\n4 - Показать данные" + "\n5 - Добавить данные в таблицу ");
			try {
				a = Integer.parseInt(DataInput.getLine("?"));
			} catch (Exception e) {
				System.out.println("Неправильный формат данных!!!");
				continue;
			}
			switch (a) {
			case 1:
				boolean check = false;
				String data = TestAPI.createData();
				d.nameTable(tableName);
				if (!d.exist(tableName)) {
					check = d.createNewTable(data, tableName);
				}
				if (check) {
					System.out.println("Таблица успешно создана!!!");
				} else {
					System.out.println("Что то пошло не так!!!");
					continue;
				}
				break;
			case 2:
				String datas = d.avalableTables();
				if (datas != null)
					System.out.println("Список доступных таблиц: " + datas);
				else
					System.out.println("Cписок таблиц пуст!!!");
				break;
			case 3:
				String name = DataInput.getLine("Введите название: ");
				boolean flag = d.deleteTable(name);
				if (flag) {
					System.out.println("Таблица успешно удалена!!!");
				} else {
					System.out.println("Произошла ошибка!!!");
				}
				break;
			case 4:
				String name1 = DataInput.getLine("Введите название: ");
				String data2 = d.readInfo(name1);
				if (data2 != null)
					System.out.println("Данные таблицы: " + data2);
				else
					System.out.println("Что то пошло не так!!!");
				break;
			case 5:
				String tName = DataInput.getLine("Введите название таблицы: ");
				String res = d.readInfo(tName);
				Map m = ((DataStorageImpl) d).makeMap(res);
				Map map1 = (Map) m.get("1");
				ArrayList<Object>topics = new ArrayList<>();
				topics.addAll(map1.keySet());
				String str = createData2(topics, m.size() +1);
				boolean flag1 = d.addData(tName, str);
				if (flag1) {
					System.out.println("Данные успешно добавлены!!!");
					break;
				} else {
					System.out.println("Такой таблицы не существует!!!");
				}
				break;
			}
		}
	}

	private static String createData() {
		int size = 0;

		System.out.println("Введите название таблицы: ");
		tableName = DataInput.getLine("??");
		String itog = "{";
		while (true) {
			System.out.println("Введите количество коллонок: ");
			try {
				size = DataInput.getInt("?");
			} catch (NumberFormatException e) {
				System.out.println("Неправильный формат данных!");
				continue;

			}
			System.out.println("Введите название коллонок: ");
			// список с названиями колонок
			topic = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				// ввод всех названий
				String name = DataInput.getLine("Ввод: ");
				topic.add(name);
			}
			System.out.println("Введите данные для каждой колонки(-1 конец ввода): ");
			for (int k = 1;; k++) {
				int end = 0;
				try {
					end = DataInput.getInt("Конец?: ");
				} catch (NumberFormatException e) {
					System.out.println("Неправильный формат данных!");
					k--;
					continue;
				}
				if (end == -1) {
					if (itog.length() > 2) {
						itog = itog.substring(0, itog.length() - 1);
					}

					itog += "}";
					return itog;
				}
				// статичная колонка с номером
				itog += "\"" + k + "\": {";
				for (int counter = 0; counter < topic.size(); counter++) {
					String contain = DataInput.getLine("Ввод: ");
					// конец ввода строки
					if (counter == topic.size() - 1) {
						itog += "\"" + topic.get(counter) + "\"" + ":" + "\"" + contain + "\"},";
						break;
						// начало ввода строки
					} /*
						 * else if (counter == 0) { itog += "{ \"" + topic.get(counter) + "\"" + ":" +
						 * "\"" + contain + "\","; continue; }
						 */

					itog += "\"" + topic.get(counter) + "\"" + ":" + "\"" + contain + "\",";
				}
			}

		}

	}

	private static String createData2(ArrayList<Object> topics, int z) {
		System.out.println("Введите данные для каждой колонки(-1 конец ввода): ");
		String itog = "";
		for (int k = z;; ++k) {
			int end = 0;
			try {
				end = DataInput.getInt("Конец?: ");
			} catch (NumberFormatException e) {
				System.out.println("Неправильный формат данных!");
				continue;
			}
			if (end == -1) {
				String s = itog.substring(0, itog.length() - 1);
				s += "}";
				return s;
			}
			// статичная колонка с номером
			itog += "\"" + k + "\": {";
			for (int counter = 0; counter < topics.size(); counter++) {
				String contain = DataInput.getLine("Ввод: ");
				// конец ввода строки
				if (counter == topics.size() - 1) {
					itog += "\" " + topics.get(counter) + "\" " + ":" + "\" " + contain + "\"},";
					break;
					// начало ввода строки
				}
				itog += "\"" + topics.get(counter) + "\"" + ":" + "\"" + contain + "\",";
			}

		}

	}
}

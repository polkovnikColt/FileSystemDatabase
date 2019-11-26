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
		System.out.println("������������ � ����� ��������� \"�������� ���� ������\"");
		System.out.println("�������� ����������� �����������: ");
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("��������� �������: " + "\n1 - ������� �������" + "\n2 - ��������� �������"
					+ "\n3 - ������� �������" + "\n4 - �������� ������" + "\n5 - �������� ������ � ������� ");
			try {
				a = Integer.parseInt(DataInput.getLine("?"));
			} catch (Exception e) {
				System.out.println("������������ ������ ������!!!");
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
					System.out.println("������� ������� �������!!!");
				} else {
					System.out.println("��� �� ����� �� ���!!!");
					continue;
				}
				break;
			case 2:
				String datas = d.avalableTables();
				if (datas != null)
					System.out.println("������ ��������� ������: " + datas);
				else
					System.out.println("C����� ������ ����!!!");
				break;
			case 3:
				String name = DataInput.getLine("������� ��������: ");
				boolean flag = d.deleteTable(name);
				if (flag) {
					System.out.println("������� ������� �������!!!");
				} else {
					System.out.println("��������� ������!!!");
				}
				break;
			case 4:
				String name1 = DataInput.getLine("������� ��������: ");
				String data2 = d.readInfo(name1);
				if (data2 != null)
					System.out.println("������ �������: " + data2);
				else
					System.out.println("��� �� ����� �� ���!!!");
				break;
			case 5:
				String tName = DataInput.getLine("������� �������� �������: ");
				String res = d.readInfo(tName);
				Map m = ((DataStorageImpl) d).makeMap(res);
				Map map1 = (Map) m.get("1");
				ArrayList<Object>topics = new ArrayList<>();
				topics.addAll(map1.keySet());
				String str = createData2(topics, m.size() +1);
				boolean flag1 = d.addData(tName, str);
				if (flag1) {
					System.out.println("������ ������� ���������!!!");
					break;
				} else {
					System.out.println("����� ������� �� ����������!!!");
				}
				break;
			}
		}
	}

	private static String createData() {
		int size = 0;

		System.out.println("������� �������� �������: ");
		tableName = DataInput.getLine("??");
		String itog = "{";
		while (true) {
			System.out.println("������� ���������� ��������: ");
			try {
				size = DataInput.getInt("?");
			} catch (NumberFormatException e) {
				System.out.println("������������ ������ ������!");
				continue;

			}
			System.out.println("������� �������� ��������: ");
			// ������ � ���������� �������
			topic = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				// ���� ���� ��������
				String name = DataInput.getLine("����: ");
				topic.add(name);
			}
			System.out.println("������� ������ ��� ������ �������(-1 ����� �����): ");
			for (int k = 1;; k++) {
				int end = 0;
				try {
					end = DataInput.getInt("�����?: ");
				} catch (NumberFormatException e) {
					System.out.println("������������ ������ ������!");
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
				// ��������� ������� � �������
				itog += "\"" + k + "\": {";
				for (int counter = 0; counter < topic.size(); counter++) {
					String contain = DataInput.getLine("����: ");
					// ����� ����� ������
					if (counter == topic.size() - 1) {
						itog += "\"" + topic.get(counter) + "\"" + ":" + "\"" + contain + "\"},";
						break;
						// ������ ����� ������
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
		System.out.println("������� ������ ��� ������ �������(-1 ����� �����): ");
		String itog = "";
		for (int k = z;; ++k) {
			int end = 0;
			try {
				end = DataInput.getInt("�����?: ");
			} catch (NumberFormatException e) {
				System.out.println("������������ ������ ������!");
				continue;
			}
			if (end == -1) {
				String s = itog.substring(0, itog.length() - 1);
				s += "}";
				return s;
			}
			// ��������� ������� � �������
			itog += "\"" + k + "\": {";
			for (int counter = 0; counter < topics.size(); counter++) {
				String contain = DataInput.getLine("����: ");
				// ����� ����� ������
				if (counter == topics.size() - 1) {
					itog += "\" " + topics.get(counter) + "\" " + ":" + "\" " + contain + "\"},";
					break;
					// ������ ����� ������
				}
				itog += "\"" + topics.get(counter) + "\"" + ":" + "\"" + contain + "\",";
			}

		}

	}
}

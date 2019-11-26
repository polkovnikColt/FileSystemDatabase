package DataAPI;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Json {

	static Gson gson = new GsonBuilder().serializeNulls().create();

	static public Map toMap(String json) {
		if (isBlank(json))
			return Collections.EMPTY_MAP;
		Type type = new TypeToken<LinkedHashMap<String, Object>>() {
		}.getType();
		return gson.fromJson(json, type);
	}

	static public String toJson(Map map) {
		return gson.toJson(map);
	}

	private static boolean isBlank(String str) {
		return str == null || str.length() == 0;
	}
}

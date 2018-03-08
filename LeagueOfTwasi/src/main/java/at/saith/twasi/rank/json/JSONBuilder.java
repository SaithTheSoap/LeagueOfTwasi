package at.saith.twasi.rank.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONBuilder {

	private final HashMap<String, Object> jsonMap = new HashMap<>();
	private ArrayList<String> toIgnoreString;
	public JSONBuilder() {}
	public JSONBuilder(JSONObject object) {
		this(JSONObjectToMap(object));
	}
	public JSONBuilder(HashMap<String, Object> map) {
		jsonMap.putAll(map);
	}
	public JSONBuilder(JSONBuilder builder) {
		this(builder.getMap());
	}
	public JSONBuilder add(String name, Object value) {
		return add(name, value,false);
	}
	public JSONBuilder add(String name, Object value, boolean ignoreStringSanitize) {
		if(ignoreStringSanitize) {
			if(toIgnoreString == null) {
				toIgnoreString = new ArrayList<>();
			}
			toIgnoreString.add(name);
		}else
		if(isString(value)) {
			value = sanitizeString(value.toString());
		}else
		if(isJSONBuilder(value)) {
			value = ((JSONBuilder) value).getMap();
		}else
		if(isList(value)) {
			Object last = null;
			for(Object obj: (List<?>)value) {
				if(last != null) {}
				last = obj;
				if(!last.getClass().isAssignableFrom(obj.getClass())) {return this;}
			}
			value = ((List<?>) value).toArray();
		}
		jsonMap.put(name, value);
		return this;
	}
	public void remove(String name) {
		jsonMap.remove(name);
	}
	public String toString() {
		return toString(true);
	}
	public String toString(boolean prettyPrint) {
		String json = toString(jsonMap);
		if(prettyPrint) {
			return JSONFormatter.formatJSON(json);
		}
		return json;
	}
	public HashMap<String, Object> getMap(){
		return jsonMap;
	}
	public String toString(HashMap<String, Object> jsonmap) {
		
		StringBuilder jsonStringBuilder = new StringBuilder("{");
		
		for(String name : jsonmap.keySet()) {
			Object value = jsonmap.get(name);
			jsonStringBuilder.append(toJson(name, value));
		}
		jsonStringBuilder.setLength(jsonStringBuilder.length()-1);
		jsonStringBuilder.append("}");
		return jsonStringBuilder.toString();
	}

	private String toJson(String name, Object value) {
		StringBuilder json = new StringBuilder("\""+name+"\":");
		String valueString = valueToString(name,value);
		
		json.append(valueString+",");
		return json.toString();
	}
	@SuppressWarnings("unchecked")
	private  String valueToString(String name,Object value) {
		String valueString = "";
		if(!isJSONBuilder(value)) {
			valueString = value.toString();
		}
		if(isString(value)) {
			if(toIgnoreString == null||!toIgnoreString.contains(name)) {
				valueString = "\""+valueString+"\"";
			}
			
		}else if(isNumber(value)) {
			
		}else if(isBoolean(value)) {
			valueString = valueString.toLowerCase();
		}else if(isMap(value)) {
			valueString =  toString((HashMap<String, Object>)value);
		}else if(isArray(value)) {
			StringBuilder builder = new StringBuilder("[");
			for(Object obj: (Object[])value) {
				builder.append(valueToString(name, obj)+",");
			}
			builder.setLength(builder.length()-1);
			builder.append("]");
			valueString = builder.toString();
		}
		else {
			valueString = value.toString();
		}
		
		return valueString;
	}
	private static boolean isString(Object value) {
		return value instanceof String;
	}
	private static boolean isNumber(Object value) {
		return Number.class.isAssignableFrom(value.getClass());
	}
	private static boolean isBoolean(Object value) {
		return value instanceof Boolean;
	}
	private static boolean isMap(Object value) {
		return value instanceof HashMap;
	}
	private static boolean isJSONBuilder(Object value) {
		return value instanceof JSONBuilder;
	}
	private static boolean isArray(Object value) {
		return value.getClass().isArray();
	}
	private static boolean isList(Object value) {
		return value instanceof List;
	}
	private static String sanitizeString(String string) {
		return string.replace("\\","\\\\").replace("\"", "\\\"").replaceAll("/", "\\/");
	}
	private static HashMap<String, Object> JSONObjectToMap(JSONObject object){
		HashMap<String, Object> toreturn = new HashMap<>();
		for(Object key : object.keySet()) {
			toreturn.put(key.toString(), object.get(key));
		}
		return toreturn;
	}
	public JSONObject toJsonObject() {
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) parser.parse(toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	public SimpleJSONObject toSimpleJson() {
		return new SimpleJSONObject(toJsonObject());
	}
	
}
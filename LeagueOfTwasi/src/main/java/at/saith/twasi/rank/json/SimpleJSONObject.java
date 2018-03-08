package at.saith.twasi.rank.json;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.twasi.core.logger.TwasiLogger;



public class SimpleJSONObject {

	private JSONObject jsonObject ;

	public SimpleJSONObject(JSONObject object) {
		jsonObject = object;
	}
	public SimpleJSONObject(SimpleJSONObject object) {
		this(object.toJSONObject());
	}
	public SimpleJSONObject(String json) {
		this(SimpleJSONObject.fromString(json));
	}
	public Object get(String name) {
		if(!name.contains("/")) {
			name += "/";
		}
		String[] keys = name.split("/");
		Object returned = null;
		for(String key: keys) {
			if(returned == null) {
			returned = jsonObject.get(key);
			}else {
				if(returned instanceof JSONObject) {
					returned = ((JSONObject) returned).get(key);
				}
				else {
					return returned;
				}
			}
		}
		return returned;
	}
	public String getString(String name) {
		return get(name).toString();
	}
	public Long getLong(String name) {
		return (Long) get(name);
	}
	public Double getDouble(String name) {
		return (Double) get(name);
	}
	public Boolean getBoolean(String name) {
		return (Boolean) get(name);
	}
	public JSONArray getArray(String name) {
		JSONArray array = (JSONArray) get(name);
		return array;
	}
	public SimpleJSONObject getJsonObject(String name) {
		return new SimpleJSONObject((JSONObject)get(name));
	}
	
	public static JSONObject fromString(String json) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(json);
		} catch (ParseException e) {
			TwasiLogger.log.error(e.getMessage());
		}
		return obj;
	}
	public static SimpleJSONObject fromUrl(String url) {
		try {
			return new SimpleJSONObject(new JSONURLParser().parseUrl(url));
		} catch (IOException e) {
			TwasiLogger.log.error(e.getMessage());
		}
		return null;
	}
	public String toString() {
		return jsonObject.toJSONString();
	}
	public JSONObject toJSONObject() {
		return jsonObject;
	}
}

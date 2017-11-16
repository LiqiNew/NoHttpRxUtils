package com.liqi.nohttputils.nohttp.gsonutils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Gson 简单操作的工具类
 * 
 * @author Liqi
 * 
 */
public class JsonUtil {

	private static final String TAG = "JsonUtil";
	private static Gson gson = null;

	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private JsonUtil() {
	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		String jsonStr = null;
		try {
			if (gson != null) {
				jsonStr = gson.toJson(object);
			}
		} catch (Exception e) {
			Log.e(TAG, "object to json string error >>" + e.getMessage());
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	@Deprecated
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, //
				new JsonSerializer<Date>() {//
					public JsonElement serialize(Date src, Type typeOfSrc,
							JsonSerializationContext context) {
						SimpleDateFormat format = new SimpleDateFormat(
								dateformat);
						return new JsonPrimitive(format.format(src));
					}

				}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) throws JsonSyntaxException {
		List<?> list = null;
		try {
			if (gson != null) {
				Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
				}.getType();
				list = gson.fromJson(jsonStr, type);
			}
		} catch (Exception e) {
			Log.e(TAG, "json string to list<?> error >>" + e.getMessage());
		}
		return list;
	}

	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> map = null;
		try {
			if (gson != null) {
				Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
				}.getType();
				map = gson.fromJson(jsonStr, type);
			}
			return map;
		} catch (Exception e) {
			Log.e(TAG, "json string to map error >>" + e.getMessage());
		}
		return map;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static <T> T jsonToBean(String jsonStr, Class<T> cl) {
		T t = null;
		try {
			if (gson != null) {
				t = gson.fromJson(jsonStr, cl);
			}
			return t;
		} catch (Exception e) {
			Log.e(TAG, "json string to bean object error >>" + jsonStr);
		}
		return t;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl,
			final String pattern) {
		Object obj = null;
		Gson gson = new GsonBuilder().registerTypeAdapter(//
				Date.class, //
				new JsonDeserializer<Date>() {//
					public Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String dateStr = json.getAsString();
						try {
							return format.parse(dateStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
				}).setDateFormat(pattern).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}

	/**
	 * 根据
	 * 
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object obj = null;

		try {
			Map<?, ?> map = jsonToMap(jsonStr);
			if (map != null && map.size() > 0) {
				obj = map.get(key);
				System.out.println(">>>>Map对象装换>>" + obj);
			}
		} catch (Exception e) {
			Log.e(TAG, "get value from map error >>" + e.getMessage());
		}
		return obj;
	}
}

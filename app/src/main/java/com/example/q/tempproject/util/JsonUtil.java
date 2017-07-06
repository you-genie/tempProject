package com.example.q.tempproject.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2017-07-06.
 */

public class JsonUtil {
  public static JSONObject getJSONObjectFrom(String jsonString) {
    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      return jsonObject;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getStringFrom(JSONObject jsonObject, String key) {
    try{
      return jsonObject.getString(key);
    } catch (JSONException e) {
      e.printStackTrace();
      return "";
    }
  }
}

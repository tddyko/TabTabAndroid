package com.yjrlab.tabdoctor.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public class BaseModel {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Map<String, Object> getQueryMap() {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject data = new JSONObject(toString());

            Iterator<String> iterator = data.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                result.put(key, data.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}

package chestnut.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import chestnut.Interface.json.Json;

/**
 * Created by Chestnut on 2016/12/9.
 */

public class JsonUtils implements Json{

    private static JsonUtils jsonUtils = new JsonUtils();
    private Gson gson = null;
    private JsonUtils() {}
    public static JsonUtils getInstance() {
        return jsonUtils;
    }
    public void init() {
        if (this.gson == null) {
            gson = new Gson();
        }
    }

    @Override
    public <Bean> Bean getBean(String json, Class<Bean> beanClass) {
        init();
        return gson.fromJson(json,beanClass);
    }

    @Override
    public <Bean> List<Bean> getListBean(String json, Class<Bean> beanClass) {
        init();
        return gson.fromJson(json, new TypeToken<List<Bean>>(){}.getType());
    }

}

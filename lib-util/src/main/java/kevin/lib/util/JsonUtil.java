package kevin.lib.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

    public static Map jsonToMap(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper.readValue(json, Map.class);
    }

    public static Map jsonToClass(String json, Class clazz) throws JsonParseException, JsonMappingException,
            IOException {
        ObjectMapper mapper = new ObjectMapper();
        // mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper.readValue(json, clazz);
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}

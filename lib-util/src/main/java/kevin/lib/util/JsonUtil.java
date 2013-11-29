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
    
    public static void main(String[] args) {
        String t = "中";
        byte[] b = t.getBytes();
        for(int i=0; i<b.length; i++) {
            for(int j=0; j<8; j++) {
                System.out.print((b[i]>>i)&1);
            }
        }
        byte[] zhong = {(byte)0xff,(byte)0x00,(byte)0x00};
        System.out.println(new String(zhong));
    }
}

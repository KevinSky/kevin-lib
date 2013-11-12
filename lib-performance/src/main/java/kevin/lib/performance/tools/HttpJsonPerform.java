package kevin.lib.performance.tools;

import java.util.Map;

import kevin.lib.performance.PerformFailure;

public class HttpJsonPerform extends HttpPerform {

    public HttpJsonPerform(String url) {
        super(url);
    }

    @Override
    public void perform() throws PerformFailure {
        boolean success = true;
        try {
            Map jsonMap = httpClientManagerUtil.doGet2Map(url);
            if(!"1".equals(jsonMap.get("code")+""))
                success = false;
        } catch (Exception e) {
            throw new PerformFailure(e);
        }
        if(!success)
            throw new PerformFailure("code=-1");
    }
}

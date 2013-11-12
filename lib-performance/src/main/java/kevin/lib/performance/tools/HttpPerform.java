package kevin.lib.performance.tools;

import kevin.lib.performance.Perform;
import kevin.lib.performance.PerformFailure;
import kevin.lib.util.http.HttpClientConfig;
import kevin.lib.util.http.HttpClientManagerUtil;

public class HttpPerform implements Perform {
    protected static HttpClientManagerUtil httpClientManagerUtil;
    protected String url;

    
    static {
        HttpClientConfig config = new HttpClientConfig();
        config.setSocketTimeOut(1500);
        config.setConnectionTimeout(700);
        config.setConnectionManagerTimeout(1000);
        config.setMaxTotalConnections(2000);
        config.setMaxConnectionsPerHost(1000);
        httpClientManagerUtil = new HttpClientManagerUtil(config);
    }

    public HttpPerform(String url) {
        this.url = url;
    }
    
	@Override
	public void perform() throws PerformFailure {
		try {
			String response = httpClientManagerUtil.doGet(url);
		} catch (Exception e) {
		    throw new PerformFailure(e);
		}
	}

}

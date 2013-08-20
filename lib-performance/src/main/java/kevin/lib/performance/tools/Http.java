package kevin.lib.performance.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import kevin.lib.performance.Perform;
import kevin.lib.performance.PerformFailure;

public class Http implements Perform {

	private int n = 1000;
	private int c = 1;
	private String url = null;
	private String save = null;

	private int count = 0;

	public static void main(String[] args) throws PerformFailure {
		Http h = new Http();
		h.start(args);
	}

	public void start(String[] args) throws PerformFailure {
		if(!checkArgs(args)) {
			System.out.println("Using : -n -c -url [-save]");
			return;
		}
		System.out.println("-n:"+n+", -c:"+c+", url:"+url);
		this.perform();
	}
	
	private boolean checkArgs(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if ("-n".equals(arg)) {
					n = Integer.parseInt(args[i + 1]);
					count++;
				} else if ("-c".equals(arg)) {
					c = Integer.parseInt(args[i + 1]);
					count++;
				} else if ("-url".equals(arg)) {
					url = args[i + 1];
					count++;
				} else if ("-save".equals(arg)) {
					save = args[i + 1];
				}
			}
		} catch (Exception e) {
			count = 0;
		}
		if(count<3)
			return false;
		else
			return true;
	}

	@Override
	public void perform() throws PerformFailure {
		try {
			URL _url = new URL(url);
			
			URLConnection con = _url.openConnection();
			con.connect();
			InputStream input = con.getInputStream();
			byte[] buffer = new byte[100];
			int size = input.read(buffer);
			String str = new String(buffer, 0, size);
			System.out.println(str);
		} catch (MalformedURLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

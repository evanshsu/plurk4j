package plurk4j;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/***
 * Plurk Http Request Object Adapter 
 * It's implemented by Apache Http Client Library
 * 
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class PlurkHttpAdapter implements PlurkHttp {
	private HttpClient httpclient;
	public String execute(String url) throws Exception {
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		return httpclient.execute(httpget, responseHandler);
	}

	public boolean init() {
		try{
			this.httpclient = new DefaultHttpClient();
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public boolean close() {
		try{
			httpclient.getConnectionManager().shutdown();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

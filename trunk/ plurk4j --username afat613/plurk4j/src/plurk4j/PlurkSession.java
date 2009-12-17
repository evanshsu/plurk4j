package plurk4j;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import plurk4j.entity.PlurkUser;
import plurk4j.json.PlurkProfile;

/***
 * Plurk Session
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.0.1
 */
public class PlurkSession{
	private String api_key;
	private HttpClient httpclient;
	private PlurkProfile ownProfile;
	private PlurkUser userInfo;
	private Boolean login = false;
	private String error;
	
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
	
	public String execute(String uri) throws ClientProtocolException, IOException {
		HttpClient httpclient = this.getHttpclient();
		HttpGet httpget = new HttpGet(uri);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		return httpclient.execute(httpget, responseHandler);
	}

	HttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}
	public PlurkProfile getOwnProfile() {
		return ownProfile;
	}

	public void setOwnProfile(PlurkProfile ownProfile) {
		this.ownProfile = ownProfile;
	}

	public Boolean isLogin() {
		return login;
	}

	public void enable() {
		login = true;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

	public PlurkUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(PlurkUser userInfo) {
		this.userInfo = userInfo;
	}

}

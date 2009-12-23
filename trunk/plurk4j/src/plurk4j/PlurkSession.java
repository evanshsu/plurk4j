package plurk4j;

import plurk4j.entity.PlurkProfile;

/***
 * Plurk Session
 * Every session is mapping to a Http connection and a Plurk User.
 * 
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.0.1
 */
public class PlurkSession{
	private String api_key;
	private PlurkHttp plurkHttpAdapter;

	private PlurkProfile profile;
	private Boolean login = false;
	private String error;
	
	PlurkSession() {}
	PlurkSession(PlurkHttp plurkHttpAdapter) {
		this.plurkHttpAdapter = plurkHttpAdapter;
	}
	
	boolean init() {
		if(plurkHttpAdapter == null)
			plurkHttpAdapter = new PlurkHttpAdapter();
		return plurkHttpAdapter.init();
	}
	
	boolean close() {
		return plurkHttpAdapter.close();
	}
	
	public String execute(String url) throws Exception {
		return plurkHttpAdapter.execute(url);
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

	public PlurkProfile getProfile() {
		return profile;
	}

	public void setProfile(PlurkProfile profile) {
		this.profile = profile;
	}
	
	public PlurkHttp getPlurkHttpAdapter() {
		return plurkHttpAdapter;
	}

	public void setPlurkHttpAdapter(PlurkHttp plurkHttpAdapter) {
		this.plurkHttpAdapter = plurkHttpAdapter;
	}

}

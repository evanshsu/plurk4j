package plurk4j;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkUser;
import plurk4j.entity.Plurk.PlurkQualifier;
import plurk4j.json.PlurkProfile;
import plurk4j.json.PlurkResponses;
import plurk4j.json.Plurks;

/***
 * Plurk Common Utils
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.0.1
 */
public class PlurkUtils {
	private static final String PLURK_URI = "http://www.plurk.com/API";
	private static String api_key = "FY9U2Ju3PMeeKO0kPp3wOdrGD8hgb2Zb";
	private static ObjectMapper json = new ObjectMapper();
	static {
		json.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	private PlurkUtils() {}
	
	/***
	 * Every session is mapping to a Http connection and a Plurk User.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession() {
		PlurkSession session = new PlurkSession();
		session.setHttpclient(new DefaultHttpClient());
		return session;
	}
	/***
	 * The same with createSession, but you can use your API_KEY for this session.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession(String api_key) {
		PlurkSession session = PlurkUtils.createSession();
		session.setApi_key(api_key);
		return session;
	}
	/***
	 * Close session when you don't need this plurk user.
	 * @param session PlurkSession
	 * @return true:success, false:failure
	 */
	public static boolean closeSession(PlurkSession session) {
		return session.close();
	}
	/***
	 * Login plurk user
	 * @param session PlurkSession
	 * @param username Username in plurk
	 * @param password Password in plurk
	 * @return true:success, false:failure
	 */
	public static boolean login(PlurkSession session,String username, String password) {
		if(username == null || password == null) {
			session.setError("username or password is null");
			return false;
		}
		String result = null;
		session.setLogin(true);
		try {
			result = session.execute(PLURK_URI + "/Users/login?api_key="
					+ PlurkUtils.getApi_key(session) + "&username=" + username + "&password=" + password);
		} catch (ClientProtocolException e) {
			session.setLogin(false);
			session.setError("LOGIN FAILURE");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			session.setLogin(false);
			session.setError(e.getMessage());
			e.printStackTrace();
			return false;
		}

		if(result != null) {
			try {
				PlurkProfile ownProfile = json.readValue(result, PlurkProfile.class);
				session.setOwnProfile(ownProfile);
				if(ownProfile != null)
					session.setUserInfo(ownProfile.getUser_info());
			} catch (JsonParseException e) {
				session.setLogin(false);
				session.setError(e.getMessage());
				e.printStackTrace();
				return false;
			} catch (JsonMappingException e) {
				session.setLogin(false);
				session.setError(e.getMessage());
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				session.setLogin(false);
				session.setError(e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	/***
	 * Get other's profile
	 * @param session PlurkSession
	 * @param user_id user_id
	 * @return PlurkProfile
	 */
	public static PlurkProfile getPublicProfile(PlurkSession session,Long user_id) {
		if(user_id == null) {
			session.setError("user_id is null");
			return null;
		}
		String result = null;
		try {
			result = session.execute(PLURK_URI + "/Profile/getPublicProfile?api_key="
					+ PlurkUtils.getApi_key(session) + "&user_id=" + user_id);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}

		if(result != null) {
			try {
				return json.readValue(result, PlurkProfile.class);
			} catch (Exception e) {
				session.setError(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	/***
	 * Get plurks about this session
	 * @param session PlurkSession
	 * @return Plurks
	 */
	public static Plurks getPlurks(PlurkSession session) {
		return PlurkUtils.getPlurks(session,null,null,null,null,null);
	}
	/***
	 * Get plurks
	 * @param session PlurkSession
	 * @param offset Return plurks older than offset, formatted as 2009-6-20T21:55:34. 
	 * @param limit How many plurks should be returned? Default is 20. 
	 * @param only_user The numeric ID of the user who's plurks should be returned. 
	 * @param only_responded Setting it to true will only return responded plurks 
	 * @param only_private Setting it to true will only return private plurks 
	 * @return Plurks
	 */
	public static Plurks getPlurks(PlurkSession session, String offset, Long limit
			,Long only_user,Boolean only_responded,Boolean only_private) {
		StringBuilder options = new StringBuilder();
		if(offset != null) options.append("&offset=" + offset);
		if(limit != null) options.append("&limit=" + limit);
		if(only_user != null) options.append("&only_user=" + only_user);
		if(only_responded != null) options.append("&only_responded=" + only_responded);
		if(only_private != null) options.append("&only_private=" + only_private);
		
		String result = null;
		try {
			result = session.execute(PLURK_URI + "/Timeline/getPlurks?api_key="
					+ PlurkUtils.getApi_key(session) + options);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		if(result != null) {
			try {
				Plurks plurks = json.readValue(result, Plurks.class);
				if(plurks != null && plurks.getPlurks() != null && plurks.getPlurk_users() != null) {
					for(int i=0;i<plurks.getPlurks().size();i++) {
						Plurk plurk = plurks.getPlurks().get(i);
						if(plurk != null && plurk.owner_id != null) {
							PlurkUser user = plurks.getPlurk_users().get("" + plurk.owner_id);
							if(user != null && plurk.owner_id.equals(user.id)) {
								plurk.setPlurkUser(user);
								user.addPlurk(plurk);
							}
						}
					}
				}
				return plurks;
			} catch (Exception e) {
				session.setError(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	/***
	 * Get Plurk Responses
	 * @param session PlurkSession
	 * @param plurk_id The plurk that the responses should be added to. 
	 * @param from_response Only fetch responses from an offset, should be 5, 10 or 15. 
	 * @return
	 */
	public static PlurkResponses getResponses(PlurkSession session, Long plurk_id, Long from_response) {
		String result = null;
		try {
			result = session.execute(PLURK_URI + "/Responses/get?api_key="
					+ PlurkUtils.getApi_key(session) + "&plurk_id=" + plurk_id
					+ "&from_response=" + from_response);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, PlurkResponses.class);
			} catch (Exception e) {
//				session.setError(e.getMessage());
//				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}

	/***
	 * Add Response
	 * @param session PlurkSession
	 * @param plurk_id The plurk that the responses should be added to. 
	 * @param content The response's text. 
	 * @param qualifier The Plurk's qualifier, must be in English. [loves,likes,shares,gives,hates,wants,has,will,asks,wishes,was,feels,thinks,says,is,:,freestyle,hopes,needs,wonders]
	 * @return
	 */
	public static Plurk addResponse(PlurkSession session, Long plurk_id, String content, PlurkQualifier qualifier) {
		String result = null;
		try {
			result = session.execute(PLURK_URI + "/Responses/responseAdd?api_key="
					+ PlurkUtils.getApi_key(session) + "&plurk_id=" + plurk_id
					+ "&content=" + content + "&qualifier=" + qualifier.toString());
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, Plurk.class);
			} catch (Exception e) {
				session.setError(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	/***
	 * Add Plurk
	 * @param session PlurkSession
	 * @param content The Plurk's text.  
	 * @param qualifier The Plurk's qualifier, must be in English. [loves,likes,shares,gives,hates,wants,has,will,asks,wishes,was,feels,thinks,says,is,:,freestyle,hopes,needs,wonders]
	 * @return
	 */
	public static Plurk addPlurk(PlurkSession session, String content, PlurkQualifier qualifier) {
		String result = null;
		try {
			result = session.execute(PLURK_URI + "/Timeline/plurkAdd?api_key="
					+ PlurkUtils.getApi_key(session)
					+ "&content=" + content + "&qualifier=" + qualifier.toString());
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, Plurk.class);
			} catch (Exception e) {
				session.setError(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}

	public static String getApi_key(PlurkSession session) {
		if(session.getApi_key() != null && session.getApi_key().length() > 0)
			return session.getApi_key();
		else
			return api_key;
	}

	public static void setApi_key(String api_key) {
		PlurkUtils.api_key = api_key;
	}

}

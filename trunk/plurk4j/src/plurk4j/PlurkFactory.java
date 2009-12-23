package plurk4j;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkProfile;
import plurk4j.entity.PlurkUser;
import plurk4j.entity.PlurkEnum.PlurkQualifier;
import plurk4j.official.JsonPlurk;
import plurk4j.official.JsonPlurks;
import plurk4j.official.JsonProfile;
import plurk4j.official.JsonResponses;
import plurk4j.official.JsonUser;

/***
 * Plurk Factory
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class PlurkFactory {
	private static final String PLURK_URI = "http://www.plurk.com";
	private static String api_key = "FY9U2Ju3PMeeKO0kPp3wOdrGD8hgb2Zb";
	private static ObjectMapper json = new ObjectMapper();
	private static boolean debug = false;
	static {
		//when plurk json format change or add new field, we won't throw exception.
		json.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, debug);
	}
	public static void setDebug(boolean debug) {
		PlurkFactory.debug = debug;
		json.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, debug);
	}
	
	private PlurkFactory() {}
	
	/***
	 * Every session is mapping to a Http connection and a Plurk User.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession() {
		PlurkSession session = new PlurkSession();
		session.init();
		return session;
	}
	/***
	 * Every session is mapping to a Http connection and a Plurk User.
	 * You can use your Http request Object.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession(PlurkHttp plurkHttpAdapter) {
		PlurkSession session = new PlurkSession(plurkHttpAdapter);
		session.init();
		return session;
	}
	/***
	 * The same with createSession, but you can use your API_KEY for this session.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession(String api_key) {
		PlurkSession session = PlurkFactory.createSession();
		session.setApi_key(api_key);
		return session;
	}
	/***
	 * The same with createSession, but you can use your API_KEY for this session.
	 * And you can use your Http request Object.
	 * @return PlurkSession
	 */
	public static PlurkSession createSession(String api_key,PlurkHttp plurkHttpAdapter) {
		PlurkSession session = PlurkFactory.createSession(plurkHttpAdapter);
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
		if(session == null) return false;
		if(username == null || password == null) {
			session.setError("username or password is null");
			return false;
		}
		String result = null;
		session.setLogin(true);
		try {
//			result = session.execute(PLURK_URI + "/Users/login?api_key="
//					+ PlurkFactory.getApi_key(session) + "&username=" + username + "&password=" + password);
			result = PlurkFactory.execute(session,"/API/Users/login","username",username,"password",password);
		} catch (Exception e) {
			session.setLogin(false);
			session.setError("LOGIN FAILURE");
			e.printStackTrace();
			return false;
		}
		
		if(result != null) {
			try {
				JsonProfile ownProfile = json.readValue(result, JsonProfile.class);
				session.setProfile(new PlurkProfile(ownProfile));
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
	 * @param user PlurkUser
	 * @return PlurkProfile
	 */
	public static PlurkProfile getPlurkProfile(PlurkSession session,PlurkUser user) {
		if(session == null || session.isLogin() == false) return null;
		if(user == null) return null;
		
		JsonProfile profile = getJsonProfile(session,user.getId());
		if(profile != null) {
			PlurkProfile plurkProfile = new PlurkProfile(profile);
			PlurkPool.addPlurkUser(plurkProfile.getPlurkUser());
			return plurkProfile;
		}
		else
			return null;
	}
	/***
	 * Get other's profile
	 * @param session PlurkSession
	 * @param user_id user_id
	 * @return PlurkProfile
	 */
	private static JsonProfile getJsonProfile(PlurkSession session,Long user_id) {
		if(user_id == null) {
			session.setError("user_id is null");
			return null;
		}
		String result = null;
		try {
//			result = session.execute(PLURK_URI + "/Profile/getPublicProfile?api_key="
//					+ PlurkFactory.getApi_key(session) + "&user_id=" + user_id);
			result = PlurkFactory.execute(session,"/API/Profile/getPublicProfile","user_id",user_id);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}

		if(result != null) {
			try {
				return json.readValue(result, JsonProfile.class);
			} catch (Exception e) {
				session.setError(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	/***
	 * Get plurks 
	 * @param session PlurkSession
	 * @param offset Return plurks older than offset
	 * @param limit How many plurks should be returned? Default is 20. 
	 * @param onlyUser The numeric ID of the user who's plurks should be returned. 
	 * @param onlyResponded Setting it to true will only return responded plurks 
	 * @param onlyPrivate Setting it to true will only return private plurks 
	 * @return
	 */
	public static List<Plurk> getPlurks(PlurkSession session, Date offset, Long limit
			,PlurkUser onlyUser,Boolean onlyResponded,Boolean onlyPrivate) {
		if(session == null || session.isLogin() == false) return null;
		JsonPlurks jsonPlurks = getJsonPlurks(session,getDateString(offset),limit,(onlyUser != null ? onlyUser.getId() : null),onlyResponded,onlyPrivate);
		if(jsonPlurks != null) {
			if(jsonPlurks.plurks != null && jsonPlurks.plurks.size() > 0 
					&& jsonPlurks.plurk_users != null && jsonPlurks.plurk_users.size() > 0) {
				List<Plurk> plurks = new ArrayList<Plurk>();
				for(JsonPlurk jsonPlurk:jsonPlurks.plurks) {
					Plurk plurk = new Plurk(jsonPlurk);
					JsonUser plurkUser = jsonPlurks.plurk_users.get("" + jsonPlurk.owner_id);
					JsonUser plurkOwner = jsonPlurks.plurk_users.get("" + jsonPlurk.user_id);
					if(plurkUser != null) plurk.setPlurkUser(PlurkPool.getPlurkUser(plurkUser));
					if(plurkOwner != null) plurk.setPlurkOwner(PlurkPool.getPlurkUser(plurkOwner));
					plurks.add(plurk);
				}
				return plurks;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	/***
	 * Get plurks about this session user
	 * @param session PlurkSession
	 * @return plurks
	 */
	public static List<Plurk> getPlurks(PlurkSession session) {
		if(session == null || session.isLogin() == false) return null;
		return PlurkFactory.getPlurks(session,null,null,null,null,null);
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
	private static JsonPlurks getJsonPlurks(PlurkSession session, String offset, Long limit
			,Long only_user,Boolean only_responded,Boolean only_private) {
//		StringBuilder options = new StringBuilder();
//		if(offset != null) options.append("&offset=" + offset);
//		if(limit != null) options.append("&limit=" + limit);
//		if(only_user != null) options.append("&only_user=" + only_user);
//		if(only_responded != null) options.append("&only_responded=" + only_responded);
//		if(only_private != null) options.append("&only_private=" + only_private);
		
		String result = null;
		try {
//			result = session.execute(PLURK_URI + "/Timeline/getPlurks?api_key="
//					+ PlurkFactory.getApi_key(session) + options);
			result = PlurkFactory.execute(session,"/API/Timeline/getPlurks"
					,"offset",offset
					,"limit",limit
					,"only_user",only_user
					,"only_responded",only_responded
					,"only_private",only_private);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		if(result != null) {
			try {
				JsonPlurks plurks = json.readValue(result, JsonPlurks.class);
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
	 * @param plurk The plurk that the responses should be added to. 
	 * @return Plurk Responses
	 */
	public static List<Plurk> getResponses(PlurkSession session, Plurk plurk) {
		if(session == null || session.isLogin() == false) return null;
		if(plurk == null) return null;
		JsonResponses responses = PlurkFactory.getJsonResponses(session,plurk.getId(),0L);
		if(responses != null && responses.responses != null) {
			List<Plurk> plurks = new ArrayList<Plurk>();
			for(JsonPlurk jsonPlurk:responses.responses) {
				Plurk newplurk = new Plurk(jsonPlurk);
				JsonUser plurkUser = responses.friends.get("" + jsonPlurk.owner_id);
				JsonUser plurkOwner = responses.friends.get("" + jsonPlurk.user_id);
				if(plurkUser != null) newplurk.setPlurkUser(PlurkPool.getPlurkUser(plurkUser));
				if(plurkOwner != null) newplurk.setPlurkOwner(PlurkPool.getPlurkUser(plurkOwner));
				plurks.add(newplurk);
			}
			return plurks;
		}
		else
			return null;
	}
	/***
	 * Get Plurk Responses
	 * @param session PlurkSession
	 * @param plurk_id The plurk that the responses should be added to. 
	 * @param from_response Only fetch responses from an offset, should be 5, 10 or 15. 
	 * @return
	 */
	private static JsonResponses getJsonResponses(PlurkSession session, Long plurk_id, Long from_response) {
		String result = null;
		try {
//			result = session.execute(PLURK_URI + "/Responses/get?api_key="
//					+ PlurkFactory.getApi_key(session) + "&plurk_id=" + plurk_id
//					+ "&from_response=" + from_response);
			result = PlurkFactory.execute(session,"/API/Responses/get"
					,"plurk_id",plurk_id
					,"from_response",from_response);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, JsonResponses.class);
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
	 * @param plurk The plurk that the responses should be added to. 
	 * @param response fill content and qualifier in response
	 * @return Plurk Response
	 */
	public static Plurk addResponse(PlurkSession session, Plurk plurk, Plurk response) {
		if(session == null || session.isLogin() == false) return null;
		if(plurk == null) return null;
		if(response == null) return null;
		JsonPlurk jsonPlurk = addJsonResponse(session, plurk.getId(), response.getContent(), response.getQualifier());
		if(jsonPlurk != null)
			return PlurkPool.getPlurk(jsonPlurk);
		else
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
	private static JsonPlurk addJsonResponse(PlurkSession session, Long plurk_id, String content, PlurkQualifier qualifier) {
		String result = null;
		try {
//			result = session.execute(PLURK_URI + "/Responses/responseAdd?api_key="
//					+ PlurkFactory.getApi_key(session) + "&plurk_id=" + plurk_id
//					+ "&content=" + content + "&qualifier=" + qualifier.toString());
			result = PlurkFactory.execute(session,"/API/Responses/responseAdd"
					,"plurk_id",plurk_id
					,"content",content
					,"qualifier",qualifier);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, JsonPlurk.class);
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
	 * @param plurk fill content and qualifier in Plurk
	 * @return
	 */
	public static Plurk addPlurk(PlurkSession session, Plurk plurk) {
		if(session == null || session.isLogin() == false) return null;
		if(plurk == null) return null;
		JsonPlurk jsonPlurk = addJsonPlurk(session, plurk.getContent(), plurk.getQualifier());
		if(jsonPlurk != null)
			return PlurkPool.getPlurk(jsonPlurk);
		else
			return null;
	}
	/***
	 * Add Plurk
	 * @param session PlurkSession
	 * @param content The Plurk's text.  
	 * @param qualifier The Plurk's qualifier, must be in English. [loves,likes,shares,gives,hates,wants,has,will,asks,wishes,was,feels,thinks,says,is,:,freestyle,hopes,needs,wonders]
	 * @return
	 */
	private static JsonPlurk addJsonPlurk(PlurkSession session, String content, PlurkQualifier qualifier) {
		String result = null;
		try {
//			result = session.execute(PLURK_URI + "/Timeline/plurkAdd?api_key="
//					+ PlurkFactory.getApi_key(session)
//					+ "&content=" + content + "&qualifier=" + qualifier.toString());
			result = PlurkFactory.execute(session,"/API/Timeline/plurkAdd"
					,"content",content
					,"qualifier",qualifier);
		} catch (Exception e) {
			session.setError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(result != null) {
			try {
				return json.readValue(result, JsonPlurk.class);
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

	/***
	 * Change your own API KEY for every session
	 * You can also use different API KEY for every session by PlurkFactory.createSession(String api_key)
	 * @param api_key
	 */
	public static void setApi_key(String api_key) {
		PlurkFactory.api_key = api_key;
	}
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss");
	private static String getDateString(Date date) {
		if(date == null) return null;
		try{
			return dateFormatter.format(date).replaceFirst("--", "T");
		}catch(Exception e) {
			return null;
		}
	}
	private static SimpleDateFormat dateParser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ",Locale.ENGLISH);
	/***
	 * Parse Date String to date. ex: Tue, 22 Dec 2009 08:30:18 GMT 
	 * @param dateString
	 * @return
	 */
	public static Date getDate(String dateString) {
		if(dateString == null) return null;
		try {
			return dateParser.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	private static String execute(PlurkSession session, String urlAPI, Object... params) throws Exception {
		StringBuilder url = new StringBuilder();
		url.append(urlAPI);
		url.append("?api_key=" + PlurkFactory.getApi_key(session));
		if(params != null && params.length > 0) {
			for(int i=0;i < params.length;i++) {
				if(params[i] != null && params[i].toString() != null 
						&& params[i+1] != null && params[i+1].toString() != null) {
					url.append("&" + URLEncoder.encode(params[i].toString(),"UTF-8"));
					url.append("=" + URLEncoder.encode(params[i+1].toString(), "UTF-8"));
				}
				i++;
			}
		}
		return session.execute(PLURK_URI + url.toString());
	}
	/***
	 * Clear PlurkPool cache
	 */
	public static void clear() {
		PlurkPool.clear();
	}
}

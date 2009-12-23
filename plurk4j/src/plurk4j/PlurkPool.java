package plurk4j;

import java.util.HashMap;
import java.util.Map;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkUser;
import plurk4j.official.JsonPlurk;
import plurk4j.official.JsonUser;

/***
 * Plurk Pool
 * for cache Plurk and Plurk User.
 * 
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class PlurkPool {
	private PlurkPool() {}
	private static Map<String, Plurk> plurks = new HashMap<String, Plurk>();
	private static Map<String, PlurkUser> plurkUsers = new HashMap<String, PlurkUser>();
	
	static void flush() {
		plurks = new HashMap<String, Plurk>();
		plurkUsers = new HashMap<String, PlurkUser>();
	}
	
	static Plurk getPlurk(JsonPlurk jsonPlurk) {
		if(jsonPlurk == null) return null;
		else if(plurks.get("" + jsonPlurk.id) != null)
			return plurks.get("" + jsonPlurk.id);
		else if(plurks.get("" + jsonPlurk.plurk_id) != null)
			return plurks.get("" + jsonPlurk.plurk_id);
		else {
			Plurk plurk = new Plurk(jsonPlurk);
			addPlurk(plurk);
			return plurk;
		}
	}
	static PlurkUser getPlurkUser(JsonUser jsonUser) {
		if(jsonUser == null) return null;
		else if(plurkUsers.get("" + jsonUser.id) != null)
			return plurkUsers.get("" + jsonUser.id);
		else if(plurkUsers.get("" + jsonUser.uid) != null)
			return plurkUsers.get("" + jsonUser.uid);
		else {
			PlurkUser plurkUser = new PlurkUser(jsonUser);
			addPlurkUser(plurkUser);
			return plurkUser;
		}
	}
	static void addPlurk(Plurk plurk) {
		if(plurk != null && plurk.getId() != null)
			plurks.put("" + plurk.getId(), plurk);
	}
	static void addPlurkUser(PlurkUser plurkUser) {
		if(plurkUser != null && plurkUser.getId() != null)
			plurkUsers.put("" + plurkUser.getId(), plurkUser);
	}
	
	public static Plurk getPlurk(Long id) {
		return plurks.get("" + id);
	}
	public static PlurkUser getPlurkUser(Long id) {
		return plurkUsers.get("" + id);
	}
	
	static Plurk getPlurk(Plurk plurk) {
		if(plurk == null) return null;
		else if(plurks.get("" +  plurk.getId()) != null)
			return plurks.get("" +  plurk.getId());
		else
			return null;
	}
	
	static PlurkUser getPlurkUser(PlurkUser plurkUser) {
		if(plurkUser == null) return null;
		else if(plurkUsers.get("" +  plurkUser.getId()) != null)
			return plurkUsers.get("" +  plurkUser.getId());
		else
			return null;
	}
	
}

package plurk4j.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkUser;

public class Plurks {
	private Map<String,PlurkUser> plurk_users;
	private List<Plurk> plurks;
	
	public void print() {
		print("");
	}
	public void print(String offset) {
		System.out.println(offset + "--------------------Plurks-----------------------");
		if(plurks != null)
			for(Plurk plurk:plurks)
				plurk.print("    ");
		if(plurk_users != null) {
			Iterator userKeys = plurk_users.keySet().iterator();
			while(userKeys.hasNext()) {
				PlurkUser plurk_user = plurk_users.get(userKeys.next());
				plurk_user.print("    ");
			}
		}
		
	}
	public Map<String, PlurkUser> getPlurk_users() {
		return plurk_users;
	}
	public void setPlurk_users(Map<String, PlurkUser> plurk_users) {
		this.plurk_users = plurk_users;
	}
	public List<Plurk> getPlurks() {
		return plurks;
	}
	public void setPlurks(List<Plurk> plurks) {
		this.plurks = plurks;
	}	
	
	
}

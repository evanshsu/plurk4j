package plurk4j.json;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkUser;

/***
 * PlurkProfile Entity
 * It's mapping to Plurk JSON message.
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.0.1
 */
public class PlurkProfile {
	private Long friends_count;
	private Long fans_count;
	private Long alerts_count;
	private Long unread_count;
	private PlurkUser user_info;
	private List<Plurk> plurks;	
	private Map<String,PlurkUser> plurks_users;
	private String privacy;
	private Boolean has_read_permission;
	private Boolean are_friends; //null if not logged in
	private Boolean is_fan; //null if not logged in
	private Boolean is_following; //null if not logged in
	
	public void print() {
		print("");
	}
	public void print(String offset) {
		System.out.println(offset + "--------------------PlurkProfile-----------------------");
		System.out.print(offset + "unread_count=" + unread_count);
		System.out.print(offset + ",alerts_count=" + alerts_count);
		System.out.print(offset + ",fans_count=" + fans_count);
		System.out.println(offset + ",friends_count=" + friends_count);
		if(user_info != null) {
			user_info.print("");
		}
		if(plurks != null)
			for(Plurk plurk:plurks)
				plurk.print("    ");
		if(plurks_users != null) {
			Iterator userKeys = plurks_users.keySet().iterator();
			while(userKeys.hasNext()) {
				PlurkUser plurk_user = plurks_users.get(userKeys.next());
				plurk_user.print("    ");
			}
		}
		
	}

	public Long getFans_count() {
		return fans_count;
	}
	public void setFans_count(Long fans_count) {
		this.fans_count = fans_count;
	}
	public Long getAlerts_count() {
		return alerts_count;
	}
	public void setAlerts_count(Long alerts_count) {
		this.alerts_count = alerts_count;
	}
	public Map<String, PlurkUser> getPlurks_users() {
		return plurks_users;
	}
	public void setPlurks_users(Map<String, PlurkUser> plurks_users) {
		this.plurks_users = plurks_users;
	}
	public Long getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(Long friends_count) {
		this.friends_count = friends_count;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public Boolean getHas_read_permission() {
		return has_read_permission;
	}
	public void setHas_read_permission(Boolean has_read_permission) {
		this.has_read_permission = has_read_permission;
	}
	public PlurkUser getUser_info() {
		return user_info;
	}
	public void setUser_info(PlurkUser user_info) {
		this.user_info = user_info;
	}
	public List<Plurk> getPlurks() {
		return plurks;
	}
	public void setPlurks(List<Plurk> plurks) {
		this.plurks = plurks;
	}
	public Long getUnread_count() {
		return unread_count;
	}
	public void setUnread_count(Long unread_count) {
		this.unread_count = unread_count;
	}
	public Boolean getAre_friends() {
		return are_friends;
	}
	public void setAre_friends(Boolean are_friends) {
		this.are_friends = are_friends;
	}
	public Boolean getIs_fan() {
		return is_fan;
	}
	public void setIs_fan(Boolean is_fan) {
		this.is_fan = is_fan;
	}
	public Boolean getIs_following() {
		return is_following;
	}
	public void setIs_following(Boolean is_following) {
		this.is_following = is_following;
	}
}
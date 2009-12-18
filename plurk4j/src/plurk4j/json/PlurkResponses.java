package plurk4j.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkUser;

public class PlurkResponses {
	private Map<String,PlurkUser> friends;
	private List<Plurk> responses;
	private Long responses_seen;
	
	public void print() {
		print("");
	}
	public void print(String offset) {
		System.out.println(offset + "--------------------PlurkResponses-----------------------");
		if(responses != null)
			for(Plurk plurk:responses)
				plurk.print("    ");
		if(friends != null) {
			Iterator userKeys = friends.keySet().iterator();
			while(userKeys.hasNext()) {
				PlurkUser plurk_user = friends.get(userKeys.next());
				plurk_user.print("    ");
			}
		}

	}
	
	public Map<String, PlurkUser> getFriends() {
		return friends;
	}
	public void setFriends(Map<String, PlurkUser> friends) {
		this.friends = friends;
	}
	public List<Plurk> getResponses() {
		return responses;
	}
	public void setResponses(List<Plurk> responses) {
		this.responses = responses;
	}
	public Long getResponses_seen() {
		return responses_seen;
	}
	public void setResponses_seen(Long responses_seen) {
		this.responses_seen = responses_seen;
	}
}

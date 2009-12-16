package plurk4j.entity;

import java.util.ArrayList;
import java.util.List;

public class PlurkUser {
	public Long uid;
	public Long id; // The unique user id.
	public String nick_name; // The unique nick_name of the user, for example amix.
	public String display_name; // The non-unique display name of the user, for example Amir S. Only set if it's non empty.
	public String has_profile_image; // If 1 then the user has a profile picture, otherwise the user should use the default.
	public String avatar; // Specifies what the latest avatar (profile picture) version is.
	public String location; // The user's location, a text string, for example Aarhus Denmark.
	public String date_of_birth; // The user's birthday.
	public String full_name; // The user's full name, like Amir Salihefendic.
	public Integer gender; // 1 is male, 0 is female.
	public String page_title; // The profile title of the user.
	public Float karma; // User's karma value.
	public Long recruited; // How many friends has the user recruited.
	public String relationship; // Can be not_saying, single, married, divorced, engaged, in_relationship, complicated, widowed, open_relationship
	public String timezone;
	
	private List<Plurk> plurks;
	
	public List<Plurk> getPlurks() {
		return plurks;
	}
	public void setPlurks(List<Plurk> plurks) {
		this.plurks = plurks;
	}
	public void addPlurk(Plurk plurk) {
		if(this.plurks == null)
			this.plurks = new ArrayList<Plurk>();
		
		this.plurks.add(plurk);
	}
	
	public void print() {
		print("");
	}
	public void print(String offset) {
		System.out.println(offset + "----------------------PlurkUser---------------------");
		System.out.print(offset + "id=" + id);
		System.out.print(",nick_name=" + nick_name);
		System.out.print(",karma=" + karma);
		System.out.println(",gender=" + gender);
	}
}

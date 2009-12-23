package plurk4j.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import plurk4j.PlurkFactory;
import plurk4j.PlurkPool;
import plurk4j.entity.PlurkEnum.PlurkGender;
import plurk4j.entity.PlurkEnum.PlurkRelationship;
import plurk4j.official.JsonUser;

/***
 * PlurkUser Entity
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class PlurkUser implements Serializable{
	private static final long serialVersionUID = -1517504125234725132L;
	private Long id;
	private String nickname;
	private Float karma;
	private String displayName;
	private String fullName;
	private String smallImageUrl;
	private String mediumImageUrl;
	private String bigImageUrl;
	private String location;
	private Date dateOfBirth;
	private PlurkGender gender;
	private String pageTitle;
	private PlurkRelationship relationship;
	private String timezone;
	
	private List<Plurk> plurks;	

	private JsonUser jsonUser;
	
	public JsonUser getJsonUser() {
		return jsonUser;
	}
	public void setJsonUser(JsonUser jsonUser) {
		if(jsonUser == null) return;
		this.jsonUser = jsonUser;
		if(jsonUser.id != null) setId(jsonUser.id);
		if(jsonUser.uid != null) setId(jsonUser.uid);
		setNickname(jsonUser.nick_name);
		setKarma(jsonUser.karma);
		setDisplayName(jsonUser.display_name);
		setFullName(jsonUser.full_name);
		setImageUrls(jsonUser.has_profile_image,jsonUser.avatar);
		setLocation(jsonUser.location);
		setDateOfBirth(jsonUser.date_of_birth);
		setGender(jsonUser.gender);
		setPageTitle(jsonUser.page_title);
		setRelationship(jsonUser.relationship);
		setTimezone(jsonUser.timezone);
	}
	
	public PlurkUser() {}
	public PlurkUser(Long id) {
		this.id = id;
	}
	public PlurkUser(JsonUser jsonUser) {
		this.setJsonUser(jsonUser);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Float getKarma() {
		return karma;
	}
	public void setKarma(Float karma) {
		this.karma = karma;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setImageUrls(String has_profile_image,String avatar) {
		if("1".equals(has_profile_image) && avatar == null) {
			this.smallImageUrl = "http://avatars.plurk.com/" + getId() + "-small.gif";
			this.mediumImageUrl = "http://avatars.plurk.com/" + getId() + "-medium.gif";
			this.bigImageUrl = "http://avatars.plurk.com/" + getId() + "-big.jpg";
		}
		else if("1".equals(has_profile_image) && avatar != null) {
			this.smallImageUrl = "http://avatars.plurk.com/" + getId() + "-small" + avatar + ".gif";
			this.mediumImageUrl = "http://avatars.plurk.com/" + getId() + "-medium" + avatar + ".gif";
			this.bigImageUrl = "http://avatars.plurk.com/" + getId() + "-big" + avatar + ".jpg";
		}
		else {
			this.smallImageUrl = "http://www.plurk.com/static/default_small.gif";
			this.mediumImageUrl = "http://www.plurk.com/static/default_medium.gif";
			this.bigImageUrl = "http://www.plurk.com/static/default_big.gif ";
		}
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String date) {
		this.dateOfBirth = PlurkFactory.getDate(date);
	}
	/***
	 * Gender: 1 is male, 0 is female.
	 * @return Gender: 1 is male, 0 is female.
	 */
	public PlurkGender getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		for(PlurkGender plurkGender:PlurkGender.values()) {
			if(plurkGender.getSource().equals(gender)) {
				this.gender = plurkGender;
				break;
			}
		}
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	/***
	 * not_saying, single, married, divorced, engaged, in_relationship, complicated, widowed, open_relationship
	 * @return not_saying, single, married, divorced, engaged, in_relationship, complicated, widowed, open_relationship
	 */
	public PlurkRelationship getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		for(PlurkRelationship plurkRelationship:PlurkRelationship.values()) {
			if(plurkRelationship.toString().equals(relationship)) {
				this.relationship = plurkRelationship;
				break;
			}
		}
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getSmallImageUrl() {
		return smallImageUrl;
	}
	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}
	public String getMediumImageUrl() {
		return mediumImageUrl;
	}
	public void setMediumImageUrl(String mediumImageUrl) {
		this.mediumImageUrl = mediumImageUrl;
	}
	public String getBigImageUrl() {
		return bigImageUrl;
	}
	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}
	public List<Plurk> getPlurks() {
		return plurks;
	}
	public void setPlurks(List<Plurk> plurks) {
		this.plurks = null;
		for(Plurk plurk:plurks) {
			this.addPlurk(plurk);
		}
	}
	public void addPlurk(Plurk plurk) {
		if(plurk == null) return;
		if(plurks == null) plurks = new ArrayList<Plurk>();
		if(plurk.getPlurkOwner() == null) {
			if(this.id != null && this.id.equals(plurk.getOwnerId()))
				plurk.setPlurkOwner(this);
			else if(plurk.getOwnerId() != null)
				plurk.setPlurkOwner(PlurkPool.getPlurkUser(plurk.getOwnerId()));
		}
		if(plurk.getPlurkUser() == null) {
			if(this.id != null && this.id.equals(plurk.getUserId()))
				plurk.setPlurkUser(this);
			else if(plurk.getUserId() != null)
				plurk.setPlurkUser(PlurkPool.getPlurkUser(plurk.getUserId()));
		}
		plurks.add(plurk);
	}
}

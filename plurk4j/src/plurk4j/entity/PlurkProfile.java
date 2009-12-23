package plurk4j.entity;

import java.io.Serializable;

import plurk4j.entity.PlurkEnum.PlurkPrivacy;
import plurk4j.official.JsonProfile;

/***
 * PlurkProfile Entity
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class PlurkProfile implements Serializable{
	private static final long serialVersionUID = 6736003901292737109L;
	private Long friendsCount;
	private Long fansCount;
	private Long alertsCount;
	private Long unreadCount;
	private PlurkPrivacy privacy; //privacy: User's privacy settings. The option can be world (whole world can view the profile), only_friends (only friends can view the profile) or only_me (only the user can view own plurks).
	private Boolean hasReadPermission;
	private Boolean areFriends;
	private Boolean isFan;
	private Boolean isFollowing;
	
	private PlurkUser plurkUser;
	public PlurkUser getPlurkUser() {
		return plurkUser;
	}
	public void setPlurkUser(PlurkUser plurkUser) {
		this.plurkUser = plurkUser;
	}
	
	private JsonProfile jsonProfile;
	public JsonProfile getJsonProfile() {
		return jsonProfile;
	}
	public void setJsonProfile(JsonProfile jsonProfile) {
		if(jsonProfile == null) return;
		this.jsonProfile = jsonProfile;
		if(jsonProfile.user_info != null)
			setPlurkUser(new PlurkUser(jsonProfile.user_info));
		setFriendsCount(jsonProfile.friends_count);
		setFansCount(jsonProfile.fans_count);
		setAlertsCount(jsonProfile.alerts_count);
		setUnreadCount(jsonProfile.unread_count);
		setPrivacy(jsonProfile.privacy);
		setHasReadPermission(jsonProfile.has_read_permission);
		setAreFriends(jsonProfile.are_friends);
		setIsFan(jsonProfile.is_fan);
		setIsFollowing(jsonProfile.is_following);
	}
	
	public PlurkProfile() {}
	public PlurkProfile(JsonProfile jsonProfile) {
		this.setJsonProfile(jsonProfile);
	}

	public Long getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(Long friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Long getFansCount() {
		return fansCount;
	}
	public void setFansCount(Long fansCount) {
		this.fansCount = fansCount;
	}
	public Long getAlertsCount() {
		return alertsCount;
	}
	public void setAlertsCount(Long alertsCount) {
		this.alertsCount = alertsCount;
	}
	public Long getUnreadCount() {
		return unreadCount;
	}
	public void setUnreadCount(Long unreadCount) {
		this.unreadCount = unreadCount;
	}
	public PlurkPrivacy getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		for(PlurkPrivacy plurkPrivacy:PlurkPrivacy.values()) {
			if(plurkPrivacy.toString().equals(privacy)) {
				this.privacy = plurkPrivacy;
				break;
			}
		}
	}
	public Boolean getHasReadPermission() {
		return hasReadPermission;
	}
	public void setHasReadPermission(Boolean hasReadPermission) {
		this.hasReadPermission = hasReadPermission;
	}
	public Boolean getAreFriends() {
		return areFriends;
	}
	public void setAreFriends(Boolean areFriends) {
		this.areFriends = areFriends;
	}
	public Boolean getIsFan() {
		return isFan;
	}
	public void setIsFan(Boolean isFan) {
		this.isFan = isFan;
	}
	public Boolean getIsFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	
	
}

package plurk4j.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import plurk4j.PlurkFactory;
import plurk4j.entity.PlurkEnum.PlurkIsUnread;
import plurk4j.entity.PlurkEnum.PlurkNoComments;
import plurk4j.entity.PlurkEnum.PlurkQualifier;
import plurk4j.entity.PlurkEnum.PlurkType;
import plurk4j.official.JsonPlurk;

/***
 * Plurk Entity
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class Plurk implements Serializable{
	private static final long serialVersionUID = 5836632618014654785L;
	private Long id;
	private PlurkQualifier qualifier;
    private String qualifierTranslated; 
    private PlurkIsUnread isUnread;
    private PlurkType plurkType;
    private Long userId; 
    private Long ownerId;
    private PlurkUser plurkUser;
    private PlurkUser plurkOwner;
    private Date posted; 
    private PlurkNoComments noComments; 
    private String content; 
    private String contentRaw; 
    private Long responseCount; 
    private Long responsesSeen; 
    private String lang;
	private String limitedTo;
	
	private Plurk parent;
	private List<Plurk> responses;
	
	private JsonPlurk jsonPlurk;
	public JsonPlurk getJsonPlurk() {
		return jsonPlurk;
	}
	public void setJsonPlurk(JsonPlurk jsonPlurk) {
		if(jsonPlurk == null) return;
		this.jsonPlurk = jsonPlurk;
		if(jsonPlurk.id != null) setId(jsonPlurk.id);
		if(jsonPlurk.plurk_id != null) setId(jsonPlurk.plurk_id);
		setQualifier(jsonPlurk.qualifier);
		setQualifierTranslated(jsonPlurk.qualifier_translated);
		setIsUnread(jsonPlurk.is_unread);
		setPlurkType(jsonPlurk.plurk_type);
		setUserId(jsonPlurk.user_id);
		setOwnerId(jsonPlurk.owner_id);
		setPosted(jsonPlurk.posted);
		setNoComments(jsonPlurk.no_comments);
		setContent(jsonPlurk.content);
		setContentRaw(jsonPlurk.content_raw);
		setResponseCount(jsonPlurk.response_count);
		setResponsesSeen(jsonPlurk.responses_seen);
		setLang(jsonPlurk.lang);
		setLimitedTo(jsonPlurk.limited_to);
	}
	
	public Plurk() {}
	public Plurk(Long id) {
		this.id = id;
	}
	public Plurk(String content, PlurkQualifier qualifier) {
		this.content = content;
		this.qualifier = qualifier;
	}
	public Plurk(String content) {
		this.content = content;
		this.qualifier = PlurkQualifier.SAYS;
	}
	public Plurk(JsonPlurk jsonPlurk) {
		this.setJsonPlurk(jsonPlurk);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PlurkQualifier getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		for(PlurkQualifier plurkQualifier:PlurkQualifier.values()) {
			if(plurkQualifier.toString().equals(qualifier)) {
				this.qualifier = plurkQualifier;
				break;
			}
		}
	}
	public String getQualifierTranslated() {
		return qualifierTranslated;
	}
	public void setQualifierTranslated(String qualifierTranslated) {
		this.qualifierTranslated = qualifierTranslated;
	}
	public PlurkIsUnread getIsUnread() {
		return isUnread;
	}
	public void setIsUnread(Integer isUnread) {
		for(PlurkIsUnread plurkIsUnread:PlurkIsUnread.values()) {
			if(plurkIsUnread.getSource().equals(isUnread)) {
				this.isUnread = plurkIsUnread;
				break;
			}
		}
	}
	public PlurkType getPlurkType() {
		return plurkType;
	}
	public void setPlurkType(Integer plurkType) {
		for(PlurkType plurkTypes:PlurkType.values()) {
			if(plurkTypes.getSource().equals(plurkType)) {
				this.plurkType = plurkTypes;
				break;
			}
		}
	}
	public PlurkNoComments getNoComments() {
		return noComments;
	}
	public void setNoComments(Integer noComments) {
		for(PlurkNoComments plurkNoComments:PlurkNoComments.values()) {
			if(plurkNoComments.getSource().equals(noComments)) {
				this.noComments = plurkNoComments;
				break;
			}
		}
	}
	public Date getPosted() {
		return posted;
	}
	public void setPosted(String dateString) {
		this.posted = PlurkFactory.getDate(dateString);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentRaw() {
		return contentRaw;
	}
	public void setContentRaw(String contentRaw) {
		this.contentRaw = contentRaw;
	}
	public Long getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(Long responseCount) {
		this.responseCount = responseCount;
	}
	public Long getResponsesSeen() {
		return responsesSeen;
	}
	public void setResponsesSeen(Long responsesSeen) {
		this.responsesSeen = responsesSeen;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getLimitedTo() {
		return limitedTo;
	}
	public void setLimitedTo(String limitedTo) {
		this.limitedTo = limitedTo;
	}
	public PlurkUser getPlurkOwner() {
		return plurkOwner;
	}
	public void setPlurkOwner(PlurkUser plurkOwner) {
		this.plurkOwner = plurkOwner;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public PlurkUser getPlurkUser() {
		return plurkUser;
	}
	public void setPlurkUser(PlurkUser plurkUser) {
		this.plurkUser = plurkUser;
	}
	public Plurk getParent() {
		return parent;
	}
	public void setParent(Plurk parent) {
		this.parent = parent;
	}
	public List<Plurk> getResponses() {
		return responses;
	}
	public void setResponses(List<Plurk> responses) {
		this.responses = responses;
	}
}

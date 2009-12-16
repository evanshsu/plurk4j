package plurk4j.entity;

public class Plurk {
    public Long plurk_id; // The unique Plurk id, used for identification of the plurk. 
    public String qualifier; // The English qualifier, can be "says", [loves,likes,shares,gives,hates,wants,has,will,asks,wishes,was,feels,thinks,says,is,:,freestyle,hopes,needs,wonders]
    public String qualifier_translated; // Only set if the language is not English, will be the translated qualifier. Can be "siger" if plurk.lang is "da" (Danish). 
    public Integer is_unread; // Specifies if the plurk is read, unread or muted. Show example data
//  is_unread=0 //Read
//  is_unread=1 //Unread
//  is_unread=2 //Muted
    public Integer plurk_type; // Specifies what type of plurk it is and if the plurk has been responded by the user. The value of plurk_type is only correct when calling getPlurks with only_responded filter (this is done for perfomance and caching reasons). Show example data
//    plurk_type=0 //Plublic plurk
//    plurk_type=1 //Private plurk
//    plurk_type=2 //Plublic plurk (responded by the logged in user)
//    plurk_type=3 //Private plurk (responded by the logged in user)
    public Long user_id; // Which timeline does this Plurk belong to. 
    public Long owner_id; // Who is the owner/poster of this plurk. 
    public String posted; // The date this plurk was posted. 
    public Integer no_comments; // If set to 1, then responses are disabled for this plurk.
    						   //If set to 2, then only friends can respond to this plurk. 
    public String content; // The formatted content, emoticons and images will be turned into IMG tags etc. 
    public String content_raw; // The raw content as user entered it, useful when editing plurks or if you want to format the content differently. 
    public Long response_count; // How many responses does the plurk have. 
    public Long responses_seen; // How many of the responses have the user read. This is automatically updated when fetching responses or marking a plurk as read. 
    public String lang; // ['en','pt_BR','cn','ca','el','dk','de','es','sv','nb','hi','ro','hr','fr','ru','it','ja','he','hu','ne','th','ta_fp','in','pl','ar','fi','tr_ch','tr','ga','sk','uk','fa']
	public String limited_to; // Limit the plurk only to some users (also known as private plurking). limited_to should be a JSON list of friend ids, e.g. limited_to of [3,4,66,34] will only be plurked to these user ids.
	private PlurkUser plurkUser;
	
	public PlurkUser getPlurkUser() {
		return plurkUser;
	}
	public void setPlurkUser(PlurkUser plurkUser) {
		this.plurkUser = plurkUser;
	}
	
	public void print() {
		print("");
	}
	public void print(String offset) {
		System.out.println(offset + "--------------------Plurk-----------------------");
		System.out.print(offset + "plurk_id=" + plurk_id);
		System.out.print(",owner_id=" + owner_id);
		System.out.print(",posted=" + posted);
		System.out.println(",content=" + content);
	}
}

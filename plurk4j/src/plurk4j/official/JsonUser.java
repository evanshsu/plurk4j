package plurk4j.official;


/***
 * JsonUser
 * It's mapping to Plurk Official API JSON message.
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class JsonUser {
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
}

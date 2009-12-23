package plurk4j.official;
import java.util.List;
import java.util.Map;


/***
 * JsonProfile
 * It's mapping to Plurk Official API JSON message.
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class JsonProfile {
	public Long friends_count;
	public Long fans_count;
	public Long alerts_count;
	public Long unread_count;
	public JsonUser user_info;
	public List<JsonPlurk> plurks;	
	public Map<String,JsonUser> plurks_users;
	public String privacy; //privacy: User's privacy settings. The option can be world (whole world can view the profile), only_friends (only friends can view the profile) or only_me (only the user can view own plurks).
	public Boolean has_read_permission;
	public Boolean are_friends; //null if not logged in
	public Boolean is_fan; //null if not logged in
	public Boolean is_following; //null if not logged in
}
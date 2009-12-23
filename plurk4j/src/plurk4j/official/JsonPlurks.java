package plurk4j.official;

import java.util.List;
import java.util.Map;


/***
 * JsonPlurks
 * It's mapping to Plurk Official API JSON message.
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class JsonPlurks {
	public Map<String,JsonUser> plurk_users;
	public List<JsonPlurk> plurks;
}

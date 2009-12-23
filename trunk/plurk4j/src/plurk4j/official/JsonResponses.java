package plurk4j.official;

import java.util.List;
import java.util.Map;

/***
 * JsonResponses
 * It's mapping to Plurk Official API JSON message.
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class JsonResponses {
	public Map<String,JsonUser> friends;
	public List<JsonPlurk> responses;
	public Long responses_seen;
}

package plurk4j;

/***
 * Plurk Http Request Interface
 * You can use your Http Request Object by implment this interface, 
 * and call this method PlurkFactory.createSession(PlurkHttp) when create session.
 * 
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public interface PlurkHttp {
	public boolean init();
	public String execute(String url) throws Exception;
	public boolean close();
}

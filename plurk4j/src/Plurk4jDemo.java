import plurk4j.PlurkSession;
import plurk4j.PlurkUtils;
import plurk4j.entity.Plurk;
import plurk4j.entity.Plurk.PlurkQualifier;
import plurk4j.json.PlurkProfile;
import plurk4j.json.PlurkResponses;
import plurk4j.json.Plurks;
/***
 * Demo
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.0.1
 */
public class Plurk4jDemo {
	public static void main(String[] args) {
		String username = "username";
		String password = "password";
		
		//Every session is mapping to a Http connection and a Plurk User.
		PlurkSession session = PlurkUtils.createSession(); 

		//Login before any else
		if(PlurkUtils.login(session,username, password)) 
			System.out.println("login success\n");
		
		if(session.isLogin()) {
			//After login(), you get user info in session.
			session.getUserInfo().print();
			PlurkProfile profile = session.getOwnProfile();
			profile.print();
			
			//get other people info by getPublicProfile()
			PlurkProfile otherProfile = PlurkUtils.getPublicProfile(session,18757L);
			otherProfile.print();
			
			//get plurks in your timeline by getPlurks (default return 20 plurks)
			Plurks plurks = PlurkUtils.getPlurks(session);
			plurks.print();
			
			//get your own plurks by getPlurks
			if(session.getUserInfo() != null) {
				Plurks myPlurks = PlurkUtils.getPlurks(session,null,null,session.getUserInfo().uid,null,null);
				myPlurks.print();
				
				//get your own plurks' responses
				for(Plurk plurk:myPlurks.getPlurks()) {
					PlurkResponses responses = PlurkUtils.getResponses(session,plurk.plurk_id,0L);
					if(responses != null) responses.print();
					
					//Add response
					PlurkUtils.addResponse(session,plurk.plurk_id,"test12345",PlurkQualifier.says).print();
				}
			}
			
			//Add Plurk
			PlurkUtils.addPlurk(session,"reach~~~",PlurkQualifier.says).print();
		}
		else {
			System.out.println("session is not enable: " + session.getError());
		}
		
		//Close session after all
		PlurkUtils.closeSession(session);

	}

}

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import plurk4j.PlurkFactory;
import plurk4j.PlurkSession;
import plurk4j.entity.Plurk;
import plurk4j.entity.PlurkProfile;
import plurk4j.entity.PlurkUser;
import plurk4j.official.JsonPlurk;
import plurk4j.official.JsonResponses;
/***
 * Demo
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public class Plurk4jDemo {
	public static void main(String[] args) {
		String username = "username";
		String password = "password";
		
		PlurkFactory.setDebug(true);
		
		//Every session is mapping to a Http connection and a Plurk User.
		PlurkSession session = PlurkFactory.createSession(); 

		//Login before any else
		if(PlurkFactory.login(session,username, password)) 
			System.out.println("login success\n");
		
		if(session.isLogin()) {
			//After login(), you get user info in session.
			PlurkProfile myProfile = session.getProfile();
			print(myProfile);
			print(myProfile.getPlurkUser());
			
			//get other people info by getPublicProfile()
			PlurkProfile profile = PlurkFactory.getPlurkProfile(session, new PlurkUser(18757L));
			print(profile);
			print(profile.getPlurkUser());

			//get plurks in your timeline by getPlurks (default return 20 plurks)
			List<Plurk> plurks = PlurkFactory.getPlurks(session);
			for(Plurk plurk:plurks) {
				print(plurk);
				print(plurk.getPlurkOwner());
			}
			
			
			if(myProfile.getPlurkUser() != null) {
				//get your own plurks by getPlurks
				List<Plurk> myPlurks = PlurkFactory.getPlurks(session,null,null,myProfile.getPlurkUser(),null,null);
				for(Plurk plurk:myPlurks) {
					System.out.println("!!!!!!!!!" + plurk.getOwnerId());
					print(plurk);
				}
				
				for(Plurk plurk:myPlurks) {
					System.out.println("----------" + plurk.getOwnerId());
					print(plurk);
					
					//get your own plurks' responses
					List<Plurk> responses= PlurkFactory.getResponses(session,plurk);
					if(responses != null) {
						for(Plurk response:responses) {
							print(response);
						}
					}
					
					//Add response
					Plurk response = PlurkFactory.addResponse(session,plurk,new Plurk(new Date().toString()));
					print(response);
				}
			}
			
			//Add Plurk
			Plurk newPlurk = PlurkFactory.addPlurk(session,new Plurk(new Date().toString()));
			print(newPlurk);
		}
		else {
			System.out.println("session is not enable: " + session.getError());
		}
		
		//Close session after all
		PlurkFactory.closeSession(session);

	}
	
	public static void print(Object object) {
		System.out.println(new ReflectionToStringBuilder(object,ToStringStyle.MULTI_LINE_STYLE).toString());
	}

}

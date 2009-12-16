import plurk4j.PlurkSession;
import plurk4j.PlurkUtils;
import plurk4j.entity.PlurkUser;
import plurk4j.json.PlurkProfile;
import plurk4j.json.Plurks;


public class Plurk4jDemo {
	public static void main(String[] args) {
		
		PlurkSession session = PlurkUtils.createSession(); 
		if(PlurkUtils.login(session,"username", "password")) 
			System.out.println("login success\n");
		if(session.isLogin()) {
			session.getUserInfo().print();
			
			PlurkProfile profile = session.getOwnProfile();
			profile.print();
			
			PlurkProfile otherProfile = PlurkUtils.getPublicProfile(session,18757L);
			otherProfile.print();
			
			Plurks plurks = PlurkUtils.getPlurks(session);
			plurks.print();
			
			if(session.getUserInfo() != null)
				PlurkUtils.getPlurks(session,null,null,session.getUserInfo().uid,null,null).print();
		}
		else {
			System.out.println("session is not enable: " + session.getError());
		}
		
		
		PlurkUtils.closeSession(session);

	}

}

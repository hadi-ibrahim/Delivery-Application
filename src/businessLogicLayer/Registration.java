package businessLogicLayer;

import DTO.User;
import Helpers.SessionHelper;
import Repositories.RepoUser;

public class Registration {
	private RepoUser repoUser = new RepoUser();

	public User signUp(User user) {
		if(repoUser.create(user)) {
			SessionHelper.isLoggedIn = user;
			return user;
		}
		return null ;
		
	}
	
	public User signIn(String email, String password) {
		 User tempUser = repoUser.login(email, password);
		 if(tempUser!= null) {
			 if (tempUser.getIsDeleted()==1)
				 return null;
			return SessionHelper.isLoggedIn = tempUser;
		 }
		 return null;
	}

	public void signOut() {
		SessionHelper.isLoggedIn = null;
	}

}

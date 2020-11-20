package businessLogicLayer;

import DTO.User;
import Helpers.SessionHelper;
import Repositories.RepoUser;

public class Registration {
	private RepoUser repoUser = new RepoUser();

	public User signUp(User user) {
		if(repoUser.Create(user)) {
			SessionHelper.isLoggedIn = user;
			return user;
		}
		return null ;
		
	}
	
	public User signIn(String email, String password) {
		return SessionHelper.isLoggedIn = repoUser.Login(email, password);
	}

	public void signOut() {
		SessionHelper.isLoggedIn = null;
	}

}

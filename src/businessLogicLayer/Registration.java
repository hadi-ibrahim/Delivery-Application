package businessLogicLayer;

import DTO.User;
import Repositories.RepoUser;

public class Registration {
	private RepoUser repoUser = new RepoUser();

	public User signUp(User user) {
		if(repoUser.Create(user))
			return user;
		return null ;
		
	}
	
	public User signIn(String email, String password) {
		return repoUser.Login(email, password);
	}



}

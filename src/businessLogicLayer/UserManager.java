package businessLogicLayer;

import Repositories.RepoUser;

import java.util.ArrayList;

import DTO.IDTO;
import DTO.Role;
import DTO.User;

public class UserManager {
	private RepoUser repoUser = new RepoUser();
	
	
	public ArrayList<IDTO> getAllUsers(){
		return repoUser.getAll();
	}

	public ArrayList<IDTO> getAllActiveUsers(){
		return repoUser.getAllActive();
	}
	
	public ArrayList<IDTO> getAllDisabledUsers(){
		return repoUser.getAllDisabled();
	}
	
	public User get(int id) {
		return repoUser.get(id);
	}
	
	public void create(User user) {
		repoUser.create(user);
	}

	public void update(User user) {
		repoUser.update(user);
	}


	public void delete(int id) {
		repoUser.delete(id);
	}
	
	public void restore(int id) {
		repoUser.restore(id);
	}
	
	public boolean emailExists(String email) {
		return repoUser.emailExists(email);
	}
}

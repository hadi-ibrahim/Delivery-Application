package businessLogicLayer;

import java.util.ArrayList;

import DTO.Address;
import DTO.User;
import Repositories.RepoAddress;
import Repositories.RepoUser;

public class AddressManager {
	RepoUser repoUser = new RepoUser();
	RepoAddress repoAddress = new RepoAddress();
	
	public User setUserAddresses(User user) {
			user.setUserAddresses(repoAddress.getAllByUser(user.getId()));
			return user;
		}
	
	public ArrayList<Address> getAllByUser(User user) {
		return repoAddress.getAllByUser(user.getId());
	}
	/*
	 * TODO
	 * TRIGGER AFTER UPDATE ON USER
	 * IF NEW.isDeleted = 1 AND OLD.isDeleted = 0 THEN 
	 * DELETE FROM usersavedAddress WHERE idUser = NEW.id;
	 */
	
	/*
	 * TODO
	 * TRIGGER AFTER DELETE ON ADDRESS
	 * 
	 * DELETE FROM usersavedAddress WHERE idAddress = NEW.id;
	 */

}
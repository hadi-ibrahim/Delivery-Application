package businessLogicLayer;


import DTO.IDTO;
import DTO.User;
import Repositories.RepoAddress;
import Repositories.RepoUser;
import Repositories.RepoUserSavedAddress;

public class AddressManager {
	RepoUser repoUser = new RepoUser();
	RepoAddress repoAddress = new RepoAddress();
	RepoUserSavedAddress repoUserAddress = new RepoUserSavedAddress();
	
	public void setUserAddresses() {
		for (IDTO dto : repoUser.getAll()) {
			User user = (User) dto;
			user.setUserAddresses(repoUserAddress.getAllUserAddresses(user.getId()));
		}
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

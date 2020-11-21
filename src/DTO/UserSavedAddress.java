package DTO;

public class UserSavedAddress implements IDTO {
	private int id;
	private int idUser;
	private int idAddress;

	public UserSavedAddress() {

	}

	public UserSavedAddress(int idUser, int idAddress) {
		super();
		this.idUser = idUser;
		this.idAddress = idAddress;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}

}

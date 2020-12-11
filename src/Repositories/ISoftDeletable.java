package Repositories;

import java.util.ArrayList;

import DTO.IDTO;

public interface ISoftDeletable {
	public boolean restore(int id);
	public ArrayList<IDTO> getAllActive();
	public ArrayList<IDTO> getAllDisabled();

}

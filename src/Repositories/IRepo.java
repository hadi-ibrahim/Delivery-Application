package Repositories;

import java.util.ArrayList;

import DTO.IDTO;

public interface IRepo {

	public IDTO get(int id);

	public boolean update(IDTO dto);

	public ArrayList<IDTO> getAll();

	public boolean create(IDTO dto);

	public boolean delete(int id);

	public boolean destroy();
}

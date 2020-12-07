package businessLogicLayer;

import java.util.ArrayList;

import DTO.IDTO;
import DTO.Warehouse;
import Repositories.RepoWarehouse;

public class WarehouseManager {
	RepoWarehouse repoWarehouse = new RepoWarehouse();
	
	public void create(Warehouse Warehouse) {
		repoWarehouse.create(Warehouse);
	}
	
	public void delete(int id) {
		repoWarehouse.delete(id);
	}
	
	public void restore(int id) {
		repoWarehouse.restore(id);
	}
	
	public ArrayList<IDTO> getAllActiveWarehouses(){
		return repoWarehouse.getAllActiveWarehouses();
	}
	
	public ArrayList<IDTO> getAllDisabledWarehouses(){
		return repoWarehouse.getAllDisabledWarehouses();
	}

	public Warehouse get(int id) {
		return repoWarehouse.get(id);
	}

	public void update(Warehouse Warehouse) {
		repoWarehouse.update(Warehouse);
	}
}

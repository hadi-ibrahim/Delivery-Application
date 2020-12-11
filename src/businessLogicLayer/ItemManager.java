package businessLogicLayer;

import DTO.Item;
import Repositories.RepoItem;

import java.util.ArrayList;

import DTO.IDTO;

public class ItemManager {
	RepoItem repoItem = new RepoItem();
	
	public void create(Item item) {
		repoItem.create(item);
	}
	
	public void delete(int id) {
		repoItem.delete(id);
	}
	
	public void restore(int id) {
		repoItem.restore(id);
	}
	
	public ArrayList<IDTO> getAllActiveItems(){
		return repoItem.getAllActive();
	}
	
	public ArrayList<IDTO> getAllDisabledItems(){
		return repoItem.getAllDisabled();
	}

	public Item get(int id) {
		return repoItem.get(id);
	}

	public void update(Item item) {
		repoItem.update(item);
	}
}

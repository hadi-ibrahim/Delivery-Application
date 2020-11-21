package Repositories;

public interface ISoftDeletableRepo extends IRepo {
	public boolean restore(int id);
}

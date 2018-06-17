package service;

import java.util.List;

public interface IGenericService <T> {
	
	boolean saveOrUpdate(T addObject);
	
	boolean remove(T removeObject);
	
	List<T> loadAll();

}

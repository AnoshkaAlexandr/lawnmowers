package dao;

import java.util.List;

public interface IGenericDao<T> {

	boolean add(T addObject);

	boolean remove(T removeObject);

	List<T> loadAll();

}

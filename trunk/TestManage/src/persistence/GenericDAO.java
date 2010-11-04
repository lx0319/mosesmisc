/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mao Shijie
 */
public interface GenericDAO<T, ID extends Serializable> {

	T findById(ID id, boolean lock);

	List<T> findAll();

	List<T> findByExample(T exampleInstance,
			String... excludeProperty);

	T makePersistent(T entity);

	void makeTransient(T entity);

	void flush();

	void clear();
}

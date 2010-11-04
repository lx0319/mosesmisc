/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Task;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mao Shijie
 */
@Repository
public interface TaskDAO extends GenericDAO<Task,Integer> {
	
}

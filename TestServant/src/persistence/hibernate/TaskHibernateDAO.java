
package persistence.hibernate;

import domain.Task;
import org.springframework.stereotype.Repository;
import persistence.TaskDAO;

/**
 *
 * @author Mao Shijie
 */
@Repository
public class TaskHibernateDAO extends GenericHibernateDAO<Task,Integer>  implements TaskDAO {

}


package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import persistence.TaskDAO;
import domain.Student;
import domain.Task;

/**
 *
 * @author Mao Shijie
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;
	
	public void add(Task task) {
		taskDAO.makePersistent(task);
	}

	public void delete(int id) {
		Task task = taskDAO.findById(id, false);
		taskDAO.makeTransient(task);
	}

	public void complete(int id) {
		Task task = taskDAO.findById(id, false);
		task.setComplete(true);
		taskDAO.makePersistent(task);
	}

	public List<Task> list() {
		return taskDAO.findAll();
	}
}

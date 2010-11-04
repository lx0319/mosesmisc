package action;

import org.springframework.beans.factory.annotation.Autowired;

import service.TaskService;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
	
	private static final long serialVersionUID = 5950508911877846142L;

	@Autowired
	private TaskService service ;
	
	public String  execute(){
		return SUCCESS;
	}
	
}

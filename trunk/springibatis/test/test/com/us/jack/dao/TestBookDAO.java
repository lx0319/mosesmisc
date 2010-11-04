package test.com.us.jack.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.us.jack.dao.ISBookDAO;
import com.us.jack.pojo.SBook;

public class TestBookDAO {
	@Test
	public void testSaveBook(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/us/jack/config/applicationContext.xml");
		ISBookDAO bookDAO = (ISBookDAO)context.getBean("bookDAO");
		SBook book = new SBook();
		book.setTitle("Spring2.0核心技术与最佳实践");
		book.setAuthor("廖雪峰");
		book.setPrice(59.80f);
		book.setTotal(50);
		book.setIsbn("7121042621");
		book.setPublisher("电子工业出版社");
		bookDAO.saveBook(book);
	}
	
	@Test
	public void testDeleteBook(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/us/jack/config/applicationContext.xml");
		ISBookDAO bookDAO = (ISBookDAO)context.getBean("bookDAO");
		bookDAO.deleteBook(2);
	}
}

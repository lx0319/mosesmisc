package net.liuxuan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowCountCallbackHandler;

public class testjdbc {
    static Logger logger = Logger.getLogger(testjdbc.class);

    final public static String createSql="create table mytable(id int,cname varchar(50))";
    final public static String insertData1 = "insert into mytable values(1,'胡素苗')";
    final public static String insertData2 = "insert into mytable values(2,'胡云丛')";
    final public static String insertData3 = "insert into mytable values(3,'王文先')";
    final public static String insertData4 = "insert into mytable values(4,'胡娟')";
    final public static String selectSql = "select * from mytable";
    
    public static void tryJDBC(){
	JdbcTemplate jt = (JdbcTemplate) BasicModule.getBeanFactory().getBean("dbtest");
	//jt.execute(test01.createSql);
	jt.execute(insertData1);
	jt.execute(insertData2);
	jt.execute(insertData3);
	jt.execute(insertData4);
	RowCountCallbackHandler rcch = new RowCountCallbackHandler();
	jt.query(selectSql, rcch);

	System.out.println("结果集中的列的数量为：" + rcch.getColumnCount());
	System.out.println("结果集中的行的数量为：" + rcch.getRowCount());
	System.out.println("结果集中的结果为： ");

	String[] str = rcch.getColumnNames();
	for (int i = 0; i < str.length; i++) {
	    System.out.print(str[i] + " ");
	}

	final ArrayList list = new ArrayList();

	jt.query(selectSql, new RowCallbackHandler() {
	    public void processRow(ResultSet rs) throws SQLException {
		SpringtoResultSetInfo sri = new testjdbc().new SpringtoResultSetInfo();
		sri.setId(rs.getInt(1));
		sri.setTemplatename(rs.getString(2));
		list.add(sri);
	    }
	});

	for (int i = 0; i < list.size(); i++) {
	    SpringtoResultSetInfo sri = (SpringtoResultSetInfo) list.get(i);
	    System.out.print("\n" + " " + sri.getId());
	    System.out.print("   " + sri.getTemplatename());
	}

	System.out.println("\n完成！");
    }
    public class SpringtoResultSetInfo {
	    private int id;
	    private String templatename;
	    
	    public String getTemplatename() {
	     return templatename;
	    }

	    public void setTemplatename(String templatename) {
	     this.templatename = templatename;
	    }

	    public int getId() {
	     return id;
	    }

	    public void setId(int id) {
	     this.id = id;
	    }

	   }

    
}

package net.liuxuan.database;

import java.io.Reader;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.liuxuan.database.mapbean.User;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class baseMain {


    /**
     * ��ʼ��iBatis���һ��SqlMapClient����
     * 
     * @param
     * @return SqlMapClient
     */
    public static SqlMapClient getSqlMapClient() {
	String resource = "net/liuxuan/database/conf/SqlMapConfig.xml";
	SqlMapClient sqlMap = null;
	try {
	    Reader reader = Resources.getResourceAsReader(resource);
	    sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return sqlMap;
    }

    /**
     * ����һ����¼
     * 
     * @param
     * @return
     */
    public static void insert() {
	SqlMapClient sqlMap = getSqlMapClient();
	try {
	    sqlMap.startTransaction();
	    User user = new User();
	    user.setName("insert1");
	    user.setDate(new Date());
	    sqlMap.insert("User.insertUser", user);
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    /**
     * ����һ����¼����Ϣ����
     * 
     * @param
     * @return
     */
    public static void update() {
	SqlMapClient sqlMap = getSqlMapClient();
	try {
	    sqlMap.startTransaction();
	    User user = (User) sqlMap.queryForObject("User.getById", "1");
	    user.setName("update1");
	    sqlMap.update("User.updateUser", user);
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		sqlMap.endTransaction();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * ɾ�� id ���ļ�¼
     * 
     * @param
     * @return
     */
    public static void delete() {
	SqlMapClient sqlMap = getSqlMapClient();
	try {
	    sqlMap.startTransaction();
	    String maxId = sqlMap.queryForObject("User.getMaxId", null)
		    .toString();
	    sqlMap.delete("User.deleteUser", maxId);
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    /**
     * ����name��ѯUserΪMap��List
     * 
     * @param
     * @return List
     */
    public static List getUser() {
	SqlMapClient sqlMap = getSqlMapClient();
	List<User> user = null;
	try {
	    sqlMap.startTransaction();
	    HashMap params = new HashMap();
	    params.put("name", "%liulu%");
	    user = sqlMap.queryForList("User.getUser", params);
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		sqlMap.endTransaction();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return user;
    }

    /**
     * ��ѯ�����ֶε����ֵ(һ������ͳ�ƣ��˴���ʾʹ�÷���)
     * 
     * @param
     * @return
     */
    public static void getMax() {
	SqlMapClient sqlMap = getSqlMapClient();
	try {
	    sqlMap.startTransaction();
	    Map search = (HashMap) sqlMap.queryForObject("User.getMax", null);
	    System.out.println(search.get("id").toString() + "\n"
		    + search.get("name").toString() + "\n"
		    + search.get("date").toString());
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    /**
     * ͨ����������,����user
     * 
     * @param
     * @return
     */
    public static void getByPK() {
	SqlMapClient sqlMap = getSqlMapClient();
	User user = new User();
	try {
	    sqlMap.startTransaction();
	    user.setId(1);
	    user = (User) sqlMap.queryForObject("User.getByPK", user);
	    System.out.println(user.getId() + "\n" + user.getName() + "\n"
		    + user.getDate());
	    sqlMap.commitTransaction();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	// insert();
	// update();
	// delete();

	List<User> user = getUser();
	for (User o : user) {
	    System.out.println("id:" + o.getId() + "\nname:" + o.getName()
		    + "\nDate:" + o.getDate() + "\n------------");
	}
	// getMax();
	// getByPK();
    }

}

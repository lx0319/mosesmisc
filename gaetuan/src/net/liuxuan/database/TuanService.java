package net.liuxuan.database;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.liuxuan.database.bean.TuanBean;

public class TuanService {
	public static void save(TuanBean tb) {
		PersistenceManager pm = PMF.getPM();
		try {
			pm.makePersistent(tb);
		} finally {
			pm.close();
		}
	}
	
	public static List<TuanBean> searchUseTB() {
		List<TuanBean> results = (List<TuanBean>) getSearchTBQuery().execute(System.currentTimeMillis());
		return results;
	}
	public static Query getSearchTBQuery() {
//		Query query = pm.newQuery("select from TuanBean " +
//                "where finishDate == CurrentDate " +
//                "parameters String CurrentDate " +
//                "order by finishDate desc");
		PersistenceManager pm = PMF.getPM();
		Query query = pm.newQuery(TuanBean.class);
	    query.setFilter("finishDate > CurrentDate");
	    query.setOrdering("finishDate desc");
	    query.declareParameters("Long CurrentDate");
	    return query;
	}
}

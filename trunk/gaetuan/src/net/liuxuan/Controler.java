package net.liuxuan;

import net.liuxuan.database.TuanService;
import net.liuxuan.database.bean.TuanBean;

public class Controler {
	public static void writeTB(){
		TuanBean tb1 = new TuanBean();
		tb1.setFinishDate(System.currentTimeMillis()+10000000);
		tb1.setSitename("ATUAN");
		TuanBean tb2 = new TuanBean();
		tb2.setFinishDate(System.currentTimeMillis()+20000000);
		tb2.setSitename("BTUAN");
		TuanBean tb3 = new TuanBean();
		tb3.setFinishDate(System.currentTimeMillis());
		tb3.setSitename("CTUAN");
		TuanService.save(tb1);
		TuanService.save(tb2);
		TuanService.save(tb3);
	}
	public static void LoadTB(){
		
	}
	
}

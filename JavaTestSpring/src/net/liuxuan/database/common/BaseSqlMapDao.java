package net.liuxuan.database.common;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BaseSqlMapDao extends SqlMapClientDaoSupport {
    protected static final int PAGE_SIZE = 4;
    protected SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
    public BaseSqlMapDao() { 
  	}
  }

package com.us.jack.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.us.jack.dao.ISBookDAO;
import com.us.jack.pojo.SBook;

public class SBookDAO extends SqlMapClientDaoSupport implements ISBookDAO {
	public SBook findBookByISBN(String isbn) throws RuntimeException {
		return (SBook) this.getSqlMapClientTemplate().queryForObject(
				"findBookByISBN", isbn);
	}

	@SuppressWarnings("unchecked")
	public List<SBook> findBooksByPublisher(String publisher)
			throws RuntimeException {
		return this.getSqlMapClientTemplate().queryForList(
				"findBookByPublisher", publisher);
	}

	public void saveBook(SBook book) throws RuntimeException {
		this.getSqlMapClientTemplate().insert("saveBook", book);
	}

	public void deleteBook(int id) throws RuntimeException {
		this.getSqlMapClientTemplate().delete("deleteBook", id);
	}

	public void updateBook(SBook book) throws RuntimeException {
		this.getSqlMapClientTemplate().update("updateBook", book);
	}

	@SuppressWarnings("unchecked")
	public List findAllBook() throws RuntimeException {
		return this.getSqlMapClientTemplate().queryForList("findAllBook");
	}

	public SBook findBookById(int id) throws RuntimeException {
		return (SBook) this.getSqlMapClientTemplate().queryForObject(
				"findBookById", id);
	}
}

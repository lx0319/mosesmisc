package com.us.jack.services.impl;

import java.util.List;

import com.us.jack.dao.ISBookDAO;
import com.us.jack.pojo.SBook;
import com.us.jack.services.ISBookServices;

public class SBookServices implements ISBookServices {
	private ISBookDAO sbookDAO;

	@SuppressWarnings("unchecked")
	public List getAllBook() throws RuntimeException {
		return sbookDAO.findAllBook();
	}

	public SBook getBookByISBN(String isbn) throws RuntimeException {
		return sbookDAO.findBookByISBN(isbn);
	}

	public List<SBook> getBooksByPublisher(String publisher)
			throws RuntimeException {
		return sbookDAO.findBooksByPublisher(publisher);
	}

	public void removeBook(int id) throws RuntimeException {
		sbookDAO.deleteBook(id);
	}

	public void saveBook(SBook book) throws RuntimeException {
		sbookDAO.saveBook(book);
	}

	public void updateBook(SBook book) throws RuntimeException {
		sbookDAO.updateBook(book);
	}

	public SBook getBookById(int id) throws RuntimeException {
		return sbookDAO.findBookById(id);
	}

	public void setSbookDAO(ISBookDAO sbookDAO) {
		this.sbookDAO = sbookDAO;
	}

}

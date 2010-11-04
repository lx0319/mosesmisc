package com.us.jack.services;

import java.util.List;

import com.us.jack.pojo.SBook;

public interface ISBookServices {
	/**
	 * 添加一本图书至数据库中
	 * 
	 * @param book
	 *            图书对象
	 * @throws RuntimeException
	 */
	public void saveBook(SBook book) throws RuntimeException;

	/**
	 * 删除图书信息
	 * 
	 * @param id
	 *            ID编码
	 * @throws RuntimeException
	 */
	public void removeBook(int id) throws RuntimeException;

	/**
	 * 通过出版社查找此出版社出版的所有图书列表
	 * 
	 * @param publisher
	 *            出版社名称
	 * @return 返回特定出版社下的所有图书列表
	 * @throws RuntimeException
	 */
	public List<SBook> getBooksByPublisher(String publisher)
			throws RuntimeException;

	/**
	 * 通过图书对应的唯一的ISBN号查找图书
	 * 
	 * @param isbn
	 *            图书唯一的ISBN号码
	 * @return 返回此ISBN号对应的图书
	 * @throws RuntimeException
	 */
	public SBook getBookByISBN(String isbn) throws RuntimeException;

	/**
	 * 更新一本图书的信息
	 * 
	 * @param book
	 *            图书对象
	 * @throws RuntimeException
	 */
	public void updateBook(SBook book) throws RuntimeException;

	/**
	 * 查找库中所有的图书
	 * 
	 * @return 返回图书列表
	 * @throws RuntimeException
	 */
	public List getAllBook() throws RuntimeException;

	/**
	 * 通过图书ID号得到图书对象
	 * 
	 * @param id
	 *            图书ID号码
	 * @return 返回此ID对应的图书信息
	 * @throws RuntimeException
	 */
	public SBook getBookById(int id) throws RuntimeException;
}

package com.us.jack.services;

import java.util.List;

import com.us.jack.pojo.SBook;

public interface ISBookServices {
	/**
	 * ���һ��ͼ�������ݿ���
	 * 
	 * @param book
	 *            ͼ�����
	 * @throws RuntimeException
	 */
	public void saveBook(SBook book) throws RuntimeException;

	/**
	 * ɾ��ͼ����Ϣ
	 * 
	 * @param id
	 *            ID����
	 * @throws RuntimeException
	 */
	public void removeBook(int id) throws RuntimeException;

	/**
	 * ͨ����������Ҵ˳�������������ͼ���б�
	 * 
	 * @param publisher
	 *            ����������
	 * @return �����ض��������µ�����ͼ���б�
	 * @throws RuntimeException
	 */
	public List<SBook> getBooksByPublisher(String publisher)
			throws RuntimeException;

	/**
	 * ͨ��ͼ���Ӧ��Ψһ��ISBN�Ų���ͼ��
	 * 
	 * @param isbn
	 *            ͼ��Ψһ��ISBN����
	 * @return ���ش�ISBN�Ŷ�Ӧ��ͼ��
	 * @throws RuntimeException
	 */
	public SBook getBookByISBN(String isbn) throws RuntimeException;

	/**
	 * ����һ��ͼ�����Ϣ
	 * 
	 * @param book
	 *            ͼ�����
	 * @throws RuntimeException
	 */
	public void updateBook(SBook book) throws RuntimeException;

	/**
	 * ���ҿ������е�ͼ��
	 * 
	 * @return ����ͼ���б�
	 * @throws RuntimeException
	 */
	public List getAllBook() throws RuntimeException;

	/**
	 * ͨ��ͼ��ID�ŵõ�ͼ�����
	 * 
	 * @param id
	 *            ͼ��ID����
	 * @return ���ش�ID��Ӧ��ͼ����Ϣ
	 * @throws RuntimeException
	 */
	public SBook getBookById(int id) throws RuntimeException;
}

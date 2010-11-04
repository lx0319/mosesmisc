package com.us.jack.action;

import java.util.List;

import com.us.jack.pojo.SBook;
import com.us.jack.services.ISBookServices;

public class SBookAction {
	private ISBookServices sbookServices;
	private SBook sbook;
	private String tips;
	private String bookId;
	@SuppressWarnings("unchecked")
	private List bookList;

	/**
	 * ���ͼ����Ϣ
	 * 
	 * @return ��������Ƿ�ɹ�
	 */
	public String addSBook() {
		String result = "error";
		try {
			sbookServices.saveBook(sbook);
			this.setTips("��ӳɹ�");
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("ϵͳ��������");
		}
		return result;
	}

	/**
	 * �鿴����ͼ��
	 * 
	 * @return
	 */
	public String viewSBook() {
		String result = "error";
		try {
			bookList = sbookServices.getAllBook();
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("ϵͳ�������⣬���Ժ����");
		}
		return result;
	}

	/**
	 * �޸�ͼ����Ϣ
	 * 
	 * @return
	 */
	public String modifySBook() {
		String result = "error";
		try {
			sbook = sbookServices.getBookById(Integer.parseInt(this.getBookId()));
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("ϵͳ��������");
		}
		return result;
	}

	public String updateSBook(){
		String result = "error";
		try{
			sbookServices.updateBook(sbook);
			result = "success";
		}catch(Exception e){
			e.printStackTrace();
			this.setTips("���²���ʧ��");
		}
		return result;
	}
	
	/**
	 * ɾ��ͼ��
	 * @return
	 */
	public String removeSBook(){
		String result = "error";
		try{
			sbookServices.removeBook(Integer.parseInt(this.getBookId()));
			result = "success";
		}catch(Exception e){
			e.printStackTrace();
			this.setTips("ɾ������ʧ��");
		}
		return result;
	}
	
	public SBook getSbook() {
		return sbook;
	}

	public void setSbook(SBook sbook) {
		this.sbook = sbook;
	}

	public void setSbookServices(ISBookServices sbookServices) {
		this.sbookServices = sbookServices;
	}

	@SuppressWarnings("unchecked")
	public List getBookList() {
		return bookList;
	}

	@SuppressWarnings("unchecked")
	public void setBookList(List bookList) {
		this.bookList = bookList;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
}

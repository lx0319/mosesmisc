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
	 * 添加图书信息
	 * 
	 * @return 返回添加是否成功
	 */
	public String addSBook() {
		String result = "error";
		try {
			sbookServices.saveBook(sbook);
			this.setTips("添加成功");
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("系统出现问题");
		}
		return result;
	}

	/**
	 * 查看所有图书
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
			this.setTips("系统出现问题，请稍后访问");
		}
		return result;
	}

	/**
	 * 修改图书信息
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
			this.setTips("系统出现问题");
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
			this.setTips("更新操作失败");
		}
		return result;
	}
	
	/**
	 * 删除图书
	 * @return
	 */
	public String removeSBook(){
		String result = "error";
		try{
			sbookServices.removeBook(Integer.parseInt(this.getBookId()));
			result = "success";
		}catch(Exception e){
			e.printStackTrace();
			this.setTips("删除操作失败");
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

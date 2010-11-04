package com.us.jack.pojo;
/**
 * Õº È¿‡
 * @author jack
 *
 */
public class SBook {
	private int id;
	private String title;
	private String author;
	private int total;
	private float price;
	private String isbn;
	private String publisher;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getPrice(){
		return price;
	}
}

package net.liuxuan.database.mapbean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private int id;
	private String name;
	private Date date;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

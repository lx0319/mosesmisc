package net.liuxuan.tuan.site.bean;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ProcessNodeBean {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private int processtype;
	@Persistent
	private String xpath;

	/**
	 * 取得一段文字后正则解析
	 */
	@Persistent
	private String regex;

	/**
	 * 乘倍数
	 */
	@Persistent
	private int multplier;

	public ProcessNodeBean(){
		
	}
	public ProcessNodeBean(int processtype, String xpath, String regex,
			int multplier) {
		super();
		this.processtype = processtype;
		this.xpath = xpath;
		this.regex = regex;
		this.multplier = multplier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getProcesstype() {
		return processtype;
	}

	public void setProcesstype(int processtype) {
		this.processtype = processtype;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public int getMultplier() {
		return multplier;
	}

	public void setMultplier(int multplier) {
		this.multplier = multplier;
	}

}

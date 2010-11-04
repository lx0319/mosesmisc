package net.liuxuan.database.bean;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TuanBean {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date savedate;

	/**
	 * 网站名
	 */
	@Persistent
	private String sitename;

	/**
	 * 网站地址
	 */
	@Persistent
	private String siteurl;

	/**
	 * 产品名
	 */
	@Persistent
	private String productname;

	/**
	 * 原价
	 */
	@Persistent
	private String priceorigin;
	/**
	 * 现价
	 */
	@Persistent
	private String pricenow;
	/**
	 * 折扣
	 */
	@Persistent
	private String pricediscount;
	/**
	 * 备注
	 */
	@Persistent
	private Text comment;
	
	/**
	 * 内容
	 */
	@Persistent
	private Text content;
	/**
	 * 图片地址
	 */
	@Persistent
	private String imgurl;
	
	/**
	 * 结束日期
	 */
	@Persistent
	private Long finishDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSavedate() {
		return savedate;
	}

	public void setSavedate(Date savedate) {
		this.savedate = savedate;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getSiteurl() {
		return siteurl;
	}

	public void setSiteurl(String siteurl) {
		this.siteurl = siteurl;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getPriceorigin() {
		return priceorigin;
	}

	public void setPriceorigin(String priceorigin) {
		this.priceorigin = priceorigin;
	}

	public String getPricenow() {
		return pricenow;
	}

	public void setPricenow(String pricenow) {
		this.pricenow = pricenow;
	}

	public String getPricediscount() {
		return pricediscount;
	}

	public void setPricediscount(String pricediscount) {
		this.pricediscount = pricediscount;
	}

	public Text getComment() {
		return comment;
	}

	public void setComment(Text comment) {
		this.comment = comment;
	}

	public Text getContent() {
		return content;
	}

	public void setContent(Text content) {
		this.content = content;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Long getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Long finishDate) {
		this.finishDate = finishDate;
	}

}

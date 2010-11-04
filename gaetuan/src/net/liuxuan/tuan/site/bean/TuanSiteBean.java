package net.liuxuan.tuan.site.bean;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class TuanSiteBean {
//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Long id;
	@PrimaryKey
	@Persistent
	private String sitename;
	@Persistent
	private Date lastupdatedate;
	@Persistent
	private String siteurl;
	@Persistent
	private Integer siterank;
	@Persistent
	private ProcessNodeBean productname;
	@Persistent
	private ProcessNodeBean priceorigin;
	@Persistent
	private ProcessNodeBean pricenow;
	@Persistent
	private ProcessNodeBean pricediscount;
	@Persistent
	private ProcessNodeBean comment;
	@Persistent
	private ProcessNodeBean content;
	@Persistent
	private ProcessNodeBean imgurl;
	@Persistent
	private ProcessNodeBean finishDate;
	@Persistent
	private String charset;

	public String getSitename() {
//		return sitename;
		// Once submitted, the @PrimaryKey will be turned into
	    // a serialized Key instance, which is why we need to
	    // do this stuff.
	    return KeyFactory.stringToKey(sitename).getName();
	}
	
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public Date getLastupdatedate() {
		return lastupdatedate;
	}

	public void setLastupdatedate(Date lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}


	public String getSiteurl() {
		return siteurl;
	}

	public void setSiteurl(String siteurl) {
		this.siteurl = siteurl;
	}

	public Integer getSiterank() {
		return siterank;
	}

	public void setSiterank(Integer siterank) {
		this.siterank = siterank;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public ProcessNodeBean getProductname() {
		return productname;
	}

	public void setProductname(ProcessNodeBean productname) {
		this.productname = productname;
	}

	public ProcessNodeBean getPriceorigin() {
		return priceorigin;
	}

	public void setPriceorigin(ProcessNodeBean priceorigin) {
		this.priceorigin = priceorigin;
	}

	public ProcessNodeBean getPricenow() {
		return pricenow;
	}

	public void setPricenow(ProcessNodeBean pricenow) {
		this.pricenow = pricenow;
	}

	public ProcessNodeBean getPricediscount() {
		return pricediscount;
	}

	public void setPricediscount(ProcessNodeBean pricediscount) {
		this.pricediscount = pricediscount;
	}

	public ProcessNodeBean getComment() {
		return comment;
	}

	public void setComment(ProcessNodeBean comment) {
		this.comment = comment;
	}

	public ProcessNodeBean getContent() {
		return content;
	}

	public void setContent(ProcessNodeBean content) {
		this.content = content;
	}

	public ProcessNodeBean getImgurl() {
		return imgurl;
	}

	public void setImgurl(ProcessNodeBean imgurl) {
		this.imgurl = imgurl;
	}

	public ProcessNodeBean getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(ProcessNodeBean finishDate) {
		this.finishDate = finishDate;
	}
}

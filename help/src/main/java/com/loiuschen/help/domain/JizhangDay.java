package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "jizhangday")
public class JizhangDay  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5208764875449863265L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "jizhangid")
	String jizhangid;
	@Column(name = "id")
	String id;
	@Column(name = "jizhangriqi")
	String jizhangriqi;
	@Column(name = "xinzengtouzie")
	String xinzengtouzie;
	@Column(name = "tixiane")
	String tixiane;
	@Column(name = "shouyie")
	String shouyie;
	@Column(name = "touzitianshu")
	String touzitianshu;
	
	public String getTouzitianshu() {
		return touzitianshu;
	}
	public void setTouzitianshu(String touzitianshu) {
		this.touzitianshu = touzitianshu;
	}
	public String getJizhangid() {
		return jizhangid;
	}
	public void setJizhangid(String jizhangid) {
		this.jizhangid = jizhangid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJizhangriqi() {
		return jizhangriqi;
	}
	public void setJizhangriqi(String jizhangriqi) {
		this.jizhangriqi = jizhangriqi;
	}
	public String getXinzengtouzie() {
		return xinzengtouzie;
	}
	public void setXinzengtouzie(String xinzengtouzie) {
		this.xinzengtouzie = xinzengtouzie;
	}
	public String getTixiane() {
		return tixiane;
	}
	public void setTixiane(String tixiane) {
		this.tixiane = tixiane;
	}
	public String getShouyie() {
		return shouyie;
	}
	public void setShouyie(String shouyie) {
		this.shouyie = shouyie;
	}
	
	
}

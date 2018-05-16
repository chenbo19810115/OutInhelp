package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "jizhangsum")
public class JizhangSum   implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3679639020017197867L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "jizhangid")
	String jizhangid;
	@Column(name = "id")
	String id;
	@Column(name = "jizhangriqi")
	String jizhangriqi;
	@Column(name = "zongtouzie")
	String zongtouzie;
	@Column(name = "zongshouyie")
	String zongshouyie;
	@Column(name = "shouyie")
	String shouyie;
	@Column(name = "nianhuashouyilv")
	String nianhuashouyilv;
	
	public String getNianhuashouyilv() {
		return nianhuashouyilv;
	}
	public void setNianhuashouyilv(String nianhuashouyilv) {
		this.nianhuashouyilv = nianhuashouyilv;
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
	public String getZongtouzie() {
		return zongtouzie;
	}
	public void setZongtouzie(String zongtouzie) {
		this.zongtouzie = zongtouzie;
	}
	public String getZongshouyie() {
		return zongshouyie;
	}
	public void setZongshouyie(String zongshouyie) {
		this.zongshouyie = zongshouyie;
	}
	public String getShouyie() {
		return shouyie;
	}
	public void setShouyie(String shouyie) {
		this.shouyie = shouyie;
	}

}

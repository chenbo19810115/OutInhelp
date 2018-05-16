package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "usertouzi")
public class Touzi  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9031008341086668461L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "touziid")
    private String touziid;
	@Column(name = "id")
	private String id;
	@Column(name = "touzie")
	private String touzie;
	@Column(name = "touziriqi")
	private String touziriqi;
	@Column(name = "touzitianshu")
	private String touzitianshu;

	public String getTouzitianshu() {
		return touzitianshu;
	}
	public void setTouzitianshu(String touzitianshu) {
		this.touzitianshu = touzitianshu;
	}
	public String getTouziid() {
		return touziid;
	}
	public void setTouziid(String touziid) {
		this.touziid = touziid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTouzie() {
		return touzie;
	}
	public void setTouzie(String touzie) {
		this.touzie = touzie;
	}
	public String getTouziriqi() {
		return touziriqi;
	}
	public void setTouziriqi(String touziriqi) {
		this.touziriqi = touziriqi;
	}
}

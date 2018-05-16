package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "usertixian")
public class Tixian   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5065873086101197077L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "tixianid")
    private String tixianid;
	@Column(name = "id")
	private String id;
	@Column(name = "tixiane")
	private String tixiane;
	@Column(name = "tixianriqi")
	private String tixianriqi;
	@Column(name = "btixian")
	private String btixian = "0";
	@Column(name = "buserread")
	private String buserread = "0";
	
	public String getBtixian() {
		return btixian;
	}

	public void setBtixian(String btixian) {
		this.btixian = btixian;
	}

	public String getBuserread() {
		return buserread;
	}

	public void setBuserread(String buserread) {
		this.buserread = buserread;
	}

	public String getTixianid() {
		return tixianid;
	}
	
	public void setTixianid(String tixianid) {
		this.tixianid = tixianid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTixiane() {
		return tixiane;
	}
	
	public void setTixiane(String userTixiane) {
		this.tixiane = userTixiane;
	}
	
	public String getTixianriqi() {
		return tixianriqi;
	}
	public void setTixianriqi(String tixianRiqi) {
		this.tixianriqi = tixianRiqi;
	}
}

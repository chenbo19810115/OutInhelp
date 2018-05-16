package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "usershouyi")
public class Shouyi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8692746002730275468L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "shouyiid")
	String shouyiid;
	@Column(name = "id")
	String id;
	@Column(name = "shouyiriqi")
	String shouyiriqi;
	@Column(name = "shouyie")
	String shouyie;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getShouyiid() {
		return shouyiid;
	}
	public void setShouyiid(String shouyiid) {
		this.shouyiid = shouyiid;
	}
	
	public String getShouyiriqi() {
		return shouyiriqi;
	}
	public void setShouyiriqi(String shouyiriqi) {
		this.shouyiriqi = shouyiriqi;
	}
	public String getShouyie() {
		return shouyie;
	}
	public void setShouyie(String shouyie) {
		this.shouyie = shouyie;
	}
	
	
}

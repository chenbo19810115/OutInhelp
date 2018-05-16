package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "back")
public class Back implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9003873280348635724L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "backid")
	private String backid;
	@Column(name = "backfile")
	private String backfile;
	public String getBackid() {
		return backid;
	}
	public void setBackid(String backid) {
		this.backid = backid;
	}
	public String getBackfile() {
		return backfile;
	}
	public void setBackfile(String backfile) {
		this.backfile = backfile;
	}
}

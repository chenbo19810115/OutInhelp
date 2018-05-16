package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "optinfo")
public class Optinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8286062090705238640L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "optid")
	private String optid;
	@Column(name = "opttime")
	private String opttime;
	@Column(name = "optcode")
	private Integer optcode;
	@Column(name = "optdescription")
	private String optdescription;
	@Column(name = "optcontent")
	private String optcontent;
	public String getOptid() {
		return optid;
	}
	public void setOptid(String optid) {
		this.optid = optid;
	}
	public String getOpttime() {
		return opttime;
	}
	public void setOpttime(String opttime) {
		this.opttime = opttime;
	}
	public Integer getOptcode() {
		return optcode;
	}
	public void setOptcode(Integer optcode) {
		this.optcode = optcode;
	}
	public String getOptdescription() {
		return optdescription;
	}
	public void setOptdescription(String optdescription) {
		this.optdescription = optdescription;
	}
	public String getOptcontent() {
		return optcontent;
	}
	public void setOptcontent(String optcontent) {
		this.optcontent = optcontent;
	}
}
